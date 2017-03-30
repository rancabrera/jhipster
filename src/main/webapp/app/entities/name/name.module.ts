import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyAppSharedModule } from '../../shared';

import {
    NameService,
    NamePopupService,
    NameComponent,
    NameDetailComponent,
    NameDialogComponent,
    NamePopupComponent,
    NameDeletePopupComponent,
    NameDeleteDialogComponent,
    nameRoute,
    namePopupRoute,
    NameResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...nameRoute,
    ...namePopupRoute,
];

@NgModule({
    imports: [
        MyAppSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        NameComponent,
        NameDetailComponent,
        NameDialogComponent,
        NameDeleteDialogComponent,
        NamePopupComponent,
        NameDeletePopupComponent,
    ],
    entryComponents: [
        NameComponent,
        NameDialogComponent,
        NamePopupComponent,
        NameDeleteDialogComponent,
        NameDeletePopupComponent,
    ],
    providers: [
        NameService,
        NamePopupService,
        NameResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyAppNameModule {}
