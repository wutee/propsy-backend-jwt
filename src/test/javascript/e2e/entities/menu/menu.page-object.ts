import { element, by, ElementFinder } from 'protractor';

export class MenuComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-menu div table .btn-danger'));
    title = element.all(by.css('jhi-menu div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MenuUpdatePage {
    pageTitle = element(by.id('jhi-menu-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameSlugInput = element(by.id('field_nameSlug'));
    photoBlobInput = element(by.id('file_photoBlob'));
    restaurantSelect = element(by.id('field_restaurant'));
    foodItemsSelect = element(by.id('field_foodItems'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNameSlugInput(nameSlug) {
        await this.nameSlugInput.sendKeys(nameSlug);
    }

    async getNameSlugInput() {
        return this.nameSlugInput.getAttribute('value');
    }

    async setPhotoBlobInput(photoBlob) {
        await this.photoBlobInput.sendKeys(photoBlob);
    }

    async getPhotoBlobInput() {
        return this.photoBlobInput.getAttribute('value');
    }

    async restaurantSelectLastOption() {
        await this.restaurantSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async restaurantSelectOption(option) {
        await this.restaurantSelect.sendKeys(option);
    }

    getRestaurantSelect(): ElementFinder {
        return this.restaurantSelect;
    }

    async getRestaurantSelectedOption() {
        return this.restaurantSelect.element(by.css('option:checked')).getText();
    }

    async foodItemsSelectLastOption() {
        await this.foodItemsSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async foodItemsSelectOption(option) {
        await this.foodItemsSelect.sendKeys(option);
    }

    getFoodItemsSelect(): ElementFinder {
        return this.foodItemsSelect;
    }

    async getFoodItemsSelectedOption() {
        return this.foodItemsSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class MenuDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-menu-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-menu'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
