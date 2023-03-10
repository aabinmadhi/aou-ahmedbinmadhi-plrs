import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProfitFormService } from './profit-form.service';
import { ProfitService } from '../service/profit.service';
import { IProfit } from '../profit.model';
import { IBooking } from 'app/entities/booking/booking.model';
import { BookingService } from 'app/entities/booking/service/booking.service';

import { ProfitUpdateComponent } from './profit-update.component';

describe('Profit Management Update Component', () => {
  let comp: ProfitUpdateComponent;
  let fixture: ComponentFixture<ProfitUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let profitFormService: ProfitFormService;
  let profitService: ProfitService;
  let bookingService: BookingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProfitUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ProfitUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProfitUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    profitFormService = TestBed.inject(ProfitFormService);
    profitService = TestBed.inject(ProfitService);
    bookingService = TestBed.inject(BookingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call booking query and add missing value', () => {
      const profit: IProfit = { id: 456 };
      const booking: IBooking = { id: 63537 };
      profit.booking = booking;

      const bookingCollection: IBooking[] = [{ id: 26022 }];
      jest.spyOn(bookingService, 'query').mockReturnValue(of(new HttpResponse({ body: bookingCollection })));
      const expectedCollection: IBooking[] = [booking, ...bookingCollection];
      jest.spyOn(bookingService, 'addBookingToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ profit });
      comp.ngOnInit();

      expect(bookingService.query).toHaveBeenCalled();
      expect(bookingService.addBookingToCollectionIfMissing).toHaveBeenCalledWith(bookingCollection, booking);
      expect(comp.bookingsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const profit: IProfit = { id: 456 };
      const booking: IBooking = { id: 44341 };
      profit.booking = booking;

      activatedRoute.data = of({ profit });
      comp.ngOnInit();

      expect(comp.bookingsCollection).toContain(booking);
      expect(comp.profit).toEqual(profit);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfit>>();
      const profit = { id: 123 };
      jest.spyOn(profitFormService, 'getProfit').mockReturnValue(profit);
      jest.spyOn(profitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ profit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: profit }));
      saveSubject.complete();

      // THEN
      expect(profitFormService.getProfit).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(profitService.update).toHaveBeenCalledWith(expect.objectContaining(profit));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfit>>();
      const profit = { id: 123 };
      jest.spyOn(profitFormService, 'getProfit').mockReturnValue({ id: null });
      jest.spyOn(profitService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ profit: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: profit }));
      saveSubject.complete();

      // THEN
      expect(profitFormService.getProfit).toHaveBeenCalled();
      expect(profitService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfit>>();
      const profit = { id: 123 };
      jest.spyOn(profitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ profit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(profitService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareBooking', () => {
      it('Should forward to bookingService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(bookingService, 'compareBooking');
        comp.compareBooking(entity, entity2);
        expect(bookingService.compareBooking).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
