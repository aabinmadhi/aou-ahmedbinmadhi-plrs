import dayjs from 'dayjs/esm';

import { ITimeSlot, NewTimeSlot } from './time-slot.model';

export const sampleWithRequiredData: ITimeSlot = {
  id: 5308,
  startTime: dayjs('2023-03-09T14:07'),
  endTime: dayjs('2023-03-09T20:41'),
};

export const sampleWithPartialData: ITimeSlot = {
  id: 16492,
  startTime: dayjs('2023-03-10T10:17'),
  endTime: dayjs('2023-03-09T16:34'),
};

export const sampleWithFullData: ITimeSlot = {
  id: 73145,
  startTime: dayjs('2023-03-10T08:42'),
  endTime: dayjs('2023-03-10T03:29'),
  isDeleted: false,
};

export const sampleWithNewData: NewTimeSlot = {
  startTime: dayjs('2023-03-09T17:22'),
  endTime: dayjs('2023-03-09T21:09'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
