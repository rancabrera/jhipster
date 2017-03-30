import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Name } from './name.model';
import { NameService } from './name.service';

@Component({
    selector: 'jhi-name-detail',
    templateUrl: './name-detail.component.html'
})
export class NameDetailComponent implements OnInit, OnDestroy {

    name: Name;
    private subscription: any;

    constructor(
        private nameService: NameService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.nameService.find(id).subscribe(name => {
            this.name = name;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
