import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProfit, NewProfit } from '../profit.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProfit for edit and NewProfitFormGroupInput for create.
 */
type ProfitFormGroupInput = IProfit | PartialWithRequiredKeyOf<NewProfit>;

type ProfitFormDefaults = Pick<NewProfit, 'id'>;

type ProfitFormGroupContent = {
  id: FormControl<IProfit['id'] | NewProfit['id']>;
  userProfitAmountPerBooking: FormControl<IProfit['userProfitAmountPerBooking']>;
  plrsProfitAmountPerBooking: FormControl<IProfit['plrsProfitAmountPerBooking']>;
  userProfitAmountPerLocationService: FormControl<IProfit['userProfitAmountPerLocationService']>;
  plrsProfitAmountPerLocationService: FormControl<IProfit['plrsProfitAmountPerLocationService']>;
  userTotalProfit: FormControl<IProfit['userTotalProfit']>;
  plrsTotalProfit: FormControl<IProfit['plrsTotalProfit']>;
  booking: FormControl<IProfit['booking']>;
};

export type ProfitFormGroup = FormGroup<ProfitFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProfitFormService {
  createProfitFormGroup(profit: ProfitFormGroupInput = { id: null }): ProfitFormGroup {
    const profitRawValue = {
      ...this.getFormDefaults(),
      ...profit,
    };
    return new FormGroup<ProfitFormGroupContent>({
      id: new FormControl(
        { value: profitRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      userProfitAmountPerBooking: new FormControl(profitRawValue.userProfitAmountPerBooking),
      plrsProfitAmountPerBooking: new FormControl(profitRawValue.plrsProfitAmountPerBooking),
      userProfitAmountPerLocationService: new FormControl(profitRawValue.userProfitAmountPerLocationService),
      plrsProfitAmountPerLocationService: new FormControl(profitRawValue.plrsProfitAmountPerLocationService),
      userTotalProfit: new FormControl(profitRawValue.userTotalProfit),
      plrsTotalProfit: new FormControl(profitRawValue.plrsTotalProfit),
      booking: new FormControl(profitRawValue.booking),
    });
  }

  getProfit(form: ProfitFormGroup): IProfit | NewProfit {
    return form.getRawValue() as IProfit | NewProfit;
  }

  resetForm(form: ProfitFormGroup, profit: ProfitFormGroupInput): void {
    const profitRawValue = { ...this.getFormDefaults(), ...profit };
    form.reset(
      {
        ...profitRawValue,
        id: { value: profitRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProfitFormDefaults {
    return {
      id: null,
    };
  }
}
