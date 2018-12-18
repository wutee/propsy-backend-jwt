import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PropsyBackendJwtSharedModule } from 'app/shared';
import {
    FoodOrderMovementComponent,
    FoodOrderMovementDetailComponent,
    FoodOrderMovementUpdateComponent,
    FoodOrderMovementDeletePopupComponent,
    FoodOrderMovementDeleteDialogComponent,
    foodOrderMovementRoute,
    foodOrderMovementPopupRoute
} from './';

const ENTITY_STATES = [...foodOrderMovementRoute, ...foodOrderMovementPopupRoute];

@NgModule({
    imports: [PropsyBackendJwtSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FoodOrderMovementComponent,
        FoodOrderMovementDetailComponent,
        FoodOrderMovementUpdateComponent,
        FoodOrderMovementDeleteDialogComponent,
        FoodOrderMovementDeletePopupComponent
    ],
    entryComponents: [
        FoodOrderMovementComponent,
        FoodOrderMovementUpdateComponent,
        FoodOrderMovementDeleteDialogComponent,
        FoodOrderMovementDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropsyBackendJwtFoodOrderMovementModule {}
