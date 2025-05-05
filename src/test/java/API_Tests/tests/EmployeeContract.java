package API_Tests.tests;

import API_Tests.ext.CreateCompanyResponse;
import API_Tests.ext.CreateEmployeeResponse;
import API_Tests.helpers.ApiCompanyHelper;
import API_Tests.helpers.ApiEmployeeHelper;
import API_Tests.helpers.ApiToken;
import API_Tests.helpers.AuthHelper;
import configs.ConfigApi;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeContract extends ConfigApi {
    private static int companyId;


    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = URL;
        authHelper = new AuthHelper();
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType("application/json")
                .addHeader("x-client-token", ApiToken.getToken())
                .build();
    }

    //создать компанию
    @Test
    @DisplayName("Создание компании")
    public void createCompany() {
        CreateCompanyResponse createCompanyResponse = apiCompanyHelper.createCompany();
        companyId = createCompanyResponse.id(); // Сохраняем ID компании
        System.out.println("Создана компания с ID: " + companyId);
    }

    @Test
    @DisplayName("Получить компанию по id")
    public void getCompany() {
        assertNotNull(companyId, "ID компании не должен быть null"); // Проверка, что ID сохранён
        given().log().all()
                .basePath("company")
                .pathParam("id", companyId)
                .when()
                .get("/{id}") // ГЕТ запрос с использованием сохранённого ID
                .then()
                .log().all()
                .statusCode(200);
        System.out.println("Получена компания с ID: " + companyId);
    }

    //тесты сотрудников в новой компании
    //создание сотрудника
    @Test
    @DisplayName("Создание сотрудника")
    public void createEmployee() {
        CreateEmployeeResponse createEmployeeResponse = ApiEmployeeHelper.createEmployee();
        System.out.println(createEmployeeResponse.id());
    }
    //получение сотрудника

    @Test
    @DisplayName("Получить сотрудника по id")
    public void getEmployee() {
        int id = 774;
        given()  // ДАНО:
                .basePath("employee")
                .when()     // КОГДА
                .get("{id}", id).prettyPrint(); // ШЛЕШЬ ГЕТ ЗАПРОС
    }

    //получение сотрдуников по айди компании
    @Test
    @DisplayName("Получить список сотрудников по ID компании")
    public void getEmployeesByCompanyId() {
        int companyId = 120;
        Response response = given().log().all()
                .basePath("/employee")  // Базовый путь как в Swagger
                .queryParam("company", companyId)  // Используем query параметр "company"
                .when()
                .get()
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        System.out.println(response.prettyPrint());
    }

    //изменить данные сотрудника
    @Test
    @DisplayName("Изменить фамилию сотрудника по id")
    public void patchEmployee() {
        int id = 773;
        String newLastname = "Измененная";  // Указываем новую фамилию
        String requestBody = "{\n" +
                "  \"lastName\": \"" + newLastname + "\",\n" +
                "  \"email\": \"dfsdfds@mail.ru\",\n" +
                "  \"url\": \"sfsdfsf.mail.ru\",\n" +
                "  \"phone\": \"34535\",\n" +
                "  \"isActive\": true\n" +
                "}";
        given().log().all()
                .basePath("/employee")
                .pathParam("id", id)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .patch("/{id}")
                .then()
                .log().all()
                .statusCode(200);

        // Получение сотрдуника и проверка фамилии
        String newLastnameResponse = given().log().all()
                .basePath("/employee")
                .pathParam("id", id)
                .when()
                .get("/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .path("lastName");

        System.out.println("Updated Lastname: " + newLastnameResponse);
        assertEquals(newLastname, newLastnameResponse, "Фамилия сотрудника не изменилось.");
    }


    // Cоздание сотрудника с некорректными данными
    @Test
    @DisplayName("Создание сотрудника с некорректными данными")
    public void createEmployeeInvalidEmail() {
        String invalidRequestBody = "{\n" +
                "  \"firstName\": \"\",\n" +
                "  \"lastName\": \"\",\n" +
                "  \"email\": \"ddgsfgsgs\",\n" +  // Некорректный email
                "  \"phone\": \"\",\n" +
                "  \"birthdate\": \"465455\",\n" +
                "  \"isActive\": true\n" +
                "}";

        given().log().all()
                .basePath("/employee")
                .contentType("application/json")
                .body(invalidRequestBody)
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(400);  // Ожидаем статус 400 Bad Request
    }

    //статус 200, хотя по документации д.б.404
    @Test
    @DisplayName("Получить сотрудника по несуществующему id")
    public void getNullEmployee() {
        int id = 99999;
        given()
                .log().all()  // ДАНО:
                .basePath("/employee")
                .pathParam("id", id)
                .when()     // КОГДА
                .get("/{id}")
                .then()  // ПОСЛЕ
                .log().all()
                .statusCode(404);
    }


    //пустой массив в несуществующей компании
    @Test
    @DisplayName("Список сотрудников по несуществующей компании")
    public void getEmployeesByCompanyIdNull() {
        int companyIdNull = 000;  // Несуществующий ID компании

        Response response = given().log().all()
                .basePath("/employee")
                .queryParam("company", companyIdNull)
                .when()
                .get()
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();


        List<?> employees = response.jsonPath().getList("$");
        assertTrue(employees.isEmpty(), "Список сотрудников не пустой, хотя компании не существует.");


    }
}






