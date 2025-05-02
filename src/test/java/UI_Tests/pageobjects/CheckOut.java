package UI_Tests.pageobjects;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class CheckOut {

    // Заполнить форму для заказа
    public void fillOutForm(String firstName, String lastName, String postalCode) {
        $("#first-name").setValue(firstName);
        $("#last-name").setValue(lastName);
        $("#postal-code").setValue(postalCode);
        $("#continue").click();
    }

    // Проверить итоговую сумму
    public void verifyTotalCost(String expectedPrice) {
        $(".summary_total_label").shouldHave(text("Total: " + expectedPrice));
    }

    // Завершить заказ
    public void finishOrder() {
        $("#finish").click();
        $(".complete-header").shouldHave(text("THANK YOU FOR YOUR ORDER"));
    }
}
