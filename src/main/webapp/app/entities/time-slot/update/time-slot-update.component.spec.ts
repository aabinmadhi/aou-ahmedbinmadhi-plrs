import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TimeSlotFormService } from './time-slot-form.service';
import { TimeSlotService } from '../service/time-slot.service';
import { ITimeSlot } from '../time-slot.model';
import { ILocationService } from 'app/entities/location-service/location-service.model';
import { LocationServiceService } from 'app/entities/location-service/service/location-service.service';

import { TimeSlotUpdateComponent } from './time-slot-update.component';

describe('TimeSlot Management Update Component', () => {
  let comp: TimeSlotUpdateComponent;
  let fixture: ComponentFixture<TimeSlotUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let timeSlotFormService: TimeSlotFormService;
  let timeSlotService: TimeSlotService;
  let locationServiceService: LocationServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TimeSlotUpdateComponent],
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
      .overrideTemplate(TimeSlotUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TimeSlotUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    timeSlotFormService = TestBed.inject(TimeSlotFormService);
    timeSlotService = TestBed.inject(TimeSlotService);
    locationServiceService = TestBed.inject(LocationServiceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LocationService query and add missing value', () => {
      const timeSlot: ITimeSlot = { id: 456 };
      const locationService: ILocationService = { id: 53802 };
      timeSlot.locationService = locationService;

      const locationServiceCollection: ILocationService[] = [{ id: 16241 }];
      jest.spyOn(locationServiceService, 'query').mockReturnValue(of(new HttpResponse({ body: locationServiceCollection })));
      const additionalLocationServices = [locationService];
      const expectedCollection: ILocationService[] = [...additionalLocationServices, ...locationServiceCollection];
      jest.spyOn(locationServiceService, 'addLocationServiceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ timeSlot });
      comp.ngOnInit();

      expect(locationServiceService.query).toHaveBeenCalled();
      expect(locationServiceService.addLocationServiceToCollectionIfMissing).toHaveBeenCalledWith(
        locationServiceCollection,
        ...additionalLocationServices.map(expect.objectContaining)
      );
      expect(comp.locationServicesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const timeSlot: ITimeSlot = { id: 456 };
      const locationService: ILocationService = { id: 40335 };
      timeSlot.locationService = locationService;

      activatedRoute.data = of({ timeSlot });
      comp.ngOnInit();

      expect(comp.locationServicesSharedCollection).toContain(locationService);
      expect(comp.timeSlot).toEqual(timeSlot);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITimeSlot>>();
      const timeSlot = { id: 123 };
      jest.spyOn(timeSlotFormService, 'getTimeSlot').mockReturnValue(timeSlot);
      jest.spyOn(timeSlotService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ timeSlot });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: timeSlot }));
      saveSubject.complete();

      // THEN
      expect(timeSlotFormService.getTimeSlot).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(timeSlotService.update).toHaveBeenCalledWith(expect.objectContaining(timeSlot));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITimeSlot>>();
      const timeSlot = { id: 123 };
      jest.spyOn(timeSlotFormService, 'getTimeSlot').mockReturnValue({ id: null });
      jest.spyOn(timeSlotService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ timeSlot: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: timeSlot }));
      saveSubject.complete();

      // THEN
      expect(timeSlotFormService.getTimeSlot).toHaveBeenCalled();
      expect(timeSlotService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITimeSlot>>();
      const timeSlot = { id: 123 };
      jest.spyOn(timeSlotService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ timeSlot });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(timeSlotService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
