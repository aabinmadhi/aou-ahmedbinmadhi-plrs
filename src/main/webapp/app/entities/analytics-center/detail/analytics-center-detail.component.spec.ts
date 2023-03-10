import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AnalyticsCenterDetailComponent } from './analytics-center-detail.component';

describe('AnalyticsCenter Management Detail Component', () => {
  let comp: AnalyticsCenterDetailComponent;
  let fixture: ComponentFixture<AnalyticsCenterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AnalyticsCenterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ analyticsCenter: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AnalyticsCenterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AnalyticsCenterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load analyticsCenter on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.analyticsCenter).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
