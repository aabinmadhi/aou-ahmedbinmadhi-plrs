import dayjs from 'dayjs/esm';

import { IBooking, NewBooking } from './booking.model';

export const sampleWithRequiredData: IBooking = {
  id: 79364,
  startTime: dayjs('2023-03-09T20:59'),
  endTime: dayjs('2023-03-10T07:34'),
};

export const sampleWithPartialData: IBooking = {
  id: 14827,
  startTime: dayjs('2023-03-09T17:03'),
  endTime: dayjs('2023-03-10T03:41'),
  totalPrice: 99017,
  isPaid: false,
};

export const sampleWithFullData: IBooking = {
  id: 56506,
  startTime: dayjs('2023-03-10T05:02'),
  endTime: dayjs('2023-03-09T18:55'),
  totalPrice: 36040,
  isPaid: false,
};

export const sampleWithNewData: NewBooking = {
  startTime: dayjs('2023-03-10T01:52'),
  endTime: dayjs('2023-03-09T22:53'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
