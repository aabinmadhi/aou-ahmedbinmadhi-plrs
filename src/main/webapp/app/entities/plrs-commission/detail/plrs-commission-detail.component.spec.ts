import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlrsCommissionDetailComponent } from './plrs-commission-detail.component';

describe('PlrsCommission Management Detail Component', () => {
  let comp: PlrsCommissionDetailComponent;
  let fixture: ComponentFixture<PlrsCommissionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PlrsCommissionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ plrsCommission: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PlrsCommissionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PlrsCommissionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load plrsCommission on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.plrsCommission).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
