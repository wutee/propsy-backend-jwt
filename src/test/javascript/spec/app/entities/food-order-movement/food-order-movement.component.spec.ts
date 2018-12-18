/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PropsyBackendJwtTestModule } from '../../../test.module';
import { FoodOrderMovementComponent } from 'app/entities/food-order-movement/food-order-movement.component';
import { FoodOrderMovementService } from 'app/entities/food-order-movement/food-order-movement.service';
import { FoodOrderMovement } from 'app/shared/model/food-order-movement.model';

describe('Component Tests', () => {
    describe('FoodOrderMovement Management Component', () => {
        let comp: FoodOrderMovementComponent;
        let fixture: ComponentFixture<FoodOrderMovementComponent>;
        let service: FoodOrderMovementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyBackendJwtTestModule],
                declarations: [FoodOrderMovementComponent],
                providers: []
            })
                .overrideTemplate(FoodOrderMovementComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FoodOrderMovementComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FoodOrderMovementService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new FoodOrderMovement(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.foodOrderMovements[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
