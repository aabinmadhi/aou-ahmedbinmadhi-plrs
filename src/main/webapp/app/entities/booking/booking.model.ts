import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { ILocationService } from 'app/entities/location-service/location-service.model';

export interface IBooking {
  id: number;
  startTime?: dayjs.Dayjs | null;
  endTime?: dayjs.Dayjs | null;
  totalPrice?: number | null;
  isPaid?: boolean | null;
  internalUser?: Pick<IUser, 'id'> | null;
  locationService?: Pick<ILocationService, 'id'> | null;
}

export type NewBooking = Omit<IBooking, 'id'> & { id: null };
