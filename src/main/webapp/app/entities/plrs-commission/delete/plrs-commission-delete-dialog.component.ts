import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlrsCommission } from '../plrs-commission.model';
import { PlrsCommissionService } from '../service/plrs-commission.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './plrs-commission-delete-dialog.component.html',
})
export class PlrsCommissionDeleteDialogComponent {
  plrsCommission?: IPlrsCommission;

  constructor(protected plrsCommissionService: PlrsCommissionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.plrsCommissionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
