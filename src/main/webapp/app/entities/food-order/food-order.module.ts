import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PropsyBackendJwtSharedModule } from 'app/shared';
import { PropsyBackendJwtAdminModule } from 'app/admin/admin.module';
import {
    FoodOrderComponent,
    FoodOrderDetailComponent,
    FoodOrderUpdateComponent,
    FoodOrderDeletePopupComponent,
    FoodOrderDeleteDialogComponent,
    foodOrderRoute,
    foodOrderPopupRoute
} from './';

const ENTITY_STATES = [...foodOrderRoute, ...foodOrderPopupRoute];

@NgModule({
    imports: [PropsyBackendJwtSharedModule, PropsyBackendJwtAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FoodOrderComponent,
        FoodOrderDetailComponent,
        FoodOrderUpdateComponent,
        FoodOrderDeleteDialogComponent,
        FoodOrderDeletePopupComponent
    ],
    entryComponents: [FoodOrderComponent, FoodOrderUpdateComponent, FoodOrderDeleteDialogComponent, FoodOrderDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropsyBackendJwtFoodOrderModule {}
