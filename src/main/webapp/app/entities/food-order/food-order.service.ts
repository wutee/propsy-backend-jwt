import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFoodOrder } from 'app/shared/model/food-order.model';
import { AccountService, User } from 'app/core';

type EntityResponseType = HttpResponse<IFoodOrder>;
type EntityArrayResponseType = HttpResponse<IFoodOrder[]>;

@Injectable({ providedIn: 'root' })
export class FoodOrderService {
    public resourceUrl = SERVER_API_URL + 'api/food-orders';
    private foodOrdersSubject: Subject<IFoodOrder[]>;
    private foodOrdersRequest: Observable<IFoodOrder[]>;

    constructor(protected http: HttpClient) {
        this.foodOrdersSubject = new ReplaySubject(1);
    }

    myRestaurants(acc: User, refresh = false): Observable<IFoodOrder[]> {
        if (!acc) {
            return this.foodOrdersSubject.asObservable();
        }

        if (refresh || !this.foodOrdersRequest) {
            this.foodOrdersRequest = this.http
                .get<IFoodOrder[]>(this.resourceUrl + '/forMyRestaurants/' + acc.id, { observe: 'response' })
                .pipe(
                    map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)),
                    map((res: EntityArrayResponseType) => res.body)
                );

            this.foodOrdersRequest.subscribe(result => this.foodOrdersSubject.next(result), err => this.foodOrdersSubject.error(err));
        }
        return this.foodOrdersSubject.asObservable();
    }

    create(foodOrder: IFoodOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(foodOrder);
        return this.http
            .post<IFoodOrder>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(foodOrder: IFoodOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(foodOrder);
        return this.http
            .put<IFoodOrder>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IFoodOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IFoodOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(foodOrder: IFoodOrder): IFoodOrder {
        const copy: IFoodOrder = Object.assign({}, foodOrder, {
            date: foodOrder.date != null && foodOrder.date.isValid() ? foodOrder.date.format(DATE_FORMAT) : null
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
            res.body.forEach((foodOrder: IFoodOrder) => {
                foodOrder.date = foodOrder.date != null ? moment(foodOrder.date) : null;
            });
        }
        return res;
    }
}
