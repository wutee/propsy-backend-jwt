/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ExtendedUserComponentsPage, ExtendedUserDeleteDialog, ExtendedUserUpdatePage } from './extended-user.page-object';

const expect = chai.expect;

describe('ExtendedUser e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let extendedUserUpdatePage: ExtendedUserUpdatePage;
    let extendedUserComponentsPage: ExtendedUserComponentsPage;
    let extendedUserDeleteDialog: ExtendedUserDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ExtendedUsers', async () => {
        await navBarPage.goToEntity('extended-user');
        extendedUserComponentsPage = new ExtendedUserComponentsPage();
        expect(await extendedUserComponentsPage.getTitle()).to.eq('propsyBackendJwtApp.extendedUser.home.title');
    });

    it('should load create ExtendedUser page', async () => {
        await extendedUserComponentsPage.clickOnCreateButton();
        extendedUserUpdatePage = new ExtendedUserUpdatePage();
        expect(await extendedUserUpdatePage.getPageTitle()).to.eq('propsyBackendJwtApp.extendedUser.home.createOrEditLabel');
        await extendedUserUpdatePage.cancel();
    });

    it('should create and save ExtendedUsers', async () => {
        const nbButtonsBeforeCreate = await extendedUserComponentsPage.countDeleteButtons();

        await extendedUserComponentsPage.clickOnCreateButton();
        await promise.all([
            extendedUserUpdatePage.setNameInput('name'),
            extendedUserUpdatePage.setAddressInput('address'),
            extendedUserUpdatePage.setCityInput('city'),
            extendedUserUpdatePage.setPhoneInput('phone'),
            extendedUserUpdatePage.setLoyaltyPointsInput('5'),
            extendedUserUpdatePage.userSelectLastOption()
        ]);
        expect(await extendedUserUpdatePage.getNameInput()).to.eq('name');
        expect(await extendedUserUpdatePage.getAddressInput()).to.eq('address');
        expect(await extendedUserUpdatePage.getCityInput()).to.eq('city');
        expect(await extendedUserUpdatePage.getPhoneInput()).to.eq('phone');
        expect(await extendedUserUpdatePage.getLoyaltyPointsInput()).to.eq('5');
        await extendedUserUpdatePage.save();
        expect(await extendedUserUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await extendedUserComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ExtendedUser', async () => {
        const nbButtonsBeforeDelete = await extendedUserComponentsPage.countDeleteButtons();
        await extendedUserComponentsPage.clickOnLastDeleteButton();

        extendedUserDeleteDialog = new ExtendedUserDeleteDialog();
        expect(await extendedUserDeleteDialog.getDialogTitle()).to.eq('propsyBackendJwtApp.extendedUser.delete.question');
        await extendedUserDeleteDialog.clickOnConfirmButton();

        expect(await extendedUserComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
