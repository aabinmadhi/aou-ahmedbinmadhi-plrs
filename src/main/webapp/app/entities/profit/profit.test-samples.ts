import { IProfit, NewProfit } from './profit.model';

export const sampleWithRequiredData: IProfit = {
  id: 98935,
};

export const sampleWithPartialData: IProfit = {
  id: 1720,
  userProfitAmountPerBooking: 13875,
  plrsProfitAmountPerBooking: 90074,
  userProfitAmountPerLocationService: 12831,
};

export const sampleWithFullData: IProfit = {
  id: 48421,
  userProfitAmountPerBooking: 78154,
  plrsProfitAmountPerBooking: 3647,
  userProfitAmountPerLocationService: 59234,
  plrsProfitAmountPerLocationService: 20921,
  userTotalProfit: 90005,
  plrsTotalProfit: 89079,
};

export const sampleWithNewData: NewProfit = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
