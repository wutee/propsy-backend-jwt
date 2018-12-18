import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFoodOrderMovement } from 'app/shared/model/food-order-movement.model';

@Component({
    selector: 'jhi-food-order-movement-detail',
    templateUrl: './food-order-movement-detail.component.html'
})
export class FoodOrderMovementDetailComponent implements OnInit {
    foodOrderMovement: IFoodOrderMovement;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ foodOrderMovement }) => {
            this.foodOrderMovement = foodOrderMovement;
        });
    }

    previousState() {
        window.history.back();
    }
}
