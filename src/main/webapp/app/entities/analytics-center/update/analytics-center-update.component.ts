import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AnalyticsCenterFormService, AnalyticsCenterFormGroup } from './analytics-center-form.service';
import { IAnalyticsCenter } from '../analytics-center.model';
import { AnalyticsCenterService } from '../service/analytics-center.service';

@Component({
  selector: 'jhi-analytics-center-update',
  templateUrl: './analytics-center-update.component.html',
})
export class AnalyticsCenterUpdateComponent implements OnInit {
  isSaving = false;
  analyticsCenter: IAnalyticsCenter | null = null;

  editForm: AnalyticsCenterFormGroup = this.analyticsCenterFormService.createAnalyticsCenterFormGroup();

  constructor(
    protected analyticsCenterService: AnalyticsCenterService,
    protected analyticsCenterFormService: AnalyticsCenterFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ analyticsCenter }) => {
      this.analyticsCenter = analyticsCenter;
      if (analyticsCenter) {
        this.updateForm(analyticsCenter);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const analyticsCenter = this.analyticsCenterFormService.getAnalyticsCenter(this.editForm);
    if (analyticsCenter.id !== null) {
      this.subscribeToSaveResponse(this.analyticsCenterService.update(analyticsCenter));
    } else {
      this.subscribeToSaveResponse(this.analyticsCenterService.create(analyticsCenter));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnalyticsCenter>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(analyticsCenter: IAnalyticsCenter): void {
    this.analyticsCenter = analyticsCenter;
    this.analyticsCenterFormService.resetForm(this.editForm, analyticsCenter);
  }
}
