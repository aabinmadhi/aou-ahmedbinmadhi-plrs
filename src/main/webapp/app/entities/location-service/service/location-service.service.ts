import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILocationService, NewLocationService } from '../location-service.model';

export type PartialUpdateLocationService = Partial<ILocationService> & Pick<ILocationService, 'id'>;

export type EntityResponseType = HttpResponse<ILocationService>;
export type EntityArrayResponseType = HttpResponse<ILocationService[]>;

@Injectable({ providedIn: 'root' })
export class LocationServiceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/location-services');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(locationService: NewLocationService): Observable<EntityResponseType> {
    return this.http.post<ILocationService>(this.resourceUrl, locationService, { observe: 'response' });
  }

  update(locationService: ILocationService): Observable<EntityResponseType> {
    return this.http.put<ILocationService>(`${this.resourceUrl}/${this.getLocationServiceIdentifier(locationService)}`, locationService, {
      observe: 'response',
    });
  }

  partialUpdate(locationService: PartialUpdateLocationService): Observable<EntityResponseType> {
    return this.http.patch<ILocationService>(`${this.resourceUrl}/${this.getLocationServiceIdentifier(locationService)}`, locationService, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILocationService>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILocationService[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLocationServiceIdentifier(locationService: Pick<ILocationService, 'id'>): number {
    return locationService.id;
  }

  compareLocationService(o1: Pick<ILocationService, 'id'> | null, o2: Pick<ILocationService, 'id'> | null): boolean {
    return o1 && o2 ? this.getLocationServiceIdentifier(o1) === this.getLocationServiceIdentifier(o2) : o1 === o2;
  }

  addLocationServiceToCollectionIfMissing<Type extends Pick<ILocationService, 'id'>>(
    locationServiceCollection: Type[],
    ...locationServicesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const locationServices: Type[] = locationServicesToCheck.filter(isPresent);
    if (locationServices.length > 0) {
      const locationServiceCollectionIdentifiers = locationServiceCollection.map(
        locationServiceItem => this.getLocationServiceIdentifier(locationServiceItem)!
      );
      const locationServicesToAdd = locationServices.filter(locationServiceItem => {
        const locationServiceIdentifier = this.getLocationServiceIdentifier(locationServiceItem);
        if (locationServiceCollectionIdentifiers.includes(locationServiceIdentifier)) {
          return false;
        }
        locationServiceCollectionIdentifiers.push(locationServiceIdentifier);
        return true;
      });
      return [...locationServicesToAdd, ...locationServiceCollection];
    }
    return locationServiceCollection;
  }
}
