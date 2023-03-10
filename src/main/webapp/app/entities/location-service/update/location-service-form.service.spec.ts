import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../location-service.test-samples';

import { LocationServiceFormService } from './location-service-form.service';

describe('LocationService Form Service', () => {
  let service: LocationServiceFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LocationServiceFormService);
  });

  describe('Service methods', () => {
    describe('createLocationServiceFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLocationServiceFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            locationServiceTitle: expect.any(Object),
            locationServiceImage: expect.any(Object),
            mapCoordinates: expect.any(Object),
            locationDiscription: expect.any(Object),
            capacity: expect.any(Object),
            hourlyRate: expect.any(Object),
            whiteBoard: expect.any(Object),
            coffee: expect.any(Object),
            wiFi: expect.any(Object),
            monitor: expect.any(Object),
            pcOrLaptop: expect.any(Object),
            printer: expect.any(Object),
            city: expect.any(Object),
            internalUser: expect.any(Object),
          })
        );
      });

      it('passing ILocationService should create a new form with FormGroup', () => {
        const formGroup = service.createLocationServiceFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            locationServiceTitle: expect.any(Object),
            locationServiceImage: expect.any(Object),
            mapCoordinates: expect.any(Object),
            locationDiscription: expect.any(Object),
            capacity: expect.any(Object),
            hourlyRate: expect.any(Object),
            whiteBoard: expect.any(Object),
            coffee: expect.any(Object),
            wiFi: expect.any(Object),
            monitor: expect.any(Object),
            pcOrLaptop: expect.any(Object),
            printer: expect.any(Object),
            city: expect.any(Object),
            internalUser: expect.any(Object),
          })
        );
      });
    });

    describe('getLocationService', () => {
      it('should return NewLocationService for default LocationService initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLocationServiceFormGroup(sampleWithNewData);

        const locationService = service.getLocationService(formGroup) as any;

        expect(locationService).toMatchObject(sampleWithNewData);
      });

      it('should return NewLocationService for empty LocationService initial value', () => {
        const formGroup = service.createLocationServiceFormGroup();

        const locationService = service.getLocationService(formGroup) as any;

        expect(locationService).toMatchObject({});
      });

      it('should return ILocationService', () => {
        const formGroup = service.createLocationServiceFormGroup(sampleWithRequiredData);

        const locationService = service.getLocationService(formGroup) as any;

        expect(locationService).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILocationService should not enable id FormControl', () => {
        const formGroup = service.createLocationServiceFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLocationService should disable id FormControl', () => {
        const formGroup = service.createLocationServiceFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
