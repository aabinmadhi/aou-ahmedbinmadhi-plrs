import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TimeSlotService } from '../service/time-slot.service';

import { TimeSlotComponent } from './time-slot.component';

describe('TimeSlot Management Component', () => {
  let comp: TimeSlotComponent;
  let fixture: ComponentFixture<TimeSlotComponent>;
  let service: TimeSlotService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'time-slot', component: TimeSlotComponent }]), HttpClientTestingModule],
      declarations: [TimeSlotComponent],
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
      .overrideTemplate(TimeSlotComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TimeSlotComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TimeSlotService);

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
    expect(comp.timeSlots?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to timeSlotService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getTimeSlotIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getTimeSlotIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
