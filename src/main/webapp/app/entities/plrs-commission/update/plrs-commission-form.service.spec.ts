import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../plrs-commission.test-samples';

import { PlrsCommissionFormService } from './plrs-commission-form.service';

describe('PlrsCommission Form Service', () => {
  let service: PlrsCommissionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlrsCommissionFormService);
  });

  describe('Service methods', () => {
    describe('createPlrsCommissionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPlrsCommissionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            commissionRate: expect.any(Object),
            commissionStartDate: expect.any(Object),
            commissionEndDate: expect.any(Object),
          })
        );
      });

      it('passing IPlrsCommission should create a new form with FormGroup', () => {
        const formGroup = service.createPlrsCommissionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            commissionRate: expect.any(Object),
            commissionStartDate: expect.any(Object),
            commissionEndDate: expect.any(Object),
          })
        );
      });
    });

    describe('getPlrsCommission', () => {
      it('should return NewPlrsCommission for default PlrsCommission initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPlrsCommissionFormGroup(sampleWithNewData);

        const plrsCommission = service.getPlrsCommission(formGroup) as any;

        expect(plrsCommission).toMatchObject(sampleWithNewData);
      });

      it('should return NewPlrsCommission for empty PlrsCommission initial value', () => {
        const formGroup = service.createPlrsCommissionFormGroup();

        const plrsCommission = service.getPlrsCommission(formGroup) as any;

        expect(plrsCommission).toMatchObject({});
      });

      it('should return IPlrsCommission', () => {
        const formGroup = service.createPlrsCommissionFormGroup(sampleWithRequiredData);

        const plrsCommission = service.getPlrsCommission(formGroup) as any;

        expect(plrsCommission).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPlrsCommission should not enable id FormControl', () => {
        const formGroup = service.createPlrsCommissionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPlrsCommission should disable id FormControl', () => {
        const formGroup = service.createPlrsCommissionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
