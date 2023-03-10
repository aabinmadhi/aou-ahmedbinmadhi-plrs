import dayjs from 'dayjs/esm';

import { IPlrsCommission, NewPlrsCommission } from './plrs-commission.model';

export const sampleWithRequiredData: IPlrsCommission = {
  id: 48780,
  commissionRate: 14,
};

export const sampleWithPartialData: IPlrsCommission = {
  id: 58855,
  commissionRate: 53,
  commissionEndDate: dayjs('2023-03-09T18:58'),
};

export const sampleWithFullData: IPlrsCommission = {
  id: 89705,
  commissionRate: 49,
  commissionStartDate: dayjs('2023-03-09T20:16'),
  commissionEndDate: dayjs('2023-03-10T08:43'),
};

export const sampleWithNewData: NewPlrsCommission = {
  commissionRate: 67,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
