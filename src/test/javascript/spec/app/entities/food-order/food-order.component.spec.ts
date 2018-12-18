/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PropsyBackendJwtTestModule } from '../../../test.module';
import { FoodOrderComponent } from 'app/entities/food-order/food-order.component';
import { FoodOrderService } from 'app/entities/food-order/food-order.service';
import { FoodOrder } from 'app/shared/model/food-order.model';

describe('Component Tests', () => {
    describe('FoodOrder Management Component', () => {
        let comp: FoodOrderComponent;
        let fixture: ComponentFixture<FoodOrderComponent>;
        let service: FoodOrderService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyBackendJwtTestModule],
                declarations: [FoodOrderComponent],
                providers: []
            })
                .overrideTemplate(FoodOrderComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FoodOrderComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FoodOrderService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new FoodOrder(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.foodOrders[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
