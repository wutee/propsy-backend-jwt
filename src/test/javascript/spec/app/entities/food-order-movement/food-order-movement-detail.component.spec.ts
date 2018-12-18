/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PropsyBackendJwtTestModule } from '../../../test.module';
import { FoodOrderMovementDetailComponent } from 'app/entities/food-order-movement/food-order-movement-detail.component';
import { FoodOrderMovement } from 'app/shared/model/food-order-movement.model';

describe('Component Tests', () => {
    describe('FoodOrderMovement Management Detail Component', () => {
        let comp: FoodOrderMovementDetailComponent;
        let fixture: ComponentFixture<FoodOrderMovementDetailComponent>;
        const route = ({ data: of({ foodOrderMovement: new FoodOrderMovement(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyBackendJwtTestModule],
                declarations: [FoodOrderMovementDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FoodOrderMovementDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FoodOrderMovementDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.foodOrderMovement).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
