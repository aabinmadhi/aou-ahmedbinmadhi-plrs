import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPlrsCommission, NewPlrsCommission } from '../plrs-commission.model';

export type PartialUpdatePlrsCommission = Partial<IPlrsCommission> & Pick<IPlrsCommission, 'id'>;

type RestOf<T extends IPlrsCommission | NewPlrsCommission> = Omit<T, 'commissionStartDate' | 'commissionEndDate'> & {
  commissionStartDate?: string | null;
  commissionEndDate?: string | null;
};

export type RestPlrsCommission = RestOf<IPlrsCommission>;

export type NewRestPlrsCommission = RestOf<NewPlrsCommission>;

export type PartialUpdateRestPlrsCommission = RestOf<PartialUpdatePlrsCommission>;

export type EntityResponseType = HttpResponse<IPlrsCommission>;
export type EntityArrayResponseType = HttpResponse<IPlrsCommission[]>;

@Injectable({ providedIn: 'root' })
export class PlrsCommissionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/plrs-commissions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(plrsCommission: NewPlrsCommission): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plrsCommission);
    return this.http
      .post<RestPlrsCommission>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(plrsCommission: IPlrsCommission): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plrsCommission);
    return this.http
      .put<RestPlrsCommission>(`${this.resourceUrl}/${this.getPlrsCommissionIdentifier(plrsCommission)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(plrsCommission: PartialUpdatePlrsCommission): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plrsCommission);
    return this.http
      .patch<RestPlrsCommission>(`${this.resourceUrl}/${this.getPlrsCommissionIdentifier(plrsCommission)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPlrsCommission>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPlrsCommission[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPlrsCommissionIdentifier(plrsCommission: Pick<IPlrsCommission, 'id'>): number {
    return plrsCommission.id;
  }

  comparePlrsCommission(o1: Pick<IPlrsCommission, 'id'> | null, o2: Pick<IPlrsCommission, 'id'> | null): boolean {
    return o1 && o2 ? this.getPlrsCommissionIdentifier(o1) === this.getPlrsCommissionIdentifier(o2) : o1 === o2;
  }

  addPlrsCommissionToCollectionIfMissing<Type extends Pick<IPlrsCommission, 'id'>>(
    plrsCommissionCollection: Type[],
    ...plrsCommissionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const plrsCommissions: Type[] = plrsCommissionsToCheck.filter(isPresent);
    if (plrsCommissions.length > 0) {
      const plrsCommissionCollectionIdentifiers = plrsCommissionCollection.map(
        plrsCommissionItem => this.getPlrsCommissionIdentifier(plrsCommissionItem)!
      );
      const plrsCommissionsToAdd = plrsCommissions.filter(plrsCommissionItem => {
        const plrsCommissionIdentifier = this.getPlrsCommissionIdentifier(plrsCommissionItem);
        if (plrsCommissionCollectionIdentifiers.includes(plrsCommissionIdentifier)) {
          return false;
        }
        plrsCommissionCollectionIdentifiers.push(plrsCommissionIdentifier);
        return true;
      });
      return [...plrsCommissionsToAdd, ...plrsCommissionCollection];
    }
    return plrsCommissionCollection;
  }

  protected convertDateFromClient<T extends IPlrsCommission | NewPlrsCommission | PartialUpdatePlrsCommission>(
    plrsCommission: T
  ): RestOf<T> {
    return {
      ...plrsCommission,
      commissionStartDate: plrsCommission.commissionStartDate?.toJSON() ?? null,
      commissionEndDate: plrsCommission.commissionEndDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restPlrsCommission: RestPlrsCommission): IPlrsCommission {
    return {
      ...restPlrsCommission,
      commissionStartDate: restPlrsCommission.commissionStartDate ? dayjs(restPlrsCommission.commissionStartDate) : undefined,
      commissionEndDate: restPlrsCommission.commissionEndDate ? dayjs(restPlrsCommission.commissionEndDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPlrsCommission>): HttpResponse<IPlrsCommission> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPlrsCommission[]>): HttpResponse<IPlrsCommission[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
