import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../time-slot.test-samples';

import { TimeSlotFormService } from './time-slot-form.service';

describe('TimeSlot Form Service', () => {
  let service: TimeSlotFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TimeSlotFormService);
  });

  describe('Service methods', () => {
    describe('createTimeSlotFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTimeSlotFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            startTime: expect.any(Object),
            endTime: expect.any(Object),
            isDeleted: expect.any(Object),
            locationService: expect.any(Object),
          })
        );
      });

      it('passing ITimeSlot should create a new form with FormGroup', () => {
        const formGroup = service.createTimeSlotFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            startTime: expect.any(Object),
            endTime: expect.any(Object),
            isDeleted: expect.any(Object),
            locationService: expect.any(Object),
          })
        );
      });
    });

    describe('getTimeSlot', () => {
      it('should return NewTimeSlot for default TimeSlot initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTimeSlotFormGroup(sampleWithNewData);

        const timeSlot = service.getTimeSlot(formGroup) as any;

        expect(timeSlot).toMatchObject(sampleWithNewData);
      });

      it('should return NewTimeSlot for empty TimeSlot initial value', () => {
        const formGroup = service.createTimeSlotFormGroup();

        const timeSlot = service.getTimeSlot(formGroup) as any;

        expect(timeSlot).toMatchObject({});
      });

      it('should return ITimeSlot', () => {
        const formGroup = service.createTimeSlotFormGroup(sampleWithRequiredData);

        const timeSlot = service.getTimeSlot(formGroup) as any;

        expect(timeSlot).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITimeSlot should not enable id FormControl', () => {
        const formGroup = service.createTimeSlotFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTimeSlot should disable id FormControl', () => {
        const formGroup = service.createTimeSlotFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
