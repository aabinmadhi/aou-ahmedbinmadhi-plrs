import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PlrsCommissionFormService } from './plrs-commission-form.service';
import { PlrsCommissionService } from '../service/plrs-commission.service';
import { IPlrsCommission } from '../plrs-commission.model';

import { PlrsCommissionUpdateComponent } from './plrs-commission-update.component';

describe('PlrsCommission Management Update Component', () => {
  let comp: PlrsCommissionUpdateComponent;
  let fixture: ComponentFixture<PlrsCommissionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let plrsCommissionFormService: PlrsCommissionFormService;
  let plrsCommissionService: PlrsCommissionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PlrsCommissionUpdateComponent],
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
      .overrideTemplate(PlrsCommissionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlrsCommissionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    plrsCommissionFormService = TestBed.inject(PlrsCommissionFormService);
    plrsCommissionService = TestBed.inject(PlrsCommissionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const plrsCommission: IPlrsCommission = { id: 456 };

      activatedRoute.data = of({ plrsCommission });
      comp.ngOnInit();

      expect(comp.plrsCommission).toEqual(plrsCommission);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlrsCommission>>();
      const plrsCommission = { id: 123 };
      jest.spyOn(plrsCommissionFormService, 'getPlrsCommission').mockReturnValue(plrsCommission);
      jest.spyOn(plrsCommissionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plrsCommission });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: plrsCommission }));
      saveSubject.complete();

      // THEN
      expect(plrsCommissionFormService.getPlrsCommission).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(plrsCommissionService.update).toHaveBeenCalledWith(expect.objectContaining(plrsCommission));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlrsCommission>>();
      const plrsCommission = { id: 123 };
      jest.spyOn(plrsCommissionFormService, 'getPlrsCommission').mockReturnValue({ id: null });
      jest.spyOn(plrsCommissionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plrsCommission: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: plrsCommission }));
      saveSubject.complete();

      // THEN
      expect(plrsCommissionFormService.getPlrsCommission).toHaveBeenCalled();
      expect(plrsCommissionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlrsCommission>>();
      const plrsCommission = { id: 123 };
      jest.spyOn(plrsCommissionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plrsCommission });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(plrsCommissionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
