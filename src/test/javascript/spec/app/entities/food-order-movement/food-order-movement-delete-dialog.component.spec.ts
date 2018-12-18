/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PropsyBackendJwtTestModule } from '../../../test.module';
import { FoodOrderMovementDeleteDialogComponent } from 'app/entities/food-order-movement/food-order-movement-delete-dialog.component';
import { FoodOrderMovementService } from 'app/entities/food-order-movement/food-order-movement.service';

describe('Component Tests', () => {
    describe('FoodOrderMovement Management Delete Component', () => {
        let comp: FoodOrderMovementDeleteDialogComponent;
        let fixture: ComponentFixture<FoodOrderMovementDeleteDialogComponent>;
        let service: FoodOrderMovementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyBackendJwtTestModule],
                declarations: [FoodOrderMovementDeleteDialogComponent]
            })
                .overrideTemplate(FoodOrderMovementDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FoodOrderMovementDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FoodOrderMovementService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
