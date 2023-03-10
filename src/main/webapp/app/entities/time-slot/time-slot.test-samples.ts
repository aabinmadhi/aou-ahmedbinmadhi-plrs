import dayjs from 'dayjs/esm';

import { ITimeSlot, NewTimeSlot } from './time-slot.model';

export const sampleWithRequiredData: ITimeSlot = {
  id: 5308,
  startTime: dayjs('2023-03-09T14:49'),
  endTime: dayjs('2023-03-09T21:22'),
};

export const sampleWithPartialData: ITimeSlot = {
  id: 16492,
  startTime: dayjs('2023-03-10T10:58'),
  endTime: dayjs('2023-03-09T17:16'),
};

export const sampleWithFullData: ITimeSlot = {
  id: 73145,
  startTime: dayjs('2023-03-10T09:24'),
  endTime: dayjs('2023-03-10T04:10'),
  isDeleted: false,
};

export const sampleWithNewData: NewTimeSlot = {
  startTime: dayjs('2023-03-09T18:04'),
  endTime: dayjs('2023-03-09T21:50'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
