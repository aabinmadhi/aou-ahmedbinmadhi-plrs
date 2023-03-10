import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnalyticsCenter } from '../analytics-center.model';

@Component({
  selector: 'jhi-analytics-center-detail',
  templateUrl: './analytics-center-detail.component.html',
})
export class AnalyticsCenterDetailComponent implements OnInit {
  analyticsCenter: IAnalyticsCenter | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ analyticsCenter }) => {
      this.analyticsCenter = analyticsCenter;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
