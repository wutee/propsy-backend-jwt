import { element, by, ElementFinder } from 'protractor';

export class FoodComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-food div table .btn-danger'));
    title = element.all(by.css('jhi-food div h2#page-heading span')).first();

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

export class FoodUpdatePage {
    pageTitle = element(by.id('jhi-food-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameSlugInput = element(by.id('field_nameSlug'));
    priceInput = element(by.id('field_price'));
    foodDescriptionInput = element(by.id('field_foodDescription'));
    caloriesInput = element(by.id('field_calories'));
    isSpicyInput = element(by.id('field_isSpicy'));
    isVegetarianInput = element(by.id('field_isVegetarian'));
    isGlutenFreeInput = element(by.id('field_isGlutenFree'));
    photoBlobInput = element(by.id('file_photoBlob'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNameSlugInput(nameSlug) {
        await this.nameSlugInput.sendKeys(nameSlug);
    }

    async getNameSlugInput() {
        return this.nameSlugInput.getAttribute('value');
    }

    async setPriceInput(price) {
        await this.priceInput.sendKeys(price);
    }

    async getPriceInput() {
        return this.priceInput.getAttribute('value');
    }

    async setFoodDescriptionInput(foodDescription) {
        await this.foodDescriptionInput.sendKeys(foodDescription);
    }

    async getFoodDescriptionInput() {
        return this.foodDescriptionInput.getAttribute('value');
    }

    async setCaloriesInput(calories) {
        await this.caloriesInput.sendKeys(calories);
    }

    async getCaloriesInput() {
        return this.caloriesInput.getAttribute('value');
    }

    getIsSpicyInput() {
        return this.isSpicyInput;
    }
    getIsVegetarianInput() {
        return this.isVegetarianInput;
    }
    getIsGlutenFreeInput() {
        return this.isGlutenFreeInput;
    }
    async setPhotoBlobInput(photoBlob) {
        await this.photoBlobInput.sendKeys(photoBlob);
    }

    async getPhotoBlobInput() {
        return this.photoBlobInput.getAttribute('value');
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

export class FoodDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-food-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-food'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
