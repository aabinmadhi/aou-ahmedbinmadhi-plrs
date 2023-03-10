import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { LocationServiceFormService, LocationServiceFormGroup } from './location-service-form.service';
import { ILocationService } from '../location-service.model';
import { LocationServiceService } from '../service/location-service.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { City } from 'app/entities/enumerations/city.model';

@Component({
  selector: 'jhi-location-service-update',
  templateUrl: './location-service-update.component.html',
})
export class LocationServiceUpdateComponent implements OnInit {
  isSaving = false;
  locationService: ILocationService | null = null;
  cityValues = Object.keys(City);

  usersSharedCollection: IUser[] = [];

  editForm: LocationServiceFormGroup = this.locationServiceFormService.createLocationServiceFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected locationServiceService: LocationServiceService,
    protected locationServiceFormService: LocationServiceFormService,
    protected userService: UserService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ locationService }) => {
      this.locationService = locationService;
      if (locationService) {
        this.updateForm(locationService);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('aouAhmedbinmadhiPlrsApp.error', { message: err.message })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const locationService = this.locationServiceFormService.getLocationService(this.editForm);
    if (locationService.id !== null) {
      this.subscribeToSaveResponse(this.locationServiceService.update(locationService));
    } else {
      this.subscribeToSaveResponse(this.locationServiceService.create(locationService));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocationService>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(locationService: ILocationService): void {
    this.locationService = locationService;
    this.locationServiceFormService.resetForm(this.editForm, locationService);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(
      this.usersSharedCollection,
      locationService.internalUser
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.locationService?.internalUser)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }
}
