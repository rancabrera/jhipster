import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { Team } from './team.model';
import { TeamService } from './team.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-team',
    templateUrl: './team.component.html'
})
export class TeamComponent implements OnInit, OnDestroy {

    teams: Team[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;

    constructor(
        private teamService: TeamService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private parseLinks: ParseLinks,
        private principal: Principal
    ) {
        this.teams = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll () {
        this.teamService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: Response) => this.onSuccess(res.json(), res.headers),
            (res: Response) => this.onError(res.json())
        );
    }

    reset () {
        this.page = 0;
        this.teams = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTeams();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: Team) {
        return item.id;
    }



    registerChangeInTeams() {
        this.eventSubscriber = this.eventManager.subscribe('teamListModification', (response) => this.reset());
    }

    sort () {
        let result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        for (let i = 0; i < data.length; i++) {
            this.teams.push(data[i]);
        }
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
