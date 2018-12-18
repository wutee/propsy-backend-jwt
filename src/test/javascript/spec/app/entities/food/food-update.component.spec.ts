/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PropsyBackendJwtTestModule } from '../../../test.module';
import { FoodUpdateComponent } from 'app/entities/food/food-update.component';
import { FoodService } from 'app/entities/food/food.service';
import { Food } from 'app/shared/model/food.model';

describe('Component Tests', () => {
    describe('Food Management Update Component', () => {
        let comp: FoodUpdateComponent;
        let fixture: ComponentFixture<FoodUpdateComponent>;
        let service: FoodService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyBackendJwtTestModule],
                declarations: [FoodUpdateComponent]
            })
                .overrideTemplate(FoodUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FoodUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FoodService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Food(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.food = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Food();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.food = entity;
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
