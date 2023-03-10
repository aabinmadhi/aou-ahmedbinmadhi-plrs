import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LocationServiceFormService } from './location-service-form.service';
import { LocationServiceService } from '../service/location-service.service';
import { ILocationService } from '../location-service.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

import { LocationServiceUpdateComponent } from './location-service-update.component';

describe('LocationService Management Update Component', () => {
  let comp: LocationServiceUpdateComponent;
  let fixture: ComponentFixture<LocationServiceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let locationServiceFormService: LocationServiceFormService;
  let locationServiceService: LocationServiceService;
  let userService: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LocationServiceUpdateComponent],
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
      .overrideTemplate(LocationServiceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LocationServiceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    locationServiceFormService = TestBed.inject(LocationServiceFormService);
    locationServiceService = TestBed.inject(LocationServiceService);
    userService = TestBed.inject(UserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const locationService: ILocationService = { id: 456 };
      const internalUser: IUser = { id: 9195 };
      locationService.internalUser = internalUser;

      const userCollection: IUser[] = [{ id: 2360 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [internalUser];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ locationService });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const locationService: ILocationService = { id: 456 };
      const internalUser: IUser = { id: 45366 };
      locationService.internalUser = internalUser;

      activatedRoute.data = of({ locationService });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(internalUser);
      expect(comp.locationService).toEqual(locationService);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILocationService>>();
      const locationService = { id: 123 };
      jest.spyOn(locationServiceFormService, 'getLocationService').mockReturnValue(locationService);
      jest.spyOn(locationServiceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ locationService });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: locationService }));
      saveSubject.complete();

      // THEN
      expect(locationServiceFormService.getLocationService).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(locationServiceService.update).toHaveBeenCalledWith(expect.objectContaining(locationService));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILocationService>>();
      const locationService = { id: 123 };
      jest.spyOn(locationServiceFormService, 'getLocationService').mockReturnValue({ id: null });
      jest.spyOn(locationServiceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ locationService: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: locationService }));
      saveSubject.complete();

      // THEN
      expect(locationServiceFormService.getLocationService).toHaveBeenCalled();
      expect(locationServiceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILocationService>>();
      const locationService = { id: 123 };
      jest.spyOn(locationServiceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ locationService });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(locationServiceService.update).toHaveBeenCalled();
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
  });
});
