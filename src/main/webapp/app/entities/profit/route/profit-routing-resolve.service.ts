import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProfit } from '../profit.model';
import { ProfitService } from '../service/profit.service';

@Injectable({ providedIn: 'root' })
export class ProfitRoutingResolveService implements Resolve<IProfit | null> {
  constructor(protected service: ProfitService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProfit | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((profit: HttpResponse<IProfit>) => {
          if (profit.body) {
            return of(profit.body);
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
