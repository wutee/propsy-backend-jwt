import { Moment } from 'moment';
import { IRestaurant } from 'app/shared/model//restaurant.model';
import { IUser } from 'app/core/user/user.model';
import { IFood } from 'app/shared/model//food.model';
import { IFoodOrderMovement } from 'app/shared/model//food-order-movement.model';

export const enum OrderStatus {
    NEW = 'NEW',
    PAYMENT = 'PAYMENT',
    CONFIRMED = 'CONFIRMED',
    IN_PROGRESS = 'IN_PROGRESS',
    TO_PICK_UP = 'TO_PICK_UP',
    IN_DELIVERY = 'IN_DELIVERY',
    DELIVERED = 'DELIVERED'
}

export interface IFoodOrder {
    id?: number;
    timeRating?: number;
    priceRating?: number;
    qualityRating?: number;
    loyaltyPoints?: number;
    addressRating?: number;
    date?: Moment;
    price?: number;
    status?: OrderStatus;
    purchaserOpinion?: string;
    purchaserComment?: string;
    city?: string;
    phone?: string;
    address?: string;
    restaurant?: IRestaurant;
    deliveryman?: IUser;
    purchaser?: IUser;
    foodItems?: IFood[];
    movements?: IFoodOrderMovement[];
}

export class FoodOrder implements IFoodOrder {
    constructor(
        public id?: number,
        public timeRating?: number,
        public priceRating?: number,
        public qualityRating?: number,
        public loyaltyPoints?: number,
        public addressRating?: number,
        public date?: Moment,
        public price?: number,
        public status?: OrderStatus,
        public purchaserOpinion?: string,
        public purchaserComment?: string,
        public city?: string,
        public phone?: string,
        public address?: string,
        public restaurant?: IRestaurant,
        public deliveryman?: IUser,
        public purchaser?: IUser,
        public foodItems?: IFood[],
        public movements?: IFoodOrderMovement[]
    ) {}
}
