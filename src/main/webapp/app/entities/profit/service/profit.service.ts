import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProfit, NewProfit } from '../profit.model';

export type PartialUpdateProfit = Partial<IProfit> & Pick<IProfit, 'id'>;

export type EntityResponseType = HttpResponse<IProfit>;
export type EntityArrayResponseType = HttpResponse<IProfit[]>;

@Injectable({ providedIn: 'root' })
export class ProfitService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/profits');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(profit: NewProfit): Observable<EntityResponseType> {
    return this.http.post<IProfit>(this.resourceUrl, profit, { observe: 'response' });
  }

  update(profit: IProfit): Observable<EntityResponseType> {
    return this.http.put<IProfit>(`${this.resourceUrl}/${this.getProfitIdentifier(profit)}`, profit, { observe: 'response' });
  }

  partialUpdate(profit: PartialUpdateProfit): Observable<EntityResponseType> {
    return this.http.patch<IProfit>(`${this.resourceUrl}/${this.getProfitIdentifier(profit)}`, profit, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProfit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProfit[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProfitIdentifier(profit: Pick<IProfit, 'id'>): number {
    return profit.id;
  }

  compareProfit(o1: Pick<IProfit, 'id'> | null, o2: Pick<IProfit, 'id'> | null): boolean {
    return o1 && o2 ? this.getProfitIdentifier(o1) === this.getProfitIdentifier(o2) : o1 === o2;
  }

  addProfitToCollectionIfMissing<Type extends Pick<IProfit, 'id'>>(
    profitCollection: Type[],
    ...profitsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const profits: Type[] = profitsToCheck.filter(isPresent);
    if (profits.length > 0) {
      const profitCollectionIdentifiers = profitCollection.map(profitItem => this.getProfitIdentifier(profitItem)!);
      const profitsToAdd = profits.filter(profitItem => {
        const profitIdentifier = this.getProfitIdentifier(profitItem);
        if (profitCollectionIdentifiers.includes(profitIdentifier)) {
          return false;
        }
        profitCollectionIdentifiers.push(profitIdentifier);
        return true;
      });
      return [...profitsToAdd, ...profitCollection];
    }
    return profitCollection;
  }
}
