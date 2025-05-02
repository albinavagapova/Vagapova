package API_Tests.helpers;

import API_Tests.ext.AuthRequest;
import API_Tests.ext.AuthResponse;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class AuthHelper {
    public String authAndGetToken(String username, String password) {
        AuthRequest authRequest = new AuthRequest(username, password);
        AuthResponse authResponse = given()
                .basePath("auth/login")
                .body(authRequest)
                .contentType(ContentType.JSON)
                .when()
                .post()
                .as(AuthResponse.class);

        return authResponse.userToken();
    }
}
