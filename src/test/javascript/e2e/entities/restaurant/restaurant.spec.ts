/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RestaurantComponentsPage, RestaurantDeleteDialog, RestaurantUpdatePage } from './restaurant.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Restaurant e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let restaurantUpdatePage: RestaurantUpdatePage;
    let restaurantComponentsPage: RestaurantComponentsPage;
    let restaurantDeleteDialog: RestaurantDeleteDialog;
    const fileNameToUpload = 'logo-jhipster.png';
    const fileToUpload = '../../../../../main/webapp/content/images/' + fileNameToUpload;
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Restaurants', async () => {
        await navBarPage.goToEntity('restaurant');
        restaurantComponentsPage = new RestaurantComponentsPage();
        expect(await restaurantComponentsPage.getTitle()).to.eq('propsyBackendJwtApp.restaurant.home.title');
    });

    it('should load create Restaurant page', async () => {
        await restaurantComponentsPage.clickOnCreateButton();
        restaurantUpdatePage = new RestaurantUpdatePage();
        expect(await restaurantUpdatePage.getPageTitle()).to.eq('propsyBackendJwtApp.restaurant.home.createOrEditLabel');
        await restaurantUpdatePage.cancel();
    });

    it('should create and save Restaurants', async () => {
        const nbButtonsBeforeCreate = await restaurantComponentsPage.countDeleteButtons();

        await restaurantComponentsPage.clickOnCreateButton();
        await promise.all([
            restaurantUpdatePage.setNameSlugInput('nameSlug'),
            restaurantUpdatePage.setAddressInput('address'),
            restaurantUpdatePage.setCityInput('city'),
            restaurantUpdatePage.setLatitudeInput('5'),
            restaurantUpdatePage.setLongitudeInput('5'),
            restaurantUpdatePage.setPhotoBlobInput(absolutePath),
            restaurantUpdatePage.workerSelectLastOption()
        ]);
        expect(await restaurantUpdatePage.getNameSlugInput()).to.eq('nameSlug');
        expect(await restaurantUpdatePage.getAddressInput()).to.eq('address');
        expect(await restaurantUpdatePage.getCityInput()).to.eq('city');
        expect(await restaurantUpdatePage.getLatitudeInput()).to.eq('5');
        expect(await restaurantUpdatePage.getLongitudeInput()).to.eq('5');
        expect(await restaurantUpdatePage.getPhotoBlobInput()).to.endsWith(fileNameToUpload);
        await restaurantUpdatePage.save();
        expect(await restaurantUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await restaurantComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Restaurant', async () => {
        const nbButtonsBeforeDelete = await restaurantComponentsPage.countDeleteButtons();
        await restaurantComponentsPage.clickOnLastDeleteButton();

        restaurantDeleteDialog = new RestaurantDeleteDialog();
        expect(await restaurantDeleteDialog.getDialogTitle()).to.eq('propsyBackendJwtApp.restaurant.delete.question');
        await restaurantDeleteDialog.clickOnConfirmButton();

        expect(await restaurantComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
