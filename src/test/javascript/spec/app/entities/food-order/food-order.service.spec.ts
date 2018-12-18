/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { FoodOrderService } from 'app/entities/food-order/food-order.service';
import { IFoodOrder, FoodOrder, OrderStatus } from 'app/shared/model/food-order.model';

describe('Service Tests', () => {
    describe('FoodOrder Service', () => {
        let injector: TestBed;
        let service: FoodOrderService;
        let httpMock: HttpTestingController;
        let elemDefault: IFoodOrder;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(FoodOrderService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new FoodOrder(
                0,
                0,
                0,
                0,
                0,
                0,
                currentDate,
                0,
                OrderStatus.NEW,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        date: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a FoodOrder', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        date: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        date: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new FoodOrder(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a FoodOrder', async () => {
                const returnedFromService = Object.assign(
                    {
                        timeRating: 1,
                        priceRating: 1,
                        qualityRating: 1,
                        loyaltyPoints: 1,
                        addressRating: 1,
                        date: currentDate.format(DATE_FORMAT),
                        price: 1,
                        status: 'BBBBBB',
                        purchaserOpinion: 'BBBBBB',
                        purchaserComment: 'BBBBBB',
                        city: 'BBBBBB',
                        phone: 'BBBBBB',
                        address: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        date: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of FoodOrder', async () => {
                const returnedFromService = Object.assign(
                    {
                        timeRating: 1,
                        priceRating: 1,
                        qualityRating: 1,
                        loyaltyPoints: 1,
                        addressRating: 1,
                        date: currentDate.format(DATE_FORMAT),
                        price: 1,
                        status: 'BBBBBB',
                        purchaserOpinion: 'BBBBBB',
                        purchaserComment: 'BBBBBB',
                        city: 'BBBBBB',
                        phone: 'BBBBBB',
                        address: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        date: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a FoodOrder', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
