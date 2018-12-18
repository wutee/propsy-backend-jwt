import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFoodOrderMovement } from 'app/shared/model/food-order-movement.model';
import { FoodOrderMovementService } from './food-order-movement.service';

@Component({
    selector: 'jhi-food-order-movement-delete-dialog',
    templateUrl: './food-order-movement-delete-dialog.component.html'
})
export class FoodOrderMovementDeleteDialogComponent {
    foodOrderMovement: IFoodOrderMovement;

    constructor(
        protected foodOrderMovementService: FoodOrderMovementService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.foodOrderMovementService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'foodOrderMovementListModification',
                content: 'Deleted an foodOrderMovement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-food-order-movement-delete-popup',
    template: ''
})
export class FoodOrderMovementDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ foodOrderMovement }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FoodOrderMovementDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.foodOrderMovement = foodOrderMovement;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
