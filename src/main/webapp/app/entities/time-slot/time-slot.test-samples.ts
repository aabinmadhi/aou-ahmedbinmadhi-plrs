import dayjs from 'dayjs/esm';

import { ITimeSlot, NewTimeSlot } from './time-slot.model';

export const sampleWithRequiredData: ITimeSlot = {
  id: 5308,
  startTime: dayjs('2023-03-09T14:53'),
  endTime: dayjs('2023-03-09T21:26'),
};

export const sampleWithPartialData: ITimeSlot = {
  id: 16492,
  startTime: dayjs('2023-03-10T11:02'),
  endTime: dayjs('2023-03-09T17:20'),
};

export const sampleWithFullData: ITimeSlot = {
  id: 73145,
  startTime: dayjs('2023-03-10T09:28'),
  endTime: dayjs('2023-03-10T04:14'),
  isDeleted: false,
};

export const sampleWithNewData: NewTimeSlot = {
  startTime: dayjs('2023-03-09T18:08'),
  endTime: dayjs('2023-03-09T21:54'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
