import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TimeSlotFormService, TimeSlotFormGroup } from './time-slot-form.service';
import { ITimeSlot } from '../time-slot.model';
import { TimeSlotService } from '../service/time-slot.service';
import { ILocationService } from 'app/entities/location-service/location-service.model';
import { LocationServiceService } from 'app/entities/location-service/service/location-service.service';

@Component({
  selector: 'jhi-time-slot-update',
  templateUrl: './time-slot-update.component.html',
})
export class TimeSlotUpdateComponent implements OnInit {
  isSaving = false;
  timeSlot: ITimeSlot | null = null;

  locationServicesSharedCollection: ILocationService[] = [];

  editForm: TimeSlotFormGroup = this.timeSlotFormService.createTimeSlotFormGroup();

  constructor(
    protected timeSlotService: TimeSlotService,
    protected timeSlotFormService: TimeSlotFormService,
    protected locationServiceService: LocationServiceService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLocationService = (o1: ILocationService | null, o2: ILocationService | null): boolean =>
    this.locationServiceService.compareLocationService(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ timeSlot }) => {
      this.timeSlot = timeSlot;
      if (timeSlot) {
        this.updateForm(timeSlot);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const timeSlot = this.timeSlotFormService.getTimeSlot(this.editForm);
    if (timeSlot.id !== null) {
      this.subscribeToSaveResponse(this.timeSlotService.update(timeSlot));
    } else {
      this.subscribeToSaveResponse(this.timeSlotService.create(timeSlot));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITimeSlot>>): void {
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

  protected updateForm(timeSlot: ITimeSlot): void {
    this.timeSlot = timeSlot;
    this.timeSlotFormService.resetForm(this.editForm, timeSlot);

    this.locationServicesSharedCollection = this.locationServiceService.addLocationServiceToCollectionIfMissing<ILocationService>(
      this.locationServicesSharedCollection,
      timeSlot.locationService
    );
  }

  protected loadRelationshipsOptions(): void {
    this.locationServiceService
      .query()
      .pipe(map((res: HttpResponse<ILocationService[]>) => res.body ?? []))
      .pipe(
        map((locationServices: ILocationService[]) =>
          this.locationServiceService.addLocationServiceToCollectionIfMissing<ILocationService>(
            locationServices,
            this.timeSlot?.locationService
          )
        )
      )
      .subscribe((locationServices: ILocationService[]) => (this.locationServicesSharedCollection = locationServices));
  }
}
