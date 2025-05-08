package UI_Tests.tests;

import UI_Tests.FirstSetup;
import UI_Tests.pageobjects.Authorization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class Check_Error_MessageTests extends FirstSetup {

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-data-dir=/tmp/chrome-profile-" + System.nanoTime()); // Уникальный профиль
        driver = new ChromeDriver(options);
    }

    @Test
    public void testLockedOutUser() {
        Authorization authorization = new Authorization();

        // Авторизация заблокированным пользователем
        authorization.openPage();
        authorization.login("locked_out_user", "secret_sauce");

        // Проверить сообщение об ошибке
        $(".error-message-container").shouldHave(text("Epic sadface: Sorry, this user has been locked out."));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Корректное закрытие браузера после теста
        }
    }
}
