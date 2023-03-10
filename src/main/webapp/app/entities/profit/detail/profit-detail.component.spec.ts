import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProfitDetailComponent } from './profit-detail.component';

describe('Profit Management Detail Component', () => {
  let comp: ProfitDetailComponent;
  let fixture: ComponentFixture<ProfitDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProfitDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ profit: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProfitDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProfitDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load profit on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.profit).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
