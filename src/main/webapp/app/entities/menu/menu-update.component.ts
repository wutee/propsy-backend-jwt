import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IMenu } from 'app/shared/model/menu.model';
import { MenuService } from './menu.service';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant';
import { IFood } from 'app/shared/model/food.model';
import { FoodService } from 'app/entities/food';

@Component({
    selector: 'jhi-menu-update',
    templateUrl: './menu-update.component.html'
})
export class MenuUpdateComponent implements OnInit {
    menu: IMenu;
    isSaving: boolean;

    restaurants: IRestaurant[];

    foods: IFood[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected menuService: MenuService,
        protected restaurantService: RestaurantService,
        protected foodService: FoodService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ menu }) => {
            this.menu = menu;
        });
        this.restaurantService.query().subscribe(
            (res: HttpResponse<IRestaurant[]>) => {
                this.restaurants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.foodService.query().subscribe(
            (res: HttpResponse<IFood[]>) => {
                this.foods = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.menu, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.menu.id !== undefined) {
            this.subscribeToSaveResponse(this.menuService.update(this.menu));
        } else {
            this.subscribeToSaveResponse(this.menuService.create(this.menu));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMenu>>) {
        result.subscribe((res: HttpResponse<IMenu>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRestaurantById(index: number, item: IRestaurant) {
        return item.id;
    }

    trackFoodById(index: number, item: IFood) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
