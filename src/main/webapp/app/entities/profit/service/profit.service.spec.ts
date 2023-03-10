import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProfit } from '../profit.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../profit.test-samples';

import { ProfitService } from './profit.service';

const requireRestSample: IProfit = {
  ...sampleWithRequiredData,
};

describe('Profit Service', () => {
  let service: ProfitService;
  let httpMock: HttpTestingController;
  let expectedResult: IProfit | IProfit[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProfitService);
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

    it('should create a Profit', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const profit = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(profit).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Profit', () => {
      const profit = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(profit).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Profit', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Profit', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Profit', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProfitToCollectionIfMissing', () => {
      it('should add a Profit to an empty array', () => {
        const profit: IProfit = sampleWithRequiredData;
        expectedResult = service.addProfitToCollectionIfMissing([], profit);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(profit);
      });

      it('should not add a Profit to an array that contains it', () => {
        const profit: IProfit = sampleWithRequiredData;
        const profitCollection: IProfit[] = [
          {
            ...profit,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProfitToCollectionIfMissing(profitCollection, profit);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Profit to an array that doesn't contain it", () => {
        const profit: IProfit = sampleWithRequiredData;
        const profitCollection: IProfit[] = [sampleWithPartialData];
        expectedResult = service.addProfitToCollectionIfMissing(profitCollection, profit);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(profit);
      });

      it('should add only unique Profit to an array', () => {
        const profitArray: IProfit[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const profitCollection: IProfit[] = [sampleWithRequiredData];
        expectedResult = service.addProfitToCollectionIfMissing(profitCollection, ...profitArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const profit: IProfit = sampleWithRequiredData;
        const profit2: IProfit = sampleWithPartialData;
        expectedResult = service.addProfitToCollectionIfMissing([], profit, profit2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(profit);
        expect(expectedResult).toContain(profit2);
      });

      it('should accept null and undefined values', () => {
        const profit: IProfit = sampleWithRequiredData;
        expectedResult = service.addProfitToCollectionIfMissing([], null, profit, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(profit);
      });

      it('should return initial array if no Profit is added', () => {
        const profitCollection: IProfit[] = [sampleWithRequiredData];
        expectedResult = service.addProfitToCollectionIfMissing(profitCollection, undefined, null);
        expect(expectedResult).toEqual(profitCollection);
      });
    });

    describe('compareProfit', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProfit(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProfit(entity1, entity2);
        const compareResult2 = service.compareProfit(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProfit(entity1, entity2);
        const compareResult2 = service.compareProfit(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProfit(entity1, entity2);
        const compareResult2 = service.compareProfit(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
