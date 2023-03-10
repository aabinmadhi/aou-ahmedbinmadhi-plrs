import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILocationService } from '../location-service.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../location-service.test-samples';

import { LocationServiceService } from './location-service.service';

const requireRestSample: ILocationService = {
  ...sampleWithRequiredData,
};

describe('LocationService Service', () => {
  let service: LocationServiceService;
  let httpMock: HttpTestingController;
  let expectedResult: ILocationService | ILocationService[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LocationServiceService);
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

    it('should create a LocationService', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const locationService = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(locationService).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LocationService', () => {
      const locationService = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(locationService).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LocationService', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LocationService', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LocationService', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLocationServiceToCollectionIfMissing', () => {
      it('should add a LocationService to an empty array', () => {
        const locationService: ILocationService = sampleWithRequiredData;
        expectedResult = service.addLocationServiceToCollectionIfMissing([], locationService);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(locationService);
      });

      it('should not add a LocationService to an array that contains it', () => {
        const locationService: ILocationService = sampleWithRequiredData;
        const locationServiceCollection: ILocationService[] = [
          {
            ...locationService,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLocationServiceToCollectionIfMissing(locationServiceCollection, locationService);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LocationService to an array that doesn't contain it", () => {
        const locationService: ILocationService = sampleWithRequiredData;
        const locationServiceCollection: ILocationService[] = [sampleWithPartialData];
        expectedResult = service.addLocationServiceToCollectionIfMissing(locationServiceCollection, locationService);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(locationService);
      });

      it('should add only unique LocationService to an array', () => {
        const locationServiceArray: ILocationService[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const locationServiceCollection: ILocationService[] = [sampleWithRequiredData];
        expectedResult = service.addLocationServiceToCollectionIfMissing(locationServiceCollection, ...locationServiceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const locationService: ILocationService = sampleWithRequiredData;
        const locationService2: ILocationService = sampleWithPartialData;
        expectedResult = service.addLocationServiceToCollectionIfMissing([], locationService, locationService2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(locationService);
        expect(expectedResult).toContain(locationService2);
      });

      it('should accept null and undefined values', () => {
        const locationService: ILocationService = sampleWithRequiredData;
        expectedResult = service.addLocationServiceToCollectionIfMissing([], null, locationService, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(locationService);
      });

      it('should return initial array if no LocationService is added', () => {
        const locationServiceCollection: ILocationService[] = [sampleWithRequiredData];
        expectedResult = service.addLocationServiceToCollectionIfMissing(locationServiceCollection, undefined, null);
        expect(expectedResult).toEqual(locationServiceCollection);
      });
    });

    describe('compareLocationService', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLocationService(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLocationService(entity1, entity2);
        const compareResult2 = service.compareLocationService(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLocationService(entity1, entity2);
        const compareResult2 = service.compareLocationService(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLocationService(entity1, entity2);
        const compareResult2 = service.compareLocationService(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
