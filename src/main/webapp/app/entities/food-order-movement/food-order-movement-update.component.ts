import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IFoodOrderMovement } from 'app/shared/model/food-order-movement.model';
import { FoodOrderMovementService } from './food-order-movement.service';
import { IFoodOrder } from 'app/shared/model/food-order.model';
import { FoodOrderService } from 'app/entities/food-order';

@Component({
    selector: 'jhi-food-order-movement-update',
    templateUrl: './food-order-movement-update.component.html'
})
export class FoodOrderMovementUpdateComponent implements OnInit {
    foodOrderMovement: IFoodOrderMovement;
    isSaving: boolean;

    foodorders: IFoodOrder[];
    dateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected foodOrderMovementService: FoodOrderMovementService,
        protected foodOrderService: FoodOrderService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ foodOrderMovement }) => {
            this.foodOrderMovement = foodOrderMovement;
        });
        this.foodOrderService.query().subscribe(
            (res: HttpResponse<IFoodOrder[]>) => {
                this.foodorders = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.foodOrderMovement.id !== undefined) {
            this.subscribeToSaveResponse(this.foodOrderMovementService.update(this.foodOrderMovement));
        } else {
            this.subscribeToSaveResponse(this.foodOrderMovementService.create(this.foodOrderMovement));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFoodOrderMovement>>) {
        result.subscribe((res: HttpResponse<IFoodOrderMovement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackFoodOrderById(index: number, item: IFoodOrder) {
        return item.id;
    }
}
