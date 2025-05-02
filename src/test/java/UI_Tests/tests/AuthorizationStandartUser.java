package UI_Tests.tests;

import UI_Tests.FirstSetup;
import UI_Tests.pageobjects.Authorization;
import UI_Tests.pageobjects.MainPage;
import UI_Tests.pageobjects.YourCart;
import org.junit.jupiter.api.Test;


public class AuthorizationStandartUser extends FirstSetup {

    @Test
    public void testSuccessfulAuthorization() {
        Authorization loginPage = new Authorization();

        // Открыть сайт и авторизоваться
        loginPage.openPage();
        loginPage.login("standard_user", "secret_sauce");

        // Проверить, что страница открыта и есть заголовок "Products"
        MainPage.verifyPageTitle();
    }
}
