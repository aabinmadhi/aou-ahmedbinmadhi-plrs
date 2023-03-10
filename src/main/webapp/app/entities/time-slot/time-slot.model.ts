import dayjs from 'dayjs/esm';
import { ILocationService } from 'app/entities/location-service/location-service.model';

export interface ITimeSlot {
  id: number;
  startTime?: dayjs.Dayjs | null;
  endTime?: dayjs.Dayjs | null;
  isDeleted?: boolean | null;
  locationService?: Pick<ILocationService, 'id'> | null;
}

export type NewTimeSlot = Omit<ITimeSlot, 'id'> & { id: null };
