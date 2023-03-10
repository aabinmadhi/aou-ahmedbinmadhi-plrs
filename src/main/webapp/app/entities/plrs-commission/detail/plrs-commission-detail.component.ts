import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlrsCommission } from '../plrs-commission.model';

@Component({
  selector: 'jhi-plrs-commission-detail',
  templateUrl: './plrs-commission-detail.component.html',
})
export class PlrsCommissionDetailComponent implements OnInit {
  plrsCommission: IPlrsCommission | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plrsCommission }) => {
      this.plrsCommission = plrsCommission;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
