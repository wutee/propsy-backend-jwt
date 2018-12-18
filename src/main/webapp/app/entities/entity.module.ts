import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PropsyBackendJwtRestaurantModule } from './restaurant/restaurant.module';
import { PropsyBackendJwtMenuModule } from './menu/menu.module';
import { PropsyBackendJwtFoodModule } from './food/food.module';
import { PropsyBackendJwtFoodOrderModule } from './food-order/food-order.module';
import { PropsyBackendJwtFoodOrderMovementModule } from './food-order-movement/food-order-movement.module';
import { PropsyBackendJwtExtendedUserModule } from './extended-user/extended-user.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        PropsyBackendJwtRestaurantModule,
        PropsyBackendJwtMenuModule,
        PropsyBackendJwtFoodModule,
        PropsyBackendJwtFoodOrderModule,
        PropsyBackendJwtFoodOrderMovementModule,
        PropsyBackendJwtExtendedUserModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropsyBackendJwtEntityModule {}
