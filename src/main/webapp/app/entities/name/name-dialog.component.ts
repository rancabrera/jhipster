import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Name } from './name.model';
import { NamePopupService } from './name-popup.service';
import { NameService } from './name.service';
import { Department, DepartmentService } from '../department';

@Component({
    selector: 'jhi-name-dialog',
    templateUrl: './name-dialog.component.html'
})
export class NameDialogComponent implements OnInit {

    name: Name;
    authorities: any[];
    isSaving: boolean;

    departments: Department[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private nameService: NameService,
        private departmentService: DepartmentService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.departmentService.query().subscribe(
            (res: Response) => { this.departments = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.name.id !== undefined) {
            this.nameService.update(this.name)
                .subscribe((res: Name) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.nameService.create(this.name)
                .subscribe((res: Name) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Name) {
        this.eventManager.broadcast({ name: 'nameListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackDepartmentById(index: number, item: Department) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-name-popup',
    template: ''
})
export class NamePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private namePopupService: NamePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.namePopupService
                    .open(NameDialogComponent, params['id']);
            } else {
                this.modalRef = this.namePopupService
                    .open(NameDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
