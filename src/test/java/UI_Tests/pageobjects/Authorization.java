package UI_Tests.pageobjects;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class Authorization {
    // Метод для открытия сайта
    public void openPage() {
        open("https://www.saucedemo.com");
    }

    // Метод для логина и пароля
    public void login(String username, String password) {
        $("#user-name").setValue(username);
        $("#password").setValue(password);
        $("#login-button").click();
    }
}

