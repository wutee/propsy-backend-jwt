/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { FoodService } from 'app/entities/food/food.service';
import { IFood, Food } from 'app/shared/model/food.model';

describe('Service Tests', () => {
    describe('Food Service', () => {
        let injector: TestBed;
        let service: FoodService;
        let httpMock: HttpTestingController;
        let elemDefault: IFood;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(FoodService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Food(0, 'AAAAAAA', 0, 'AAAAAAA', 0, false, false, false, 'image/png', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Food', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new Food(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Food', async () => {
                const returnedFromService = Object.assign(
                    {
                        nameSlug: 'BBBBBB',
                        price: 1,
                        foodDescription: 'BBBBBB',
                        calories: 1,
                        isSpicy: true,
                        isVegetarian: true,
                        isGlutenFree: true,
                        photoBlob: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Food', async () => {
                const returnedFromService = Object.assign(
                    {
                        nameSlug: 'BBBBBB',
                        price: 1,
                        foodDescription: 'BBBBBB',
                        calories: 1,
                        isSpicy: true,
                        isVegetarian: true,
                        isGlutenFree: true,
                        photoBlob: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
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

            it('should delete a Food', async () => {
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
