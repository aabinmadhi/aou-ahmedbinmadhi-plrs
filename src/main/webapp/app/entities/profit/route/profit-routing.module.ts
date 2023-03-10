import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProfitComponent } from '../list/profit.component';
import { ProfitDetailComponent } from '../detail/profit-detail.component';
import { ProfitUpdateComponent } from '../update/profit-update.component';
import { ProfitRoutingResolveService } from './profit-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const profitRoute: Routes = [
  {
    path: '',
    component: ProfitComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProfitDetailComponent,
    resolve: {
      profit: ProfitRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProfitUpdateComponent,
    resolve: {
      profit: ProfitRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProfitUpdateComponent,
    resolve: {
      profit: ProfitRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(profitRoute)],
  exports: [RouterModule],
})
export class ProfitRoutingModule {}
