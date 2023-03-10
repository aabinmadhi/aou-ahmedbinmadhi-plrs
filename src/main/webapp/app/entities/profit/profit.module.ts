import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProfitComponent } from './list/profit.component';
import { ProfitDetailComponent } from './detail/profit-detail.component';
import { ProfitUpdateComponent } from './update/profit-update.component';
import { ProfitDeleteDialogComponent } from './delete/profit-delete-dialog.component';
import { ProfitRoutingModule } from './route/profit-routing.module';

@NgModule({
  imports: [SharedModule, ProfitRoutingModule],
  declarations: [ProfitComponent, ProfitDetailComponent, ProfitUpdateComponent, ProfitDeleteDialogComponent],
})
export class ProfitModule {}
