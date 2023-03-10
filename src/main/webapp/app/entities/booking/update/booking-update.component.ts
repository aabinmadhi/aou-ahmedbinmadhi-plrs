import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { BookingFormService, BookingFormGroup } from './booking-form.service';
import { IBooking } from '../booking.model';
import { BookingService } from '../service/booking.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ILocationService } from 'app/entities/location-service/location-service.model';
import { LocationServiceService } from 'app/entities/location-service/service/location-service.service';

@Component({
  selector: 'jhi-booking-update',
  templateUrl: './booking-update.component.html',
})
export class BookingUpdateComponent implements OnInit {
  isSaving = false;
  booking: IBooking | null = null;

  usersSharedCollection: IUser[] = [];
  locationServicesSharedCollection: ILocationService[] = [];

  editForm: BookingFormGroup = this.bookingFormService.createBookingFormGroup();

  constructor(
    protected bookingService: BookingService,
    protected bookingFormService: BookingFormService,
    protected userService: UserService,
    protected locationServiceService: LocationServiceService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareLocationService = (o1: ILocationService | null, o2: ILocationService | null): boolean =>
    this.locationServiceService.compareLocationService(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ booking }) => {
      this.booking = booking;
      if (booking) {
        this.updateForm(booking);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const booking = this.bookingFormService.getBooking(this.editForm);
    if (booking.id !== null) {
      this.subscribeToSaveResponse(this.bookingService.update(booking));
    } else {
      this.subscribeToSaveResponse(this.bookingService.create(booking));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBooking>>): void {
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

  protected updateForm(booking: IBooking): void {
    this.booking = booking;
    this.bookingFormService.resetForm(this.editForm, booking);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, booking.internalUser);
    this.locationServicesSharedCollection = this.locationServiceService.addLocationServiceToCollectionIfMissing<ILocationService>(
      this.locationServicesSharedCollection,
      booking.locationService
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.booking?.internalUser)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.locationServiceService
      .query()
      .pipe(map((res: HttpResponse<ILocationService[]>) => res.body ?? []))
      .pipe(
        map((locationServices: ILocationService[]) =>
          this.locationServiceService.addLocationServiceToCollectionIfMissing<ILocationService>(
            locationServices,
            this.booking?.locationService
          )
        )
      )
      .subscribe((locationServices: ILocationService[]) => (this.locationServicesSharedCollection = locationServices));
  }
}
