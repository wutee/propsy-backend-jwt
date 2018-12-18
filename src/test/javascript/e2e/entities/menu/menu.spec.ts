/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MenuComponentsPage, MenuDeleteDialog, MenuUpdatePage } from './menu.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Menu e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let menuUpdatePage: MenuUpdatePage;
    let menuComponentsPage: MenuComponentsPage;
    let menuDeleteDialog: MenuDeleteDialog;
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

    it('should load Menus', async () => {
        await navBarPage.goToEntity('menu');
        menuComponentsPage = new MenuComponentsPage();
        expect(await menuComponentsPage.getTitle()).to.eq('propsyBackendJwtApp.menu.home.title');
    });

    it('should load create Menu page', async () => {
        await menuComponentsPage.clickOnCreateButton();
        menuUpdatePage = new MenuUpdatePage();
        expect(await menuUpdatePage.getPageTitle()).to.eq('propsyBackendJwtApp.menu.home.createOrEditLabel');
        await menuUpdatePage.cancel();
    });

    it('should create and save Menus', async () => {
        const nbButtonsBeforeCreate = await menuComponentsPage.countDeleteButtons();

        await menuComponentsPage.clickOnCreateButton();
        await promise.all([
            menuUpdatePage.setNameSlugInput('nameSlug'),
            menuUpdatePage.setPhotoBlobInput(absolutePath),
            menuUpdatePage.restaurantSelectLastOption()
            // menuUpdatePage.foodItemsSelectLastOption(),
        ]);
        expect(await menuUpdatePage.getNameSlugInput()).to.eq('nameSlug');
        expect(await menuUpdatePage.getPhotoBlobInput()).to.endsWith(fileNameToUpload);
        await menuUpdatePage.save();
        expect(await menuUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await menuComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Menu', async () => {
        const nbButtonsBeforeDelete = await menuComponentsPage.countDeleteButtons();
        await menuComponentsPage.clickOnLastDeleteButton();

        menuDeleteDialog = new MenuDeleteDialog();
        expect(await menuDeleteDialog.getDialogTitle()).to.eq('propsyBackendJwtApp.menu.delete.question');
        await menuDeleteDialog.clickOnConfirmButton();

        expect(await menuComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
