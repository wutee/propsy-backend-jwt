/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    FoodOrderMovementComponentsPage,
    FoodOrderMovementDeleteDialog,
    FoodOrderMovementUpdatePage
} from './food-order-movement.page-object';

const expect = chai.expect;

describe('FoodOrderMovement e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let foodOrderMovementUpdatePage: FoodOrderMovementUpdatePage;
    let foodOrderMovementComponentsPage: FoodOrderMovementComponentsPage;
    let foodOrderMovementDeleteDialog: FoodOrderMovementDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load FoodOrderMovements', async () => {
        await navBarPage.goToEntity('food-order-movement');
        foodOrderMovementComponentsPage = new FoodOrderMovementComponentsPage();
        expect(await foodOrderMovementComponentsPage.getTitle()).to.eq('propsyBackendJwtApp.foodOrderMovement.home.title');
    });

    it('should load create FoodOrderMovement page', async () => {
        await foodOrderMovementComponentsPage.clickOnCreateButton();
        foodOrderMovementUpdatePage = new FoodOrderMovementUpdatePage();
        expect(await foodOrderMovementUpdatePage.getPageTitle()).to.eq('propsyBackendJwtApp.foodOrderMovement.home.createOrEditLabel');
        await foodOrderMovementUpdatePage.cancel();
    });

    it('should create and save FoodOrderMovements', async () => {
        const nbButtonsBeforeCreate = await foodOrderMovementComponentsPage.countDeleteButtons();

        await foodOrderMovementComponentsPage.clickOnCreateButton();
        await promise.all([
            foodOrderMovementUpdatePage.setDateInput('2000-12-31'),
            foodOrderMovementUpdatePage.setLatitudeInput('5'),
            foodOrderMovementUpdatePage.setLongitudeInput('5'),
            foodOrderMovementUpdatePage.senderSelectLastOption(),
            foodOrderMovementUpdatePage.foodOrderSelectLastOption()
        ]);
        expect(await foodOrderMovementUpdatePage.getDateInput()).to.eq('2000-12-31');
        expect(await foodOrderMovementUpdatePage.getLatitudeInput()).to.eq('5');
        expect(await foodOrderMovementUpdatePage.getLongitudeInput()).to.eq('5');
        await foodOrderMovementUpdatePage.save();
        expect(await foodOrderMovementUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await foodOrderMovementComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last FoodOrderMovement', async () => {
        const nbButtonsBeforeDelete = await foodOrderMovementComponentsPage.countDeleteButtons();
        await foodOrderMovementComponentsPage.clickOnLastDeleteButton();

        foodOrderMovementDeleteDialog = new FoodOrderMovementDeleteDialog();
        expect(await foodOrderMovementDeleteDialog.getDialogTitle()).to.eq('propsyBackendJwtApp.foodOrderMovement.delete.question');
        await foodOrderMovementDeleteDialog.clickOnConfirmButton();

        expect(await foodOrderMovementComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
