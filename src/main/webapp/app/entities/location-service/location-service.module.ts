import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LocationServiceComponent } from './list/location-service.component';
import { LocationServiceDetailComponent } from './detail/location-service-detail.component';
import { LocationServiceUpdateComponent } from './update/location-service-update.component';
import { LocationServiceDeleteDialogComponent } from './delete/location-service-delete-dialog.component';
import { LocationServiceRoutingModule } from './route/location-service-routing.module';

@NgModule({
  imports: [SharedModule, LocationServiceRoutingModule],
  declarations: [
    LocationServiceComponent,
    LocationServiceDetailComponent,
    LocationServiceUpdateComponent,
    LocationServiceDeleteDialogComponent,
  ],
})
export class LocationServiceModule {}
