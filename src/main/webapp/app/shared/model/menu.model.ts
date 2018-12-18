import { IRestaurant } from 'app/shared/model//restaurant.model';
import { IFood } from 'app/shared/model//food.model';

export interface IMenu {
    id?: number;
    nameSlug?: string;
    photoBlobContentType?: string;
    photoBlob?: any;
    restaurant?: IRestaurant;
    foodItems?: IFood[];
}

export class Menu implements IMenu {
    constructor(
        public id?: number,
        public nameSlug?: string,
        public photoBlobContentType?: string,
        public photoBlob?: any,
        public restaurant?: IRestaurant,
        public foodItems?: IFood[]
    ) {}
}
