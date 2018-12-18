import { element, by, ElementFinder } from 'protractor';

export class ExtendedUserComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-extended-user div table .btn-danger'));
    title = element.all(by.css('jhi-extended-user div h2#page-heading span')).first();

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

export class ExtendedUserUpdatePage {
    pageTitle = element(by.id('jhi-extended-user-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    addressInput = element(by.id('field_address'));
    cityInput = element(by.id('field_city'));
    phoneInput = element(by.id('field_phone'));
    loyaltyPointsInput = element(by.id('field_loyaltyPoints'));
    userSelect = element(by.id('field_user'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
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

    async setPhoneInput(phone) {
        await this.phoneInput.sendKeys(phone);
    }

    async getPhoneInput() {
        return this.phoneInput.getAttribute('value');
    }

    async setLoyaltyPointsInput(loyaltyPoints) {
        await this.loyaltyPointsInput.sendKeys(loyaltyPoints);
    }

    async getLoyaltyPointsInput() {
        return this.loyaltyPointsInput.getAttribute('value');
    }

    async userSelectLastOption() {
        await this.userSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async userSelectOption(option) {
        await this.userSelect.sendKeys(option);
    }

    getUserSelect(): ElementFinder {
        return this.userSelect;
    }

    async getUserSelectedOption() {
        return this.userSelect.element(by.css('option:checked')).getText();
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

export class ExtendedUserDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-extendedUser-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-extendedUser'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
