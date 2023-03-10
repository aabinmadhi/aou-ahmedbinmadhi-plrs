import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAnalyticsCenter } from '../analytics-center.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../analytics-center.test-samples';

import { AnalyticsCenterService } from './analytics-center.service';

const requireRestSample: IAnalyticsCenter = {
  ...sampleWithRequiredData,
};

describe('AnalyticsCenter Service', () => {
  let service: AnalyticsCenterService;
  let httpMock: HttpTestingController;
  let expectedResult: IAnalyticsCenter | IAnalyticsCenter[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AnalyticsCenterService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a AnalyticsCenter', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const analyticsCenter = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(analyticsCenter).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AnalyticsCenter', () => {
      const analyticsCenter = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(analyticsCenter).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AnalyticsCenter', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AnalyticsCenter', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AnalyticsCenter', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAnalyticsCenterToCollectionIfMissing', () => {
      it('should add a AnalyticsCenter to an empty array', () => {
        const analyticsCenter: IAnalyticsCenter = sampleWithRequiredData;
        expectedResult = service.addAnalyticsCenterToCollectionIfMissing([], analyticsCenter);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(analyticsCenter);
      });

      it('should not add a AnalyticsCenter to an array that contains it', () => {
        const analyticsCenter: IAnalyticsCenter = sampleWithRequiredData;
        const analyticsCenterCollection: IAnalyticsCenter[] = [
          {
            ...analyticsCenter,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAnalyticsCenterToCollectionIfMissing(analyticsCenterCollection, analyticsCenter);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AnalyticsCenter to an array that doesn't contain it", () => {
        const analyticsCenter: IAnalyticsCenter = sampleWithRequiredData;
        const analyticsCenterCollection: IAnalyticsCenter[] = [sampleWithPartialData];
        expectedResult = service.addAnalyticsCenterToCollectionIfMissing(analyticsCenterCollection, analyticsCenter);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(analyticsCenter);
      });

      it('should add only unique AnalyticsCenter to an array', () => {
        const analyticsCenterArray: IAnalyticsCenter[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const analyticsCenterCollection: IAnalyticsCenter[] = [sampleWithRequiredData];
        expectedResult = service.addAnalyticsCenterToCollectionIfMissing(analyticsCenterCollection, ...analyticsCenterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const analyticsCenter: IAnalyticsCenter = sampleWithRequiredData;
        const analyticsCenter2: IAnalyticsCenter = sampleWithPartialData;
        expectedResult = service.addAnalyticsCenterToCollectionIfMissing([], analyticsCenter, analyticsCenter2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(analyticsCenter);
        expect(expectedResult).toContain(analyticsCenter2);
      });

      it('should accept null and undefined values', () => {
        const analyticsCenter: IAnalyticsCenter = sampleWithRequiredData;
        expectedResult = service.addAnalyticsCenterToCollectionIfMissing([], null, analyticsCenter, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(analyticsCenter);
      });

      it('should return initial array if no AnalyticsCenter is added', () => {
        const analyticsCenterCollection: IAnalyticsCenter[] = [sampleWithRequiredData];
        expectedResult = service.addAnalyticsCenterToCollectionIfMissing(analyticsCenterCollection, undefined, null);
        expect(expectedResult).toEqual(analyticsCenterCollection);
      });
    });

    describe('compareAnalyticsCenter', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAnalyticsCenter(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAnalyticsCenter(entity1, entity2);
        const compareResult2 = service.compareAnalyticsCenter(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAnalyticsCenter(entity1, entity2);
        const compareResult2 = service.compareAnalyticsCenter(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAnalyticsCenter(entity1, entity2);
        const compareResult2 = service.compareAnalyticsCenter(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
