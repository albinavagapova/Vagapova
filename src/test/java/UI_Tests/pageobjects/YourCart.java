package UI_Tests.pageobjects;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class YourCart {

    // Добавить товар в корзину
    public void addProductToCart(String itemName) {
        $$(".inventory_item").findBy(text(itemName)).find(".btn_inventory").click();
    }

    // Проверить количество товаров в корзине
    public void verifyProductCount(int expectedCount) {
        $(".shopping_cart_badge").shouldHave(text(String.valueOf(expectedCount)));
    }

    // Перейти в корзину
    public void goToCart() {
        $(".shopping_cart_link").click();
    }

    // Нажать на кнопку "Checkout"
    public void goToCheckout() {
        $("#checkout").click();
    }
}