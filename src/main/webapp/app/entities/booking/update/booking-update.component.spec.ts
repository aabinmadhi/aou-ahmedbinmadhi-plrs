import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BookingFormService } from './booking-form.service';
import { BookingService } from '../service/booking.service';
import { IBooking } from '../booking.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ILocationService } from 'app/entities/location-service/location-service.model';
import { LocationServiceService } from 'app/entities/location-service/service/location-service.service';

import { BookingUpdateComponent } from './booking-update.component';

describe('Booking Management Update Component', () => {
  let comp: BookingUpdateComponent;
  let fixture: ComponentFixture<BookingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bookingFormService: BookingFormService;
  let bookingService: BookingService;
  let userService: UserService;
  let locationServiceService: LocationServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BookingUpdateComponent],
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
      .overrideTemplate(BookingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BookingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bookingFormService = TestBed.inject(BookingFormService);
    bookingService = TestBed.inject(BookingService);
    userService = TestBed.inject(UserService);
    locationServiceService = TestBed.inject(LocationServiceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const booking: IBooking = { id: 456 };
      const internalUser: IUser = { id: 61694 };
      booking.internalUser = internalUser;

      const userCollection: IUser[] = [{ id: 99539 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [internalUser];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ booking });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call LocationService query and add missing value', () => {
      const booking: IBooking = { id: 456 };
      const locationService: ILocationService = { id: 62058 };
      booking.locationService = locationService;

      const locationServiceCollection: ILocationService[] = [{ id: 43248 }];
      jest.spyOn(locationServiceService, 'query').mockReturnValue(of(new HttpResponse({ body: locationServiceCollection })));
      const additionalLocationServices = [locationService];
      const expectedCollection: ILocationService[] = [...additionalLocationServices, ...locationServiceCollection];
      jest.spyOn(locationServiceService, 'addLocationServiceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ booking });
      comp.ngOnInit();

      expect(locationServiceService.query).toHaveBeenCalled();
      expect(locationServiceService.addLocationServiceToCollectionIfMissing).toHaveBeenCalledWith(
        locationServiceCollection,
        ...additionalLocationServices.map(expect.objectContaining)
      );
      expect(comp.locationServicesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const booking: IBooking = { id: 456 };
      const internalUser: IUser = { id: 42301 };
      booking.internalUser = internalUser;
      const locationService: ILocationService = { id: 50558 };
      booking.locationService = locationService;

      activatedRoute.data = of({ booking });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(internalUser);
      expect(comp.locationServicesSharedCollection).toContain(locationService);
      expect(comp.booking).toEqual(booking);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBooking>>();
      const booking = { id: 123 };
      jest.spyOn(bookingFormService, 'getBooking').mockReturnValue(booking);
      jest.spyOn(bookingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ booking });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: booking }));
      saveSubject.complete();

      // THEN
      expect(bookingFormService.getBooking).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(bookingService.update).toHaveBeenCalledWith(expect.objectContaining(booking));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBooking>>();
      const booking = { id: 123 };
      jest.spyOn(bookingFormService, 'getBooking').mockReturnValue({ id: null });
      jest.spyOn(bookingService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ booking: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: booking }));
      saveSubject.complete();

      // THEN
      expect(bookingFormService.getBooking).toHaveBeenCalled();
      expect(bookingService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBooking>>();
      const booking = { id: 123 };
      jest.spyOn(bookingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ booking });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bookingService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareLocationService', () => {
      it('Should forward to locationServiceService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(locationServiceService, 'compareLocationService');
        comp.compareLocationService(entity, entity2);
        expect(locationServiceService.compareLocationService).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
