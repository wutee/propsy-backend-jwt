import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IFood } from 'app/shared/model/food.model';
import { AccountService } from 'app/core';
import { FoodService } from './food.service';

@Component({
    selector: 'jhi-food',
    templateUrl: './food.component.html'
})
export class FoodComponent implements OnInit, OnDestroy {
    foods: IFood[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected foodService: FoodService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.foodService.query().subscribe(
            (res: HttpResponse<IFood[]>) => {
                this.foods = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFoods();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFood) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInFoods() {
        this.eventSubscriber = this.eventManager.subscribe('foodListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
