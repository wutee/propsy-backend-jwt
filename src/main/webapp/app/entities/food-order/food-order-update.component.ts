import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IFoodOrder } from 'app/shared/model/food-order.model';
import { FoodOrderService } from './food-order.service';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant';
import { IUser, UserService } from 'app/core';
import { IFood } from 'app/shared/model/food.model';
import { FoodService } from 'app/entities/food';

@Component({
    selector: 'jhi-food-order-update',
    templateUrl: './food-order-update.component.html'
})
export class FoodOrderUpdateComponent implements OnInit {
    foodOrder: IFoodOrder;
    isSaving: boolean;

    restaurants: IRestaurant[];

    users: IUser[];

    foods: IFood[];
    dateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected foodOrderService: FoodOrderService,
        protected restaurantService: RestaurantService,
        protected userService: UserService,
        protected foodService: FoodService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ foodOrder }) => {
            this.foodOrder = foodOrder;
        });
        this.restaurantService.query().subscribe(
            (res: HttpResponse<IRestaurant[]>) => {
                this.restaurants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.foodService.query().subscribe(
            (res: HttpResponse<IFood[]>) => {
                this.foods = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.foodOrder.id !== undefined) {
            this.subscribeToSaveResponse(this.foodOrderService.update(this.foodOrder));
        } else {
            this.subscribeToSaveResponse(this.foodOrderService.create(this.foodOrder));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFoodOrder>>) {
        result.subscribe((res: HttpResponse<IFoodOrder>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRestaurantById(index: number, item: IRestaurant) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackFoodById(index: number, item: IFood) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
