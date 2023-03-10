import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ProfitFormService, ProfitFormGroup } from './profit-form.service';
import { IProfit } from '../profit.model';
import { ProfitService } from '../service/profit.service';
import { IBooking } from 'app/entities/booking/booking.model';
import { BookingService } from 'app/entities/booking/service/booking.service';

@Component({
  selector: 'jhi-profit-update',
  templateUrl: './profit-update.component.html',
})
export class ProfitUpdateComponent implements OnInit {
  isSaving = false;
  profit: IProfit | null = null;

  bookingsCollection: IBooking[] = [];

  editForm: ProfitFormGroup = this.profitFormService.createProfitFormGroup();

  constructor(
    protected profitService: ProfitService,
    protected profitFormService: ProfitFormService,
    protected bookingService: BookingService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareBooking = (o1: IBooking | null, o2: IBooking | null): boolean => this.bookingService.compareBooking(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ profit }) => {
      this.profit = profit;
      if (profit) {
        this.updateForm(profit);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const profit = this.profitFormService.getProfit(this.editForm);
    if (profit.id !== null) {
      this.subscribeToSaveResponse(this.profitService.update(profit));
    } else {
      this.subscribeToSaveResponse(this.profitService.create(profit));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfit>>): void {
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

  protected updateForm(profit: IProfit): void {
    this.profit = profit;
    this.profitFormService.resetForm(this.editForm, profit);

    this.bookingsCollection = this.bookingService.addBookingToCollectionIfMissing<IBooking>(this.bookingsCollection, profit.booking);
  }

  protected loadRelationshipsOptions(): void {
    this.bookingService
      .query({ filter: 'profit-is-null' })
      .pipe(map((res: HttpResponse<IBooking[]>) => res.body ?? []))
      .pipe(map((bookings: IBooking[]) => this.bookingService.addBookingToCollectionIfMissing<IBooking>(bookings, this.profit?.booking)))
      .subscribe((bookings: IBooking[]) => (this.bookingsCollection = bookings));
  }
}
