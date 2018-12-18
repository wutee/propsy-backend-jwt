/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FoodComponentsPage, FoodDeleteDialog, FoodUpdatePage } from './food.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Food e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let foodUpdatePage: FoodUpdatePage;
    let foodComponentsPage: FoodComponentsPage;
    let foodDeleteDialog: FoodDeleteDialog;
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

    it('should load Foods', async () => {
        await navBarPage.goToEntity('food');
        foodComponentsPage = new FoodComponentsPage();
        expect(await foodComponentsPage.getTitle()).to.eq('propsyBackendJwtApp.food.home.title');
    });

    it('should load create Food page', async () => {
        await foodComponentsPage.clickOnCreateButton();
        foodUpdatePage = new FoodUpdatePage();
        expect(await foodUpdatePage.getPageTitle()).to.eq('propsyBackendJwtApp.food.home.createOrEditLabel');
        await foodUpdatePage.cancel();
    });

    it('should create and save Foods', async () => {
        const nbButtonsBeforeCreate = await foodComponentsPage.countDeleteButtons();

        await foodComponentsPage.clickOnCreateButton();
        await promise.all([
            foodUpdatePage.setNameSlugInput('nameSlug'),
            foodUpdatePage.setPriceInput('5'),
            foodUpdatePage.setFoodDescriptionInput('foodDescription'),
            foodUpdatePage.setCaloriesInput('5'),
            foodUpdatePage.setPhotoBlobInput(absolutePath)
        ]);
        expect(await foodUpdatePage.getNameSlugInput()).to.eq('nameSlug');
        expect(await foodUpdatePage.getPriceInput()).to.eq('5');
        expect(await foodUpdatePage.getFoodDescriptionInput()).to.eq('foodDescription');
        expect(await foodUpdatePage.getCaloriesInput()).to.eq('5');
        const selectedIsSpicy = foodUpdatePage.getIsSpicyInput();
        if (await selectedIsSpicy.isSelected()) {
            await foodUpdatePage.getIsSpicyInput().click();
            expect(await foodUpdatePage.getIsSpicyInput().isSelected()).to.be.false;
        } else {
            await foodUpdatePage.getIsSpicyInput().click();
            expect(await foodUpdatePage.getIsSpicyInput().isSelected()).to.be.true;
        }
        const selectedIsVegetarian = foodUpdatePage.getIsVegetarianInput();
        if (await selectedIsVegetarian.isSelected()) {
            await foodUpdatePage.getIsVegetarianInput().click();
            expect(await foodUpdatePage.getIsVegetarianInput().isSelected()).to.be.false;
        } else {
            await foodUpdatePage.getIsVegetarianInput().click();
            expect(await foodUpdatePage.getIsVegetarianInput().isSelected()).to.be.true;
        }
        const selectedIsGlutenFree = foodUpdatePage.getIsGlutenFreeInput();
        if (await selectedIsGlutenFree.isSelected()) {
            await foodUpdatePage.getIsGlutenFreeInput().click();
            expect(await foodUpdatePage.getIsGlutenFreeInput().isSelected()).to.be.false;
        } else {
            await foodUpdatePage.getIsGlutenFreeInput().click();
            expect(await foodUpdatePage.getIsGlutenFreeInput().isSelected()).to.be.true;
        }
        expect(await foodUpdatePage.getPhotoBlobInput()).to.endsWith(fileNameToUpload);
        await foodUpdatePage.save();
        expect(await foodUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await foodComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Food', async () => {
        const nbButtonsBeforeDelete = await foodComponentsPage.countDeleteButtons();
        await foodComponentsPage.clickOnLastDeleteButton();

        foodDeleteDialog = new FoodDeleteDialog();
        expect(await foodDeleteDialog.getDialogTitle()).to.eq('propsyBackendJwtApp.food.delete.question');
        await foodDeleteDialog.clickOnConfirmButton();

        expect(await foodComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
