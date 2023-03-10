import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { LocationServiceService } from '../service/location-service.service';

import { LocationServiceComponent } from './location-service.component';

describe('LocationService Management Component', () => {
  let comp: LocationServiceComponent;
  let fixture: ComponentFixture<LocationServiceComponent>;
  let service: LocationServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'location-service', component: LocationServiceComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [LocationServiceComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(LocationServiceComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LocationServiceComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LocationServiceService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.locationServices?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to locationServiceService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getLocationServiceIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getLocationServiceIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
