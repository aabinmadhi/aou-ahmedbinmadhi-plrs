import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PlrsCommissionComponent } from './list/plrs-commission.component';
import { PlrsCommissionDetailComponent } from './detail/plrs-commission-detail.component';
import { PlrsCommissionUpdateComponent } from './update/plrs-commission-update.component';
import { PlrsCommissionDeleteDialogComponent } from './delete/plrs-commission-delete-dialog.component';
import { PlrsCommissionRoutingModule } from './route/plrs-commission-routing.module';

@NgModule({
  imports: [SharedModule, PlrsCommissionRoutingModule],
  declarations: [
    PlrsCommissionComponent,
    PlrsCommissionDetailComponent,
    PlrsCommissionUpdateComponent,
    PlrsCommissionDeleteDialogComponent,
  ],
})
export class PlrsCommissionModule {}
