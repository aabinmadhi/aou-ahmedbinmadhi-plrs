import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILocationService } from '../location-service.model';
import { LocationServiceService } from '../service/location-service.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './location-service-delete-dialog.component.html',
})
export class LocationServiceDeleteDialogComponent {
  locationService?: ILocationService;

  constructor(protected locationServiceService: LocationServiceService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.locationServiceService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
