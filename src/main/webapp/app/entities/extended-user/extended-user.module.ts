import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PropsyBackendJwtSharedModule } from 'app/shared';
import { PropsyBackendJwtAdminModule } from 'app/admin/admin.module';
import {
    ExtendedUserComponent,
    ExtendedUserDetailComponent,
    ExtendedUserUpdateComponent,
    ExtendedUserDeletePopupComponent,
    ExtendedUserDeleteDialogComponent,
    extendedUserRoute,
    extendedUserPopupRoute
} from './';

const ENTITY_STATES = [...extendedUserRoute, ...extendedUserPopupRoute];

@NgModule({
    imports: [PropsyBackendJwtSharedModule, PropsyBackendJwtAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ExtendedUserComponent,
        ExtendedUserDetailComponent,
        ExtendedUserUpdateComponent,
        ExtendedUserDeleteDialogComponent,
        ExtendedUserDeletePopupComponent
    ],
    entryComponents: [
        ExtendedUserComponent,
        ExtendedUserUpdateComponent,
        ExtendedUserDeleteDialogComponent,
        ExtendedUserDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropsyBackendJwtExtendedUserModule {}
