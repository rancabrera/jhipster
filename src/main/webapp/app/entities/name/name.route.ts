import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { NameComponent } from './name.component';
import { NameDetailComponent } from './name-detail.component';
import { NamePopupComponent } from './name-dialog.component';
import { NameDeletePopupComponent } from './name-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class NameResolvePagingParams implements Resolve<any> {

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

export const nameRoute: Routes = [
  {
    path: 'name',
    component: NameComponent,
    resolve: {
      'pagingParams': NameResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Names'
    }
  }, {
    path: 'name/:id',
    component: NameDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Names'
    }
  }
];

export const namePopupRoute: Routes = [
  {
    path: 'name-new',
    component: NamePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Names'
    },
    outlet: 'popup'
  },
  {
    path: 'name/:id/edit',
    component: NamePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Names'
    },
    outlet: 'popup'
  },
  {
    path: 'name/:id/delete',
    component: NameDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Names'
    },
    outlet: 'popup'
  }
];
