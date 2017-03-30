import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Name } from './name.model';
import { NameService } from './name.service';
@Injectable()
export class NamePopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private nameService: NameService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.nameService.find(id).subscribe(name => {
                if (name.startDate) {
                    name.startDate = {
                        year: name.startDate.getFullYear(),
                        month: name.startDate.getMonth() + 1,
                        day: name.startDate.getDate()
                    };
                }
                this.nameModalRef(component, name);
            });
        } else {
            return this.nameModalRef(component, new Name());
        }
    }

    nameModalRef(component: Component, name: Name): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.name = name;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
