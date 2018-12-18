import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IRestaurant } from 'app/shared/model/restaurant.model';

@Component({
    selector: 'jhi-restaurant-detail',
    templateUrl: './restaurant-detail.component.html'
})
export class RestaurantDetailComponent implements OnInit {
    restaurant: IRestaurant;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ restaurant }) => {
            this.restaurant = restaurant;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
