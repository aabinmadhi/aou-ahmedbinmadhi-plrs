import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LocationServiceComponent } from '../list/location-service.component';
import { LocationServiceDetailComponent } from '../detail/location-service-detail.component';
import { LocationServiceUpdateComponent } from '../update/location-service-update.component';
import { LocationServiceRoutingResolveService } from './location-service-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const locationServiceRoute: Routes = [
  {
    path: '',
    component: LocationServiceComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LocationServiceDetailComponent,
    resolve: {
      locationService: LocationServiceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LocationServiceUpdateComponent,
    resolve: {
      locationService: LocationServiceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LocationServiceUpdateComponent,
    resolve: {
      locationService: LocationServiceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(locationServiceRoute)],
  exports: [RouterModule],
})
export class LocationServiceRoutingModule {}
