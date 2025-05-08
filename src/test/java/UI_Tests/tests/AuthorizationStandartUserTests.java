package UI_Tests.tests;

import UI_Tests.FirstSetup;
import UI_Tests.pageobjects.Authorization;
import UI_Tests.pageobjects.MainPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;

public class AuthorizationStandartUserTests extends FirstSetup {

    @Test
    public void testSuccessfulAuthorization() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-data-dir=/tmp/chrome-profile-" + System.nanoTime());

        Authorization loginPage = new Authorization();

        // Открыть сайт и авторизоваться
        loginPage.openPage();
        loginPage.login("standard_user", "secret_sauce");

        // Проверить, что страница открыта и есть заголовок "Products"
        MainPage.verifyPageTitle();
    }

    @AfterEach
    public void tearDown() {
        driver.quit(); // Закрытие браузера после теста
    }
}
