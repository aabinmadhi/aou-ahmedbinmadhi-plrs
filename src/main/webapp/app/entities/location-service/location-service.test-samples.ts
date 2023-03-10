import { City } from 'app/entities/enumerations/city.model';

import { ILocationService, NewLocationService } from './location-service.model';

export const sampleWithRequiredData: ILocationService = {
  id: 62194,
  locationServiceTitle: 'Developer Soft',
  locationServiceImage: '../fake-data/blob/hipster.png',
  locationServiceImageContentType: 'unknown',
  capacity: 24728,
  hourlyRate: 5739,
  whiteBoard: true,
  coffee: true,
  wiFi: true,
  monitor: true,
  pcOrLaptop: true,
  printer: true,
  city: City['DAMMAM'],
};

export const sampleWithPartialData: ILocationService = {
  id: 81041,
  locationServiceTitle: 'navigating',
  locationServiceImage: '../fake-data/blob/hipster.png',
  locationServiceImageContentType: 'unknown',
  capacity: 89794,
  hourlyRate: 22289,
  whiteBoard: false,
  coffee: true,
  wiFi: false,
  monitor: true,
  pcOrLaptop: false,
  printer: true,
  city: City['JEDDAH'],
};

export const sampleWithFullData: ILocationService = {
  id: 53541,
  locationServiceTitle: 'robust Quality Accounts',
  locationServiceImage: '../fake-data/blob/hipster.png',
  locationServiceImageContentType: 'unknown',
  mapCoordinates: 'Rubber',
  locationDiscription: 'Table Cambridgeshire International',
  capacity: 96705,
  hourlyRate: 82316,
  whiteBoard: true,
  coffee: true,
  wiFi: true,
  monitor: false,
  pcOrLaptop: true,
  printer: true,
  city: City['DAMMAM'],
};

export const sampleWithNewData: NewLocationService = {
  locationServiceTitle: 'Dynamic Investor',
  locationServiceImage: '../fake-data/blob/hipster.png',
  locationServiceImageContentType: 'unknown',
  capacity: 85120,
  hourlyRate: 5096,
  whiteBoard: false,
  coffee: false,
  wiFi: true,
  monitor: false,
  pcOrLaptop: true,
  printer: true,
  city: City['MECCA'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
