import { element, by, ElementFinder } from 'protractor';

export class FoodOrderMovementComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-food-order-movement div table .btn-danger'));
    title = element.all(by.css('jhi-food-order-movement div h2#page-heading span')).first();

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

export class FoodOrderMovementUpdatePage {
    pageTitle = element(by.id('jhi-food-order-movement-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    dateInput = element(by.id('field_date'));
    latitudeInput = element(by.id('field_latitude'));
    longitudeInput = element(by.id('field_longitude'));
    senderSelect = element(by.id('field_sender'));
    foodOrderSelect = element(by.id('field_foodOrder'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setDateInput(date) {
        await this.dateInput.sendKeys(date);
    }

    async getDateInput() {
        return this.dateInput.getAttribute('value');
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

    async setSenderSelect(sender) {
        await this.senderSelect.sendKeys(sender);
    }

    async getSenderSelect() {
        return this.senderSelect.element(by.css('option:checked')).getText();
    }

    async senderSelectLastOption() {
        await this.senderSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async foodOrderSelectLastOption() {
        await this.foodOrderSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async foodOrderSelectOption(option) {
        await this.foodOrderSelect.sendKeys(option);
    }

    getFoodOrderSelect(): ElementFinder {
        return this.foodOrderSelect;
    }

    async getFoodOrderSelectedOption() {
        return this.foodOrderSelect.element(by.css('option:checked')).getText();
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

export class FoodOrderMovementDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-foodOrderMovement-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-foodOrderMovement'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
