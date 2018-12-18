import { element, by, ElementFinder } from 'protractor';

export class FoodOrderComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-food-order div table .btn-danger'));
    title = element.all(by.css('jhi-food-order div h2#page-heading span')).first();

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

export class FoodOrderUpdatePage {
    pageTitle = element(by.id('jhi-food-order-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    timeRatingInput = element(by.id('field_timeRating'));
    priceRatingInput = element(by.id('field_priceRating'));
    qualityRatingInput = element(by.id('field_qualityRating'));
    loyaltyPointsInput = element(by.id('field_loyaltyPoints'));
    addressRatingInput = element(by.id('field_addressRating'));
    dateInput = element(by.id('field_date'));
    priceInput = element(by.id('field_price'));
    statusSelect = element(by.id('field_status'));
    purchaserOpinionInput = element(by.id('field_purchaserOpinion'));
    purchaserCommentInput = element(by.id('field_purchaserComment'));
    cityInput = element(by.id('field_city'));
    phoneInput = element(by.id('field_phone'));
    addressInput = element(by.id('field_address'));
    restaurantSelect = element(by.id('field_restaurant'));
    deliverymanSelect = element(by.id('field_deliveryman'));
    purchaserSelect = element(by.id('field_purchaser'));
    foodItemsSelect = element(by.id('field_foodItems'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setTimeRatingInput(timeRating) {
        await this.timeRatingInput.sendKeys(timeRating);
    }

    async getTimeRatingInput() {
        return this.timeRatingInput.getAttribute('value');
    }

    async setPriceRatingInput(priceRating) {
        await this.priceRatingInput.sendKeys(priceRating);
    }

    async getPriceRatingInput() {
        return this.priceRatingInput.getAttribute('value');
    }

    async setQualityRatingInput(qualityRating) {
        await this.qualityRatingInput.sendKeys(qualityRating);
    }

    async getQualityRatingInput() {
        return this.qualityRatingInput.getAttribute('value');
    }

    async setLoyaltyPointsInput(loyaltyPoints) {
        await this.loyaltyPointsInput.sendKeys(loyaltyPoints);
    }

    async getLoyaltyPointsInput() {
        return this.loyaltyPointsInput.getAttribute('value');
    }

    async setAddressRatingInput(addressRating) {
        await this.addressRatingInput.sendKeys(addressRating);
    }

    async getAddressRatingInput() {
        return this.addressRatingInput.getAttribute('value');
    }

    async setDateInput(date) {
        await this.dateInput.sendKeys(date);
    }

    async getDateInput() {
        return this.dateInput.getAttribute('value');
    }

    async setPriceInput(price) {
        await this.priceInput.sendKeys(price);
    }

    async getPriceInput() {
        return this.priceInput.getAttribute('value');
    }

    async setStatusSelect(status) {
        await this.statusSelect.sendKeys(status);
    }

    async getStatusSelect() {
        return this.statusSelect.element(by.css('option:checked')).getText();
    }

    async statusSelectLastOption() {
        await this.statusSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setPurchaserOpinionInput(purchaserOpinion) {
        await this.purchaserOpinionInput.sendKeys(purchaserOpinion);
    }

    async getPurchaserOpinionInput() {
        return this.purchaserOpinionInput.getAttribute('value');
    }

    async setPurchaserCommentInput(purchaserComment) {
        await this.purchaserCommentInput.sendKeys(purchaserComment);
    }

    async getPurchaserCommentInput() {
        return this.purchaserCommentInput.getAttribute('value');
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

    async setAddressInput(address) {
        await this.addressInput.sendKeys(address);
    }

    async getAddressInput() {
        return this.addressInput.getAttribute('value');
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

    async deliverymanSelectLastOption() {
        await this.deliverymanSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async deliverymanSelectOption(option) {
        await this.deliverymanSelect.sendKeys(option);
    }

    getDeliverymanSelect(): ElementFinder {
        return this.deliverymanSelect;
    }

    async getDeliverymanSelectedOption() {
        return this.deliverymanSelect.element(by.css('option:checked')).getText();
    }

    async purchaserSelectLastOption() {
        await this.purchaserSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async purchaserSelectOption(option) {
        await this.purchaserSelect.sendKeys(option);
    }

    getPurchaserSelect(): ElementFinder {
        return this.purchaserSelect;
    }

    async getPurchaserSelectedOption() {
        return this.purchaserSelect.element(by.css('option:checked')).getText();
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

export class FoodOrderDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-foodOrder-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-foodOrder'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
