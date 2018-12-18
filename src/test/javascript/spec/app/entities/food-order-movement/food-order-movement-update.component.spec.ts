/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PropsyBackendJwtTestModule } from '../../../test.module';
import { FoodOrderMovementUpdateComponent } from 'app/entities/food-order-movement/food-order-movement-update.component';
import { FoodOrderMovementService } from 'app/entities/food-order-movement/food-order-movement.service';
import { FoodOrderMovement } from 'app/shared/model/food-order-movement.model';

describe('Component Tests', () => {
    describe('FoodOrderMovement Management Update Component', () => {
        let comp: FoodOrderMovementUpdateComponent;
        let fixture: ComponentFixture<FoodOrderMovementUpdateComponent>;
        let service: FoodOrderMovementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyBackendJwtTestModule],
                declarations: [FoodOrderMovementUpdateComponent]
            })
                .overrideTemplate(FoodOrderMovementUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FoodOrderMovementUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FoodOrderMovementService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new FoodOrderMovement(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.foodOrderMovement = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new FoodOrderMovement();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.foodOrderMovement = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
