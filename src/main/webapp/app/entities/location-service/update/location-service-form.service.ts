import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILocationService, NewLocationService } from '../location-service.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILocationService for edit and NewLocationServiceFormGroupInput for create.
 */
type LocationServiceFormGroupInput = ILocationService | PartialWithRequiredKeyOf<NewLocationService>;

type LocationServiceFormDefaults = Pick<NewLocationService, 'id' | 'whiteBoard' | 'coffee' | 'wiFi' | 'monitor' | 'pcOrLaptop' | 'printer'>;

type LocationServiceFormGroupContent = {
  id: FormControl<ILocationService['id'] | NewLocationService['id']>;
  locationServiceTitle: FormControl<ILocationService['locationServiceTitle']>;
  locationServiceImage: FormControl<ILocationService['locationServiceImage']>;
  locationServiceImageContentType: FormControl<ILocationService['locationServiceImageContentType']>;
  mapCoordinates: FormControl<ILocationService['mapCoordinates']>;
  locationDiscription: FormControl<ILocationService['locationDiscription']>;
  capacity: FormControl<ILocationService['capacity']>;
  hourlyRate: FormControl<ILocationService['hourlyRate']>;
  whiteBoard: FormControl<ILocationService['whiteBoard']>;
  coffee: FormControl<ILocationService['coffee']>;
  wiFi: FormControl<ILocationService['wiFi']>;
  monitor: FormControl<ILocationService['monitor']>;
  pcOrLaptop: FormControl<ILocationService['pcOrLaptop']>;
  printer: FormControl<ILocationService['printer']>;
  city: FormControl<ILocationService['city']>;
  internalUser: FormControl<ILocationService['internalUser']>;
};

export type LocationServiceFormGroup = FormGroup<LocationServiceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LocationServiceFormService {
  createLocationServiceFormGroup(locationService: LocationServiceFormGroupInput = { id: null }): LocationServiceFormGroup {
    const locationServiceRawValue = {
      ...this.getFormDefaults(),
      ...locationService,
    };
    return new FormGroup<LocationServiceFormGroupContent>({
      id: new FormControl(
        { value: locationServiceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      locationServiceTitle: new FormControl(locationServiceRawValue.locationServiceTitle, {
        validators: [Validators.required],
      }),
      locationServiceImage: new FormControl(locationServiceRawValue.locationServiceImage, {
        validators: [Validators.required],
      }),
      locationServiceImageContentType: new FormControl(locationServiceRawValue.locationServiceImageContentType),
      mapCoordinates: new FormControl(locationServiceRawValue.mapCoordinates),
      locationDiscription: new FormControl(locationServiceRawValue.locationDiscription),
      capacity: new FormControl(locationServiceRawValue.capacity, {
        validators: [Validators.required],
      }),
      hourlyRate: new FormControl(locationServiceRawValue.hourlyRate, {
        validators: [Validators.required],
      }),
      whiteBoard: new FormControl(locationServiceRawValue.whiteBoard, {
        validators: [Validators.required],
      }),
      coffee: new FormControl(locationServiceRawValue.coffee, {
        validators: [Validators.required],
      }),
      wiFi: new FormControl(locationServiceRawValue.wiFi, {
        validators: [Validators.required],
      }),
      monitor: new FormControl(locationServiceRawValue.monitor, {
        validators: [Validators.required],
      }),
      pcOrLaptop: new FormControl(locationServiceRawValue.pcOrLaptop, {
        validators: [Validators.required],
      }),
      printer: new FormControl(locationServiceRawValue.printer, {
        validators: [Validators.required],
      }),
      city: new FormControl(locationServiceRawValue.city, {
        validators: [Validators.required],
      }),
      internalUser: new FormControl(locationServiceRawValue.internalUser),
    });
  }

  getLocationService(form: LocationServiceFormGroup): ILocationService | NewLocationService {
    return form.getRawValue() as ILocationService | NewLocationService;
  }

  resetForm(form: LocationServiceFormGroup, locationService: LocationServiceFormGroupInput): void {
    const locationServiceRawValue = { ...this.getFormDefaults(), ...locationService };
    form.reset(
      {
        ...locationServiceRawValue,
        id: { value: locationServiceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LocationServiceFormDefaults {
    return {
      id: null,
      whiteBoard: false,
      coffee: false,
      wiFi: false,
      monitor: false,
      pcOrLaptop: false,
      printer: false,
    };
  }
}
