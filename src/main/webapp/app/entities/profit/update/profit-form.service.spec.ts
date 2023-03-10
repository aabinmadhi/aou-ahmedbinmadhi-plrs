import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../profit.test-samples';

import { ProfitFormService } from './profit-form.service';

describe('Profit Form Service', () => {
  let service: ProfitFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProfitFormService);
  });

  describe('Service methods', () => {
    describe('createProfitFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProfitFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            userProfitAmountPerBooking: expect.any(Object),
            plrsProfitAmountPerBooking: expect.any(Object),
            userProfitAmountPerLocationService: expect.any(Object),
            plrsProfitAmountPerLocationService: expect.any(Object),
            userTotalProfit: expect.any(Object),
            plrsTotalProfit: expect.any(Object),
            booking: expect.any(Object),
          })
        );
      });

      it('passing IProfit should create a new form with FormGroup', () => {
        const formGroup = service.createProfitFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            userProfitAmountPerBooking: expect.any(Object),
            plrsProfitAmountPerBooking: expect.any(Object),
            userProfitAmountPerLocationService: expect.any(Object),
            plrsProfitAmountPerLocationService: expect.any(Object),
            userTotalProfit: expect.any(Object),
            plrsTotalProfit: expect.any(Object),
            booking: expect.any(Object),
          })
        );
      });
    });

    describe('getProfit', () => {
      it('should return NewProfit for default Profit initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProfitFormGroup(sampleWithNewData);

        const profit = service.getProfit(formGroup) as any;

        expect(profit).toMatchObject(sampleWithNewData);
      });

      it('should return NewProfit for empty Profit initial value', () => {
        const formGroup = service.createProfitFormGroup();

        const profit = service.getProfit(formGroup) as any;

        expect(profit).toMatchObject({});
      });

      it('should return IProfit', () => {
        const formGroup = service.createProfitFormGroup(sampleWithRequiredData);

        const profit = service.getProfit(formGroup) as any;

        expect(profit).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProfit should not enable id FormControl', () => {
        const formGroup = service.createProfitFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProfit should disable id FormControl', () => {
        const formGroup = service.createProfitFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
