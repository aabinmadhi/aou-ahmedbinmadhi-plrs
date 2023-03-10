import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPlrsCommission } from '../plrs-commission.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../plrs-commission.test-samples';

import { PlrsCommissionService, RestPlrsCommission } from './plrs-commission.service';

const requireRestSample: RestPlrsCommission = {
  ...sampleWithRequiredData,
  commissionStartDate: sampleWithRequiredData.commissionStartDate?.toJSON(),
  commissionEndDate: sampleWithRequiredData.commissionEndDate?.toJSON(),
};

describe('PlrsCommission Service', () => {
  let service: PlrsCommissionService;
  let httpMock: HttpTestingController;
  let expectedResult: IPlrsCommission | IPlrsCommission[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PlrsCommissionService);
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

    it('should create a PlrsCommission', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const plrsCommission = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(plrsCommission).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PlrsCommission', () => {
      const plrsCommission = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(plrsCommission).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PlrsCommission', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PlrsCommission', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PlrsCommission', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPlrsCommissionToCollectionIfMissing', () => {
      it('should add a PlrsCommission to an empty array', () => {
        const plrsCommission: IPlrsCommission = sampleWithRequiredData;
        expectedResult = service.addPlrsCommissionToCollectionIfMissing([], plrsCommission);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(plrsCommission);
      });

      it('should not add a PlrsCommission to an array that contains it', () => {
        const plrsCommission: IPlrsCommission = sampleWithRequiredData;
        const plrsCommissionCollection: IPlrsCommission[] = [
          {
            ...plrsCommission,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPlrsCommissionToCollectionIfMissing(plrsCommissionCollection, plrsCommission);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PlrsCommission to an array that doesn't contain it", () => {
        const plrsCommission: IPlrsCommission = sampleWithRequiredData;
        const plrsCommissionCollection: IPlrsCommission[] = [sampleWithPartialData];
        expectedResult = service.addPlrsCommissionToCollectionIfMissing(plrsCommissionCollection, plrsCommission);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(plrsCommission);
      });

      it('should add only unique PlrsCommission to an array', () => {
        const plrsCommissionArray: IPlrsCommission[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const plrsCommissionCollection: IPlrsCommission[] = [sampleWithRequiredData];
        expectedResult = service.addPlrsCommissionToCollectionIfMissing(plrsCommissionCollection, ...plrsCommissionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const plrsCommission: IPlrsCommission = sampleWithRequiredData;
        const plrsCommission2: IPlrsCommission = sampleWithPartialData;
        expectedResult = service.addPlrsCommissionToCollectionIfMissing([], plrsCommission, plrsCommission2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(plrsCommission);
        expect(expectedResult).toContain(plrsCommission2);
      });

      it('should accept null and undefined values', () => {
        const plrsCommission: IPlrsCommission = sampleWithRequiredData;
        expectedResult = service.addPlrsCommissionToCollectionIfMissing([], null, plrsCommission, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(plrsCommission);
      });

      it('should return initial array if no PlrsCommission is added', () => {
        const plrsCommissionCollection: IPlrsCommission[] = [sampleWithRequiredData];
        expectedResult = service.addPlrsCommissionToCollectionIfMissing(plrsCommissionCollection, undefined, null);
        expect(expectedResult).toEqual(plrsCommissionCollection);
      });
    });

    describe('comparePlrsCommission', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePlrsCommission(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePlrsCommission(entity1, entity2);
        const compareResult2 = service.comparePlrsCommission(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePlrsCommission(entity1, entity2);
        const compareResult2 = service.comparePlrsCommission(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePlrsCommission(entity1, entity2);
        const compareResult2 = service.comparePlrsCommission(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
