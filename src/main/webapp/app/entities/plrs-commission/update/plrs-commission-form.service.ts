import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPlrsCommission, NewPlrsCommission } from '../plrs-commission.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPlrsCommission for edit and NewPlrsCommissionFormGroupInput for create.
 */
type PlrsCommissionFormGroupInput = IPlrsCommission | PartialWithRequiredKeyOf<NewPlrsCommission>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPlrsCommission | NewPlrsCommission> = Omit<T, 'commissionStartDate' | 'commissionEndDate'> & {
  commissionStartDate?: string | null;
  commissionEndDate?: string | null;
};

type PlrsCommissionFormRawValue = FormValueOf<IPlrsCommission>;

type NewPlrsCommissionFormRawValue = FormValueOf<NewPlrsCommission>;

type PlrsCommissionFormDefaults = Pick<NewPlrsCommission, 'id' | 'commissionStartDate' | 'commissionEndDate'>;

type PlrsCommissionFormGroupContent = {
  id: FormControl<PlrsCommissionFormRawValue['id'] | NewPlrsCommission['id']>;
  commissionRate: FormControl<PlrsCommissionFormRawValue['commissionRate']>;
  commissionStartDate: FormControl<PlrsCommissionFormRawValue['commissionStartDate']>;
  commissionEndDate: FormControl<PlrsCommissionFormRawValue['commissionEndDate']>;
};

export type PlrsCommissionFormGroup = FormGroup<PlrsCommissionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PlrsCommissionFormService {
  createPlrsCommissionFormGroup(plrsCommission: PlrsCommissionFormGroupInput = { id: null }): PlrsCommissionFormGroup {
    const plrsCommissionRawValue = this.convertPlrsCommissionToPlrsCommissionRawValue({
      ...this.getFormDefaults(),
      ...plrsCommission,
    });
    return new FormGroup<PlrsCommissionFormGroupContent>({
      id: new FormControl(
        { value: plrsCommissionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      commissionRate: new FormControl(plrsCommissionRawValue.commissionRate, {
        validators: [Validators.required, Validators.min(0), Validators.max(100)],
      }),
      commissionStartDate: new FormControl(plrsCommissionRawValue.commissionStartDate),
      commissionEndDate: new FormControl(plrsCommissionRawValue.commissionEndDate),
    });
  }

  getPlrsCommission(form: PlrsCommissionFormGroup): IPlrsCommission | NewPlrsCommission {
    return this.convertPlrsCommissionRawValueToPlrsCommission(
      form.getRawValue() as PlrsCommissionFormRawValue | NewPlrsCommissionFormRawValue
    );
  }

  resetForm(form: PlrsCommissionFormGroup, plrsCommission: PlrsCommissionFormGroupInput): void {
    const plrsCommissionRawValue = this.convertPlrsCommissionToPlrsCommissionRawValue({ ...this.getFormDefaults(), ...plrsCommission });
    form.reset(
      {
        ...plrsCommissionRawValue,
        id: { value: plrsCommissionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PlrsCommissionFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      commissionStartDate: currentTime,
      commissionEndDate: currentTime,
    };
  }

  private convertPlrsCommissionRawValueToPlrsCommission(
    rawPlrsCommission: PlrsCommissionFormRawValue | NewPlrsCommissionFormRawValue
  ): IPlrsCommission | NewPlrsCommission {
    return {
      ...rawPlrsCommission,
      commissionStartDate: dayjs(rawPlrsCommission.commissionStartDate, DATE_TIME_FORMAT),
      commissionEndDate: dayjs(rawPlrsCommission.commissionEndDate, DATE_TIME_FORMAT),
    };
  }

  private convertPlrsCommissionToPlrsCommissionRawValue(
    plrsCommission: IPlrsCommission | (Partial<NewPlrsCommission> & PlrsCommissionFormDefaults)
  ): PlrsCommissionFormRawValue | PartialWithRequiredKeyOf<NewPlrsCommissionFormRawValue> {
    return {
      ...plrsCommission,
      commissionStartDate: plrsCommission.commissionStartDate ? plrsCommission.commissionStartDate.format(DATE_TIME_FORMAT) : undefined,
      commissionEndDate: plrsCommission.commissionEndDate ? plrsCommission.commissionEndDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
