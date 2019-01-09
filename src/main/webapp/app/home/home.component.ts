import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Account, AccountService, LoginModalService } from 'app/core';
import { FoodOrderService } from 'app/entities/food-order';
import { OrderStatus } from 'app/shared/model/food-order.model';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    foodOrders = [];

    constructor(
        private accountService: AccountService,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private foodOrderService: FoodOrderService
    ) {}

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();

        this.foodOrderService.myRestaurants(this.account).subscribe(data => {
            this.foodOrders = data;
        });

        setInterval(() => {
            if (this.account) {
                this.foodOrderService.myRestaurants(this.account, true);
            }
        }, 8000);
    }

    orderToWIP(a) {
        this.orderToStatus(a, OrderStatus.IN_PROGRESS);
    }

    orderToPickUp(a) {
        this.orderToStatus(a, OrderStatus.TO_PICK_UP);
    }

    orderToStatus(a, status) {
        this.foodOrderService
            .update({
                ...a,
                status
            })
            .subscribe(data => {
                console.log('Udao sie');
            });
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.accountService.identity().then(account => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }
}
