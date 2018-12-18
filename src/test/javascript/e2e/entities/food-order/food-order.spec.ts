/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FoodOrderComponentsPage, FoodOrderDeleteDialog, FoodOrderUpdatePage } from './food-order.page-object';

const expect = chai.expect;

describe('FoodOrder e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let foodOrderUpdatePage: FoodOrderUpdatePage;
    let foodOrderComponentsPage: FoodOrderComponentsPage;
    let foodOrderDeleteDialog: FoodOrderDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load FoodOrders', async () => {
        await navBarPage.goToEntity('food-order');
        foodOrderComponentsPage = new FoodOrderComponentsPage();
        expect(await foodOrderComponentsPage.getTitle()).to.eq('propsyBackendJwtApp.foodOrder.home.title');
    });

    it('should load create FoodOrder page', async () => {
        await foodOrderComponentsPage.clickOnCreateButton();
        foodOrderUpdatePage = new FoodOrderUpdatePage();
        expect(await foodOrderUpdatePage.getPageTitle()).to.eq('propsyBackendJwtApp.foodOrder.home.createOrEditLabel');
        await foodOrderUpdatePage.cancel();
    });

    it('should create and save FoodOrders', async () => {
        const nbButtonsBeforeCreate = await foodOrderComponentsPage.countDeleteButtons();

        await foodOrderComponentsPage.clickOnCreateButton();
        await promise.all([
            foodOrderUpdatePage.setTimeRatingInput('5'),
            foodOrderUpdatePage.setPriceRatingInput('5'),
            foodOrderUpdatePage.setQualityRatingInput('5'),
            foodOrderUpdatePage.setLoyaltyPointsInput('5'),
            foodOrderUpdatePage.setAddressRatingInput('5'),
            foodOrderUpdatePage.setDateInput('2000-12-31'),
            foodOrderUpdatePage.setPriceInput('5'),
            foodOrderUpdatePage.statusSelectLastOption(),
            foodOrderUpdatePage.setPurchaserOpinionInput('purchaserOpinion'),
            foodOrderUpdatePage.setPurchaserCommentInput('purchaserComment'),
            foodOrderUpdatePage.setCityInput('city'),
            foodOrderUpdatePage.setPhoneInput('phone'),
            foodOrderUpdatePage.setAddressInput('address'),
            foodOrderUpdatePage.restaurantSelectLastOption(),
            foodOrderUpdatePage.deliverymanSelectLastOption(),
            foodOrderUpdatePage.purchaserSelectLastOption()
            // foodOrderUpdatePage.foodItemsSelectLastOption(),
        ]);
        expect(await foodOrderUpdatePage.getTimeRatingInput()).to.eq('5');
        expect(await foodOrderUpdatePage.getPriceRatingInput()).to.eq('5');
        expect(await foodOrderUpdatePage.getQualityRatingInput()).to.eq('5');
        expect(await foodOrderUpdatePage.getLoyaltyPointsInput()).to.eq('5');
        expect(await foodOrderUpdatePage.getAddressRatingInput()).to.eq('5');
        expect(await foodOrderUpdatePage.getDateInput()).to.eq('2000-12-31');
        expect(await foodOrderUpdatePage.getPriceInput()).to.eq('5');
        expect(await foodOrderUpdatePage.getPurchaserOpinionInput()).to.eq('purchaserOpinion');
        expect(await foodOrderUpdatePage.getPurchaserCommentInput()).to.eq('purchaserComment');
        expect(await foodOrderUpdatePage.getCityInput()).to.eq('city');
        expect(await foodOrderUpdatePage.getPhoneInput()).to.eq('phone');
        expect(await foodOrderUpdatePage.getAddressInput()).to.eq('address');
        await foodOrderUpdatePage.save();
        expect(await foodOrderUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await foodOrderComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last FoodOrder', async () => {
        const nbButtonsBeforeDelete = await foodOrderComponentsPage.countDeleteButtons();
        await foodOrderComponentsPage.clickOnLastDeleteButton();

        foodOrderDeleteDialog = new FoodOrderDeleteDialog();
        expect(await foodOrderDeleteDialog.getDialogTitle()).to.eq('propsyBackendJwtApp.foodOrder.delete.question');
        await foodOrderDeleteDialog.clickOnConfirmButton();

        expect(await foodOrderComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
