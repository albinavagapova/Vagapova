package API_Tests.helpers;

import API_Tests.ext.Company;
import API_Tests.ext.CreateCompanyRequest;
import API_Tests.ext.CreateCompanyResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.Optional;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;


public class ApiCompanyHelper  {

    private AuthHelper authHelper;

    public ApiCompanyHelper() {authHelper = new AuthHelper();
    }

    public CreateCompanyResponse createCompany() {
        CreateCompanyRequest createCompanyRequest = new CreateCompanyRequest("ООО Компания", "Тестовая компания");

        return given()  // ДАНО:
                .basePath("company")
                .body(createCompanyRequest)
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("id", is(greaterThan(0)))
                .extract().as(CreateCompanyResponse.class);
    }


    public int deleteCompany(int id) {

        return given()
                .basePath("company/delete")
                .when()
                .get("{id}", id)
                .jsonPath().getInt("id");
    }

    public Optional<Company> getCompany(int id) {

        Response response =
                given()
                        .basePath("company")
                        .when()
                        .get("{id}", id);

        String header = response.header("Content-Length");
        if (header != null && header.equals("0")) {
            return Optional.empty();
        }

        return Optional.of(response.as(Company.class));
    }

}