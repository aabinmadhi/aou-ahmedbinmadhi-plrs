import { IAnalyticsCenter, NewAnalyticsCenter } from './analytics-center.model';

export const sampleWithRequiredData: IAnalyticsCenter = {
  id: 63201,
};

export const sampleWithPartialData: IAnalyticsCenter = {
  id: 30473,
  numberOfLocations: 12794,
  countOfBookingPerLocationService: 75083,
};

export const sampleWithFullData: IAnalyticsCenter = {
  id: 15134,
  totalRevenuePerLProvider: 1077,
  numberOfLocations: 61115,
  countOfBookingPerLocationService: 67286,
  totalRevenuePerLocationService: 40677,
};

export const sampleWithNewData: NewAnalyticsCenter = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
