import { IMenu } from 'app/shared/model//menu.model';
import { IFoodOrder } from 'app/shared/model//food-order.model';

export interface IFood {
    id?: number;
    nameSlug?: string;
    price?: number;
    foodDescription?: string;
    calories?: number;
    isSpicy?: boolean;
    isVegetarian?: boolean;
    isGlutenFree?: boolean;
    photoBlobContentType?: string;
    photoBlob?: any;
    menus?: IMenu[];
    orders?: IFoodOrder[];
}

export class Food implements IFood {
    constructor(
        public id?: number,
        public nameSlug?: string,
        public price?: number,
        public foodDescription?: string,
        public calories?: number,
        public isSpicy?: boolean,
        public isVegetarian?: boolean,
        public isGlutenFree?: boolean,
        public photoBlobContentType?: string,
        public photoBlob?: any,
        public menus?: IMenu[],
        public orders?: IFoodOrder[]
    ) {
        this.isSpicy = this.isSpicy || false;
        this.isVegetarian = this.isVegetarian || false;
        this.isGlutenFree = this.isGlutenFree || false;
    }
}
