import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFoodOrderMovement } from 'app/shared/model/food-order-movement.model';
import { AccountService } from 'app/core';
import { FoodOrderMovementService } from './food-order-movement.service';

@Component({
    selector: 'jhi-food-order-movement',
    templateUrl: './food-order-movement.component.html'
})
export class FoodOrderMovementComponent implements OnInit, OnDestroy {
    foodOrderMovements: IFoodOrderMovement[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected foodOrderMovementService: FoodOrderMovementService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.foodOrderMovementService.query().subscribe(
            (res: HttpResponse<IFoodOrderMovement[]>) => {
                this.foodOrderMovements = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFoodOrderMovements();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFoodOrderMovement) {
        return item.id;
    }

    registerChangeInFoodOrderMovements() {
        this.eventSubscriber = this.eventManager.subscribe('foodOrderMovementListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
