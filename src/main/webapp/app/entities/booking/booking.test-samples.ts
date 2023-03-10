import dayjs from 'dayjs/esm';

import { IBooking, NewBooking } from './booking.model';

export const sampleWithRequiredData: IBooking = {
  id: 79364,
  startTime: dayjs('2023-03-09T20:18'),
  endTime: dayjs('2023-03-10T06:53'),
};

export const sampleWithPartialData: IBooking = {
  id: 14827,
  startTime: dayjs('2023-03-09T16:21'),
  endTime: dayjs('2023-03-10T03:00'),
  totalPrice: 99017,
  isPaid: false,
};

export const sampleWithFullData: IBooking = {
  id: 56506,
  startTime: dayjs('2023-03-10T04:21'),
  endTime: dayjs('2023-03-09T18:13'),
  totalPrice: 36040,
  isPaid: false,
};

export const sampleWithNewData: NewBooking = {
  startTime: dayjs('2023-03-10T01:10'),
  endTime: dayjs('2023-03-09T22:12'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
