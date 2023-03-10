import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPlrsCommission } from '../plrs-commission.model';
import { PlrsCommissionService } from '../service/plrs-commission.service';

@Injectable({ providedIn: 'root' })
export class PlrsCommissionRoutingResolveService implements Resolve<IPlrsCommission | null> {
  constructor(protected service: PlrsCommissionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlrsCommission | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((plrsCommission: HttpResponse<IPlrsCommission>) => {
          if (plrsCommission.body) {
            return of(plrsCommission.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
