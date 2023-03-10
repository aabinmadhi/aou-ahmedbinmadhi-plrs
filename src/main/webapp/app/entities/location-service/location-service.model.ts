import { IUser } from 'app/entities/user/user.model';
import { City } from 'app/entities/enumerations/city.model';

export interface ILocationService {
  id: number;
  locationServiceTitle?: string | null;
  locationServiceImage?: string | null;
  locationServiceImageContentType?: string | null;
  mapCoordinates?: string | null;
  locationDiscription?: string | null;
  capacity?: number | null;
  hourlyRate?: number | null;
  whiteBoard?: boolean | null;
  coffee?: boolean | null;
  wiFi?: boolean | null;
  monitor?: boolean | null;
  pcOrLaptop?: boolean | null;
  printer?: boolean | null;
  city?: City | null;
  internalUser?: Pick<IUser, 'id'> | null;
}

export type NewLocationService = Omit<ILocationService, 'id'> & { id: null };
