import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Name } from './name.model';
import { NamePopupService } from './name-popup.service';
import { NameService } from './name.service';

@Component({
    selector: 'jhi-name-delete-dialog',
    templateUrl: './name-delete-dialog.component.html'
})
export class NameDeleteDialogComponent {

    name: Name;

    constructor(
        private nameService: NameService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.nameService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'nameListModification',
                content: 'Deleted an name'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-name-delete-popup',
    template: ''
})
export class NameDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private namePopupService: NamePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.namePopupService
                .open(NameDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
