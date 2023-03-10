import dayjs from 'dayjs/esm';

export interface IPlrsCommission {
  id: number;
  commissionRate?: number | null;
  commissionStartDate?: dayjs.Dayjs | null;
  commissionEndDate?: dayjs.Dayjs | null;
}

export type NewPlrsCommission = Omit<IPlrsCommission, 'id'> & { id: null };
