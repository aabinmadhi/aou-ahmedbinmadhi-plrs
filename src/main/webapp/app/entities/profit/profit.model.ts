import { IBooking } from 'app/entities/booking/booking.model';

export interface IProfit {
  id: number;
  userProfitAmountPerBooking?: number | null;
  plrsProfitAmountPerBooking?: number | null;
  userProfitAmountPerLocationService?: number | null;
  plrsProfitAmountPerLocationService?: number | null;
  userTotalProfit?: number | null;
  plrsTotalProfit?: number | null;
  booking?: Pick<IBooking, 'id'> | null;
}

export type NewProfit = Omit<IProfit, 'id'> & { id: null };
