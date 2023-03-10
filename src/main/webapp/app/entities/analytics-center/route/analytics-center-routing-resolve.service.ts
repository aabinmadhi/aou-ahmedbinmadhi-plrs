import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAnalyticsCenter } from '../analytics-center.model';
import { AnalyticsCenterService } from '../service/analytics-center.service';

@Injectable({ providedIn: 'root' })
export class AnalyticsCenterRoutingResolveService implements Resolve<IAnalyticsCenter | null> {
  constructor(protected service: AnalyticsCenterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnalyticsCenter | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((analyticsCenter: HttpResponse<IAnalyticsCenter>) => {
          if (analyticsCenter.body) {
            return of(analyticsCenter.body);
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
