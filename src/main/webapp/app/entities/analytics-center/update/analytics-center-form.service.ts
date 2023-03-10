import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAnalyticsCenter, NewAnalyticsCenter } from '../analytics-center.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAnalyticsCenter for edit and NewAnalyticsCenterFormGroupInput for create.
 */
type AnalyticsCenterFormGroupInput = IAnalyticsCenter | PartialWithRequiredKeyOf<NewAnalyticsCenter>;

type AnalyticsCenterFormDefaults = Pick<NewAnalyticsCenter, 'id'>;

type AnalyticsCenterFormGroupContent = {
  id: FormControl<IAnalyticsCenter['id'] | NewAnalyticsCenter['id']>;
  totalRevenuePerLProvider: FormControl<IAnalyticsCenter['totalRevenuePerLProvider']>;
  numberOfLocations: FormControl<IAnalyticsCenter['numberOfLocations']>;
  countOfBookingPerLocationService: FormControl<IAnalyticsCenter['countOfBookingPerLocationService']>;
  totalRevenuePerLocationService: FormControl<IAnalyticsCenter['totalRevenuePerLocationService']>;
};

export type AnalyticsCenterFormGroup = FormGroup<AnalyticsCenterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AnalyticsCenterFormService {
  createAnalyticsCenterFormGroup(analyticsCenter: AnalyticsCenterFormGroupInput = { id: null }): AnalyticsCenterFormGroup {
    const analyticsCenterRawValue = {
      ...this.getFormDefaults(),
      ...analyticsCenter,
    };
    return new FormGroup<AnalyticsCenterFormGroupContent>({
      id: new FormControl(
        { value: analyticsCenterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      totalRevenuePerLProvider: new FormControl(analyticsCenterRawValue.totalRevenuePerLProvider),
      numberOfLocations: new FormControl(analyticsCenterRawValue.numberOfLocations),
      countOfBookingPerLocationService: new FormControl(analyticsCenterRawValue.countOfBookingPerLocationService),
      totalRevenuePerLocationService: new FormControl(analyticsCenterRawValue.totalRevenuePerLocationService),
    });
  }

  getAnalyticsCenter(form: AnalyticsCenterFormGroup): IAnalyticsCenter | NewAnalyticsCenter {
    return form.getRawValue() as IAnalyticsCenter | NewAnalyticsCenter;
  }

  resetForm(form: AnalyticsCenterFormGroup, analyticsCenter: AnalyticsCenterFormGroupInput): void {
    const analyticsCenterRawValue = { ...this.getFormDefaults(), ...analyticsCenter };
    form.reset(
      {
        ...analyticsCenterRawValue,
        id: { value: analyticsCenterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AnalyticsCenterFormDefaults {
    return {
      id: null,
    };
  }
}
