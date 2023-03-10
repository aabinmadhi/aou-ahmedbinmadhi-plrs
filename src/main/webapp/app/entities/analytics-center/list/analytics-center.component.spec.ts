import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { AnalyticsCenterService } from '../service/analytics-center.service';

import { AnalyticsCenterComponent } from './analytics-center.component';

describe('AnalyticsCenter Management Component', () => {
  let comp: AnalyticsCenterComponent;
  let fixture: ComponentFixture<AnalyticsCenterComponent>;
  let service: AnalyticsCenterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'analytics-center', component: AnalyticsCenterComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [AnalyticsCenterComponent],
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
      .overrideTemplate(AnalyticsCenterComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnalyticsCenterComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AnalyticsCenterService);

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
    expect(comp.analyticsCenters?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to analyticsCenterService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getAnalyticsCenterIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getAnalyticsCenterIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
