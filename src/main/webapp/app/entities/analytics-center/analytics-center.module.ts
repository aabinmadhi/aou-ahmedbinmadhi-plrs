import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AnalyticsCenterComponent } from './list/analytics-center.component';
import { AnalyticsCenterDetailComponent } from './detail/analytics-center-detail.component';
import { AnalyticsCenterUpdateComponent } from './update/analytics-center-update.component';
import { AnalyticsCenterDeleteDialogComponent } from './delete/analytics-center-delete-dialog.component';
import { AnalyticsCenterRoutingModule } from './route/analytics-center-routing.module';

@NgModule({
  imports: [SharedModule, AnalyticsCenterRoutingModule],
  declarations: [
    AnalyticsCenterComponent,
    AnalyticsCenterDetailComponent,
    AnalyticsCenterUpdateComponent,
    AnalyticsCenterDeleteDialogComponent,
  ],
})
export class AnalyticsCenterModule {}
