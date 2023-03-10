import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AnalyticsCenterComponent } from '../list/analytics-center.component';
import { AnalyticsCenterDetailComponent } from '../detail/analytics-center-detail.component';
import { AnalyticsCenterUpdateComponent } from '../update/analytics-center-update.component';
import { AnalyticsCenterRoutingResolveService } from './analytics-center-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const analyticsCenterRoute: Routes = [
  {
    path: '',
    component: AnalyticsCenterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnalyticsCenterDetailComponent,
    resolve: {
      analyticsCenter: AnalyticsCenterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnalyticsCenterUpdateComponent,
    resolve: {
      analyticsCenter: AnalyticsCenterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnalyticsCenterUpdateComponent,
    resolve: {
      analyticsCenter: AnalyticsCenterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(analyticsCenterRoute)],
  exports: [RouterModule],
})
export class AnalyticsCenterRoutingModule {}
