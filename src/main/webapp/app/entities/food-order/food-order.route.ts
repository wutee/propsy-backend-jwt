import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FoodOrder } from 'app/shared/model/food-order.model';
import { FoodOrderService } from './food-order.service';
import { FoodOrderComponent } from './food-order.component';
import { FoodOrderDetailComponent } from './food-order-detail.component';
import { FoodOrderUpdateComponent } from './food-order-update.component';
import { FoodOrderDeletePopupComponent } from './food-order-delete-dialog.component';
import { IFoodOrder } from 'app/shared/model/food-order.model';

@Injectable({ providedIn: 'root' })
export class FoodOrderResolve implements Resolve<IFoodOrder> {
    constructor(private service: FoodOrderService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<FoodOrder> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<FoodOrder>) => response.ok),
                map((foodOrder: HttpResponse<FoodOrder>) => foodOrder.body)
            );
        }
        return of(new FoodOrder());
    }
}

export const foodOrderRoute: Routes = [
    {
        path: 'food-order',
        component: FoodOrderComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propsyBackendJwtApp.foodOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'food-order/:id/view',
        component: FoodOrderDetailComponent,
        resolve: {
            foodOrder: FoodOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propsyBackendJwtApp.foodOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'food-order/new',
        component: FoodOrderUpdateComponent,
        resolve: {
            foodOrder: FoodOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propsyBackendJwtApp.foodOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'food-order/:id/edit',
        component: FoodOrderUpdateComponent,
        resolve: {
            foodOrder: FoodOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propsyBackendJwtApp.foodOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const foodOrderPopupRoute: Routes = [
    {
        path: 'food-order/:id/delete',
        component: FoodOrderDeletePopupComponent,
        resolve: {
            foodOrder: FoodOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propsyBackendJwtApp.foodOrder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
