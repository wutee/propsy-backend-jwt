import { IUser } from 'app/core/user/user.model';

export interface IExtendedUser {
    id?: number;
    name?: string;
    address?: string;
    city?: string;
    phone?: string;
    loyaltyPoints?: number;
    user?: IUser;
}

export class ExtendedUser implements IExtendedUser {
    constructor(
        public id?: number,
        public name?: string,
        public address?: string,
        public city?: string,
        public phone?: string,
        public loyaltyPoints?: number,
        public user?: IUser
    ) {}
}
