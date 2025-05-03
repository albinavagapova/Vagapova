package API_Tests.tests;

import API_Tests.ext.CreateCompanyResponse;
import API_Tests.helpers.ApiCompanyHelper;
import API_Tests.helpers.AuthHelper;
import configs.ConfigApi;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompanyTests extends ConfigApi {
    private CreateCompanyResponse createCompanyResponse;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = URL;
        apiCompanyHelper = new ApiCompanyHelper();
        authHelper = new AuthHelper();
        String userToken = authHelper.authAndGetToken("leonardo", "leads");
        RestAssured.requestSpecification = new RequestSpecBuilder().build().header("x-client-token", userToken);
    }

    // given
    // when
    // then
    @Test
    @DisplayName("Код ответа при получении списка компаний")
    public void getCompanyList() {
        given().log().all()  // ДАНО:
                .basePath("company")
                .when()
                .get()
                .then()
                .statusCode(200)
                .header("Content-Type", "application/json; charset=utf-8");
    }

    @Test
    @DisplayName("Создание компании")
    public void createCompany() {
        // Создание компании и сохранение ответа в переменной
        createCompanyResponse = apiCompanyHelper.createCompany();
        System.out.println("Создана компания с ID: " + createCompanyResponse.id());
    }


    @Test
    @DisplayName("Удаление компании")
    public void deleteCompany() {
        CreateCompanyResponse createCompanyResponse = apiCompanyHelper.createCompany();
        int deletedObjectId = apiCompanyHelper.deleteCompany(createCompanyResponse.id());
        assertEquals(createCompanyResponse.id(), deletedObjectId);
    }

    @Test
    @DisplayName("Получить компанию по id")
    public void getCompany() {
        int id = 688;
        given()  // ДАНО:
                .basePath("company")
                .when()     // КОГДА
                .get("{id}", id).prettyPrint(); // ГЕТ ЗАПРОС
    }

}
