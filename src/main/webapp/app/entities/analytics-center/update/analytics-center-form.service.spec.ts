import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../analytics-center.test-samples';

import { AnalyticsCenterFormService } from './analytics-center-form.service';

describe('AnalyticsCenter Form Service', () => {
  let service: AnalyticsCenterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnalyticsCenterFormService);
  });

  describe('Service methods', () => {
    describe('createAnalyticsCenterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAnalyticsCenterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            totalRevenuePerLProvider: expect.any(Object),
            numberOfLocations: expect.any(Object),
            countOfBookingPerLocationService: expect.any(Object),
            totalRevenuePerLocationService: expect.any(Object),
          })
        );
      });

      it('passing IAnalyticsCenter should create a new form with FormGroup', () => {
        const formGroup = service.createAnalyticsCenterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            totalRevenuePerLProvider: expect.any(Object),
            numberOfLocations: expect.any(Object),
            countOfBookingPerLocationService: expect.any(Object),
            totalRevenuePerLocationService: expect.any(Object),
          })
        );
      });
    });

    describe('getAnalyticsCenter', () => {
      it('should return NewAnalyticsCenter for default AnalyticsCenter initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAnalyticsCenterFormGroup(sampleWithNewData);

        const analyticsCenter = service.getAnalyticsCenter(formGroup) as any;

        expect(analyticsCenter).toMatchObject(sampleWithNewData);
      });

      it('should return NewAnalyticsCenter for empty AnalyticsCenter initial value', () => {
        const formGroup = service.createAnalyticsCenterFormGroup();

        const analyticsCenter = service.getAnalyticsCenter(formGroup) as any;

        expect(analyticsCenter).toMatchObject({});
      });

      it('should return IAnalyticsCenter', () => {
        const formGroup = service.createAnalyticsCenterFormGroup(sampleWithRequiredData);

        const analyticsCenter = service.getAnalyticsCenter(formGroup) as any;

        expect(analyticsCenter).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAnalyticsCenter should not enable id FormControl', () => {
        const formGroup = service.createAnalyticsCenterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAnalyticsCenter should disable id FormControl', () => {
        const formGroup = service.createAnalyticsCenterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
