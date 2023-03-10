export interface IAnalyticsCenter {
  id: number;
  totalRevenuePerLProvider?: number | null;
  numberOfLocations?: number | null;
  countOfBookingPerLocationService?: number | null;
  totalRevenuePerLocationService?: number | null;
}

export type NewAnalyticsCenter = Omit<IAnalyticsCenter, 'id'> & { id: null };
