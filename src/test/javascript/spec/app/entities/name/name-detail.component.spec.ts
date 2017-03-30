import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { NameDetailComponent } from '../../../../../../main/webapp/app/entities/name/name-detail.component';
import { NameService } from '../../../../../../main/webapp/app/entities/name/name.service';
import { Name } from '../../../../../../main/webapp/app/entities/name/name.model';

describe('Component Tests', () => {

    describe('Name Management Detail Component', () => {
        let comp: NameDetailComponent;
        let fixture: ComponentFixture<NameDetailComponent>;
        let service: NameService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [NameDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    NameService
                ]
            }).overrideComponent(NameDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NameDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NameService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Name(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.name).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
