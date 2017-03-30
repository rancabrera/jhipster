import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ProjectComponent } from './project.component';
import { ProjectDetailComponent } from './project-detail.component';
import { ProjectPopupComponent } from './project-dialog.component';
import { ProjectDeletePopupComponent } from './project-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ProjectResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      let page = route.queryParams['page'] ? route.queryParams['page'] : '1';
      let sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
      return {
          page: this.paginationUtil.parsePage(page),
          predicate: this.paginationUtil.parsePredicate(sort),
          ascending: this.paginationUtil.parseAscending(sort)
    };
  }
}

export const projectRoute: Routes = [
  {
    path: 'project',
    component: ProjectComponent,
    resolve: {
      'pagingParams': ProjectResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Projects'
    }
  }, {
    path: 'project/:id',
    component: ProjectDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Projects'
    }
  }
];

export const projectPopupRoute: Routes = [
  {
    path: 'project-new',
    component: ProjectPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Projects'
    },
    outlet: 'popup'
  },
  {
    path: 'project/:id/edit',
    component: ProjectPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Projects'
    },
    outlet: 'popup'
  },
  {
    path: 'project/:id/delete',
    component: ProjectDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Projects'
    },
    outlet: 'popup'
  }
];
