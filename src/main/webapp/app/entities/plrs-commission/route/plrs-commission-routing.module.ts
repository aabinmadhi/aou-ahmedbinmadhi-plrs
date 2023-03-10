import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PlrsCommissionComponent } from '../list/plrs-commission.component';
import { PlrsCommissionDetailComponent } from '../detail/plrs-commission-detail.component';
import { PlrsCommissionUpdateComponent } from '../update/plrs-commission-update.component';
import { PlrsCommissionRoutingResolveService } from './plrs-commission-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const plrsCommissionRoute: Routes = [
  {
    path: '',
    component: PlrsCommissionComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlrsCommissionDetailComponent,
    resolve: {
      plrsCommission: PlrsCommissionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlrsCommissionUpdateComponent,
    resolve: {
      plrsCommission: PlrsCommissionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlrsCommissionUpdateComponent,
    resolve: {
      plrsCommission: PlrsCommissionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(plrsCommissionRoute)],
  exports: [RouterModule],
})
export class PlrsCommissionRoutingModule {}
