import { Moment } from 'moment';
import { IFoodOrder } from 'app/shared/model//food-order.model';

export const enum FoodOrderParticipant {
    DELIVERYMAN = 'DELIVERYMAN',
    PURCHASER = 'PURCHASER'
}

export interface IFoodOrderMovement {
    id?: number;
    date?: Moment;
    latitude?: number;
    longitude?: number;
    sender?: FoodOrderParticipant;
    foodOrder?: IFoodOrder;
}

export class FoodOrderMovement implements IFoodOrderMovement {
    constructor(
        public id?: number,
        public date?: Moment,
        public latitude?: number,
        public longitude?: number,
        public sender?: FoodOrderParticipant,
        public foodOrder?: IFoodOrder
    ) {}
}
