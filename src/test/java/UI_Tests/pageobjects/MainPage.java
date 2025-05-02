package UI_Tests.pageobjects;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    // Проверить, что главная страница открыта по заголовку
    public static void verifyPageTitle() {
        $(".title").shouldHave(text("Products"));
    }
}