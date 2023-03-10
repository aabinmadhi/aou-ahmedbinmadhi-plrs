import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILocationService } from '../location-service.model';
import { LocationServiceService } from '../service/location-service.service';

@Injectable({ providedIn: 'root' })
export class LocationServiceRoutingResolveService implements Resolve<ILocationService | null> {
  constructor(protected service: LocationServiceService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILocationService | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((locationService: HttpResponse<ILocationService>) => {
          if (locationService.body) {
            return of(locationService.body);
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
