import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IRestaurant } from 'app/shared/model/restaurant.model';
import { AccountService } from 'app/core';
import { RestaurantService } from './restaurant.service';

@Component({
    selector: 'jhi-restaurant',
    templateUrl: './restaurant.component.html'
})
export class RestaurantComponent implements OnInit, OnDestroy {
    restaurants: IRestaurant[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected restaurantService: RestaurantService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.restaurantService.query().subscribe(
            (res: HttpResponse<IRestaurant[]>) => {
                this.restaurants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRestaurants();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRestaurant) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInRestaurants() {
        this.eventSubscriber = this.eventManager.subscribe('restaurantListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
