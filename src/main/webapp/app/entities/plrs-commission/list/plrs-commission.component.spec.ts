import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PlrsCommissionService } from '../service/plrs-commission.service';

import { PlrsCommissionComponent } from './plrs-commission.component';

describe('PlrsCommission Management Component', () => {
  let comp: PlrsCommissionComponent;
  let fixture: ComponentFixture<PlrsCommissionComponent>;
  let service: PlrsCommissionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'plrs-commission', component: PlrsCommissionComponent }]), HttpClientTestingModule],
      declarations: [PlrsCommissionComponent],
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
      .overrideTemplate(PlrsCommissionComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlrsCommissionComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PlrsCommissionService);

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
    expect(comp.plrsCommissions?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to plrsCommissionService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPlrsCommissionIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPlrsCommissionIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
