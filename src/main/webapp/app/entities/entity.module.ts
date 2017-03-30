import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MyAppNameModule } from './name/name.module';
import { MyAppTeamModule } from './team/team.module';
import { MyAppDepartmentModule } from './department/department.module';
import { MyAppProjectModule } from './project/project.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        MyAppNameModule,
        MyAppTeamModule,
        MyAppDepartmentModule,
        MyAppProjectModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyAppEntityModule {}
