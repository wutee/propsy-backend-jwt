import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FoodOrderMovement } from 'app/shared/model/food-order-movement.model';
import { FoodOrderMovementService } from './food-order-movement.service';
import { FoodOrderMovementComponent } from './food-order-movement.component';
import { FoodOrderMovementDetailComponent } from './food-order-movement-detail.component';
import { FoodOrderMovementUpdateComponent } from './food-order-movement-update.component';
import { FoodOrderMovementDeletePopupComponent } from './food-order-movement-delete-dialog.component';
import { IFoodOrderMovement } from 'app/shared/model/food-order-movement.model';

@Injectable({ providedIn: 'root' })
export class FoodOrderMovementResolve implements Resolve<IFoodOrderMovement> {
    constructor(private service: FoodOrderMovementService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<FoodOrderMovement> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<FoodOrderMovement>) => response.ok),
                map((foodOrderMovement: HttpResponse<FoodOrderMovement>) => foodOrderMovement.body)
            );
        }
        return of(new FoodOrderMovement());
    }
}

export const foodOrderMovementRoute: Routes = [
    {
        path: 'food-order-movement',
        component: FoodOrderMovementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propsyBackendJwtApp.foodOrderMovement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'food-order-movement/:id/view',
        component: FoodOrderMovementDetailComponent,
        resolve: {
            foodOrderMovement: FoodOrderMovementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propsyBackendJwtApp.foodOrderMovement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'food-order-movement/new',
        component: FoodOrderMovementUpdateComponent,
        resolve: {
            foodOrderMovement: FoodOrderMovementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propsyBackendJwtApp.foodOrderMovement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'food-order-movement/:id/edit',
        component: FoodOrderMovementUpdateComponent,
        resolve: {
            foodOrderMovement: FoodOrderMovementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propsyBackendJwtApp.foodOrderMovement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const foodOrderMovementPopupRoute: Routes = [
    {
        path: 'food-order-movement/:id/delete',
        component: FoodOrderMovementDeletePopupComponent,
        resolve: {
            foodOrderMovement: FoodOrderMovementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propsyBackendJwtApp.foodOrderMovement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
