import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PlrsCommissionFormService, PlrsCommissionFormGroup } from './plrs-commission-form.service';
import { IPlrsCommission } from '../plrs-commission.model';
import { PlrsCommissionService } from '../service/plrs-commission.service';

@Component({
  selector: 'jhi-plrs-commission-update',
  templateUrl: './plrs-commission-update.component.html',
})
export class PlrsCommissionUpdateComponent implements OnInit {
  isSaving = false;
  plrsCommission: IPlrsCommission | null = null;

  editForm: PlrsCommissionFormGroup = this.plrsCommissionFormService.createPlrsCommissionFormGroup();

  constructor(
    protected plrsCommissionService: PlrsCommissionService,
    protected plrsCommissionFormService: PlrsCommissionFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plrsCommission }) => {
      this.plrsCommission = plrsCommission;
      if (plrsCommission) {
        this.updateForm(plrsCommission);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const plrsCommission = this.plrsCommissionFormService.getPlrsCommission(this.editForm);
    if (plrsCommission.id !== null) {
      this.subscribeToSaveResponse(this.plrsCommissionService.update(plrsCommission));
    } else {
      this.subscribeToSaveResponse(this.plrsCommissionService.create(plrsCommission));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlrsCommission>>): void {
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

  protected updateForm(plrsCommission: IPlrsCommission): void {
    this.plrsCommission = plrsCommission;
    this.plrsCommissionFormService.resetForm(this.editForm, plrsCommission);
  }
}
