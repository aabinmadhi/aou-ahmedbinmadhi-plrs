import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnalyticsCenter, NewAnalyticsCenter } from '../analytics-center.model';

export type PartialUpdateAnalyticsCenter = Partial<IAnalyticsCenter> & Pick<IAnalyticsCenter, 'id'>;

export type EntityResponseType = HttpResponse<IAnalyticsCenter>;
export type EntityArrayResponseType = HttpResponse<IAnalyticsCenter[]>;

@Injectable({ providedIn: 'root' })
export class AnalyticsCenterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/analytics-centers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(analyticsCenter: NewAnalyticsCenter): Observable<EntityResponseType> {
    return this.http.post<IAnalyticsCenter>(this.resourceUrl, analyticsCenter, { observe: 'response' });
  }

  update(analyticsCenter: IAnalyticsCenter): Observable<EntityResponseType> {
    return this.http.put<IAnalyticsCenter>(`${this.resourceUrl}/${this.getAnalyticsCenterIdentifier(analyticsCenter)}`, analyticsCenter, {
      observe: 'response',
    });
  }

  partialUpdate(analyticsCenter: PartialUpdateAnalyticsCenter): Observable<EntityResponseType> {
    return this.http.patch<IAnalyticsCenter>(`${this.resourceUrl}/${this.getAnalyticsCenterIdentifier(analyticsCenter)}`, analyticsCenter, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnalyticsCenter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnalyticsCenter[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAnalyticsCenterIdentifier(analyticsCenter: Pick<IAnalyticsCenter, 'id'>): number {
    return analyticsCenter.id;
  }

  compareAnalyticsCenter(o1: Pick<IAnalyticsCenter, 'id'> | null, o2: Pick<IAnalyticsCenter, 'id'> | null): boolean {
    return o1 && o2 ? this.getAnalyticsCenterIdentifier(o1) === this.getAnalyticsCenterIdentifier(o2) : o1 === o2;
  }

  addAnalyticsCenterToCollectionIfMissing<Type extends Pick<IAnalyticsCenter, 'id'>>(
    analyticsCenterCollection: Type[],
    ...analyticsCentersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const analyticsCenters: Type[] = analyticsCentersToCheck.filter(isPresent);
    if (analyticsCenters.length > 0) {
      const analyticsCenterCollectionIdentifiers = analyticsCenterCollection.map(
        analyticsCenterItem => this.getAnalyticsCenterIdentifier(analyticsCenterItem)!
      );
      const analyticsCentersToAdd = analyticsCenters.filter(analyticsCenterItem => {
        const analyticsCenterIdentifier = this.getAnalyticsCenterIdentifier(analyticsCenterItem);
        if (analyticsCenterCollectionIdentifiers.includes(analyticsCenterIdentifier)) {
          return false;
        }
        analyticsCenterCollectionIdentifiers.push(analyticsCenterIdentifier);
        return true;
      });
      return [...analyticsCentersToAdd, ...analyticsCenterCollection];
    }
    return analyticsCenterCollection;
  }
}
