import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProfit } from '../profit.model';

@Component({
  selector: 'jhi-profit-detail',
  templateUrl: './profit-detail.component.html',
})
export class ProfitDetailComponent implements OnInit {
  profit: IProfit | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ profit }) => {
      this.profit = profit;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
