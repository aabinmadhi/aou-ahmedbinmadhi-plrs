import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AnalyticsCenterFormService } from './analytics-center-form.service';
import { AnalyticsCenterService } from '../service/analytics-center.service';
import { IAnalyticsCenter } from '../analytics-center.model';

import { AnalyticsCenterUpdateComponent } from './analytics-center-update.component';

describe('AnalyticsCenter Management Update Component', () => {
  let comp: AnalyticsCenterUpdateComponent;
  let fixture: ComponentFixture<AnalyticsCenterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let analyticsCenterFormService: AnalyticsCenterFormService;
  let analyticsCenterService: AnalyticsCenterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AnalyticsCenterUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AnalyticsCenterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnalyticsCenterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    analyticsCenterFormService = TestBed.inject(AnalyticsCenterFormService);
    analyticsCenterService = TestBed.inject(AnalyticsCenterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const analyticsCenter: IAnalyticsCenter = { id: 456 };

      activatedRoute.data = of({ analyticsCenter });
      comp.ngOnInit();

      expect(comp.analyticsCenter).toEqual(analyticsCenter);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnalyticsCenter>>();
      const analyticsCenter = { id: 123 };
      jest.spyOn(analyticsCenterFormService, 'getAnalyticsCenter').mockReturnValue(analyticsCenter);
      jest.spyOn(analyticsCenterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ analyticsCenter });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: analyticsCenter }));
      saveSubject.complete();

      // THEN
      expect(analyticsCenterFormService.getAnalyticsCenter).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(analyticsCenterService.update).toHaveBeenCalledWith(expect.objectContaining(analyticsCenter));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnalyticsCenter>>();
      const analyticsCenter = { id: 123 };
      jest.spyOn(analyticsCenterFormService, 'getAnalyticsCenter').mockReturnValue({ id: null });
      jest.spyOn(analyticsCenterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ analyticsCenter: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: analyticsCenter }));
      saveSubject.complete();

      // THEN
      expect(analyticsCenterFormService.getAnalyticsCenter).toHaveBeenCalled();
      expect(analyticsCenterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnalyticsCenter>>();
      const analyticsCenter = { id: 123 };
      jest.spyOn(analyticsCenterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ analyticsCenter });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(analyticsCenterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
