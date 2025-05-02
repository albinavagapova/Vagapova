package UI_Tests.tests;

import UI_Tests.FirstSetup;
import UI_Tests.pageobjects.Authorization;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class Check_Error_Message extends FirstSetup {

    @Test
    public void testLockedOutUser() {
        Authorization authorization = new Authorization();

        // Авторизация заблокированным пользователем
        authorization.openPage();
        authorization.login("locked_out_user", "secret_sauce");

        // Проверить сообщение об ошибке
        $(".error-message-container").shouldHave(text("Epic sadface: Sorry, this user has been locked out."));
    }
}
