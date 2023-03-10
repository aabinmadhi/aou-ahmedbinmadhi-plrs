import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnalyticsCenter } from '../analytics-center.model';
import { AnalyticsCenterService } from '../service/analytics-center.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './analytics-center-delete-dialog.component.html',
})
export class AnalyticsCenterDeleteDialogComponent {
  analyticsCenter?: IAnalyticsCenter;

  constructor(protected analyticsCenterService: AnalyticsCenterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.analyticsCenterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
