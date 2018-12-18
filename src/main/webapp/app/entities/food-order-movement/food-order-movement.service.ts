import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFoodOrderMovement } from 'app/shared/model/food-order-movement.model';

type EntityResponseType = HttpResponse<IFoodOrderMovement>;
type EntityArrayResponseType = HttpResponse<IFoodOrderMovement[]>;

@Injectable({ providedIn: 'root' })
export class FoodOrderMovementService {
    public resourceUrl = SERVER_API_URL + 'api/food-order-movements';

    constructor(protected http: HttpClient) {}

    create(foodOrderMovement: IFoodOrderMovement): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(foodOrderMovement);
        return this.http
            .post<IFoodOrderMovement>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(foodOrderMovement: IFoodOrderMovement): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(foodOrderMovement);
        return this.http
            .put<IFoodOrderMovement>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IFoodOrderMovement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IFoodOrderMovement[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(foodOrderMovement: IFoodOrderMovement): IFoodOrderMovement {
        const copy: IFoodOrderMovement = Object.assign({}, foodOrderMovement, {
            date: foodOrderMovement.date != null && foodOrderMovement.date.isValid() ? foodOrderMovement.date.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.date = res.body.date != null ? moment(res.body.date) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((foodOrderMovement: IFoodOrderMovement) => {
                foodOrderMovement.date = foodOrderMovement.date != null ? moment(foodOrderMovement.date) : null;
            });
        }
        return res;
    }
}
