import { IUser } from 'app/core/user/user.model';
import { IFoodOrder } from 'app/shared/model//food-order.model';
import { IMenu } from 'app/shared/model//menu.model';

export interface IRestaurant {
    id?: number;
    nameSlug?: string;
    address?: string;
    city?: string;
    latitude?: number;
    longitude?: number;
    photoBlobContentType?: string;
    photoBlob?: any;
    worker?: IUser;
    orders?: IFoodOrder[];
    menus?: IMenu[];
}

export class Restaurant implements IRestaurant {
    constructor(
        public id?: number,
        public nameSlug?: string,
        public address?: string,
        public city?: string,
        public latitude?: number,
        public longitude?: number,
        public photoBlobContentType?: string,
        public photoBlob?: any,
        public worker?: IUser,
        public orders?: IFoodOrder[],
        public menus?: IMenu[]
    ) {}
}
