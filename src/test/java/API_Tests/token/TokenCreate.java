package API_Tests.token;

import io.restassured.RestAssured;

public class TokenCreate {

    private static String currentToken;

    public static String getFreshToken(String username, String password) {
        currentToken = RestAssured.given()
                .basePath("/auth")
                .contentType("application/json")
                .body("{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}")
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .path("token");
        return currentToken;
    }

    public static String getToken() {
        // Если токен не установлен или истёк, получить новый
        if (currentToken == null || isTokenExpired()) {
            currentToken = getFreshToken("leonardo", "leads"); // Передавайте актуальные креды
        }
        System.out.println("Текущий токен: " + currentToken); // Логируем токен перед возвратом
        return currentToken;
    }


    private static boolean isTokenExpired() {
        // Логика для проверки истечения токена (например, по времени или статусу запроса)
        return false; // Пока пример
    }
}