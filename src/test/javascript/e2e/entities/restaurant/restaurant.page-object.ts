import { element, by, ElementFinder } from 'protractor';

export class RestaurantComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-restaurant div table .btn-danger'));
    title = element.all(by.css('jhi-restaurant div h2#page-heading span')).first();

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

export class RestaurantUpdatePage {
    pageTitle = element(by.id('jhi-restaurant-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameSlugInput = element(by.id('field_nameSlug'));
    addressInput = element(by.id('field_address'));
    cityInput = element(by.id('field_city'));
    latitudeInput = element(by.id('field_latitude'));
    longitudeInput = element(by.id('field_longitude'));
    photoBlobInput = element(by.id('file_photoBlob'));
    workerSelect = element(by.id('field_worker'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNameSlugInput(nameSlug) {
        await this.nameSlugInput.sendKeys(nameSlug);
    }

    async getNameSlugInput() {
        return this.nameSlugInput.getAttribute('value');
    }

    async setAddressInput(address) {
        await this.addressInput.sendKeys(address);
    }

    async getAddressInput() {
        return this.addressInput.getAttribute('value');
    }

    async setCityInput(city) {
        await this.cityInput.sendKeys(city);
    }

    async getCityInput() {
        return this.cityInput.getAttribute('value');
    }

    async setLatitudeInput(latitude) {
        await this.latitudeInput.sendKeys(latitude);
    }

    async getLatitudeInput() {
        return this.latitudeInput.getAttribute('value');
    }

    async setLongitudeInput(longitude) {
        await this.longitudeInput.sendKeys(longitude);
    }

    async getLongitudeInput() {
        return this.longitudeInput.getAttribute('value');
    }

    async setPhotoBlobInput(photoBlob) {
        await this.photoBlobInput.sendKeys(photoBlob);
    }

    async getPhotoBlobInput() {
        return this.photoBlobInput.getAttribute('value');
    }

    async workerSelectLastOption() {
        await this.workerSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async workerSelectOption(option) {
        await this.workerSelect.sendKeys(option);
    }

    getWorkerSelect(): ElementFinder {
        return this.workerSelect;
    }

    async getWorkerSelectedOption() {
        return this.workerSelect.element(by.css('option:checked')).getText();
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

export class RestaurantDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-restaurant-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-restaurant'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
