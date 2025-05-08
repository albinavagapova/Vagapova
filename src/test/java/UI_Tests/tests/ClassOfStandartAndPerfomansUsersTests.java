package UI_Tests.tests;

import UI_Tests.FirstSetup;
import UI_Tests.pageobjects.Authorization;
import UI_Tests.pageobjects.CheckOut;
import UI_Tests.pageobjects.YourCart;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.AfterEach;
import com.codeborne.selenide.Configuration;
import static com.codeborne.selenide.Selenide.closeWebDriver;

public class ClassOfStandartAndPerfomansUsersTests extends FirstSetup {

    @ParameterizedTest
    @CsvSource({
            "performance_glitch_user, secret_sauce, 30000",
            "standard_user, secret_sauce, 4000"
    })
    public void testCartUsers(String username, String password, int timeout) {
        // таймут для двух пользователей
        Configuration.timeout = timeout;

        Authorization loginPage = new Authorization();
        YourCart cartPage = new YourCart();
        CheckOut checkoutPage = new CheckOut();

        loginPage.openPage();
        loginPage.login(username, password);

        cartPage.addProductToCart("Sauce Labs Backpack");
        cartPage.addProductToCart("Sauce Labs Bolt T-Shirt");
        cartPage.addProductToCart("Sauce Labs Onesie");
        cartPage.verifyProductCount(3);

        cartPage.goToCart();
        cartPage.goToCheckout();
        checkoutPage.fillOutForm("Albina", "Va", "123456");
        checkoutPage.verifyTotalCost("$58.29");
        checkoutPage.finishOrder();
    }

    @AfterEach
    public void tearDown() {
        closeWebDriver(); // Закрыть браузер после теста
    }
}