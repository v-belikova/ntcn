import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class GetInfoUserTest {
        SoftAssertions softAssertions = new SoftAssertions();
        Routes routes = new Routes();
        ErrorCode errorCode = new ErrorCode();
        Methods methods = new Methods();
        JSONObject requestBody = new JSONObject();
        RequestSpecification request = given();
        UserData userData = new UserData();


        @Epic("GET /v1/user/{id}")
        @Feature("Positive test")
        @Test
        public void GetUserInfoById() {
            Response response = request
                    .when()
                    .contentType(ContentType.JSON)
                    .get(routes.getUserInfoId + userData.getId())
                    .then().log().all()
                    .extract().response();


            methods.showBodyPostLogin(request, routes);
            String email = (String) response.jsonPath().getMap("data").get("email");
            String name = (String) response.jsonPath().getMap("data").get("name");
            String avatar = (String) response.jsonPath().getMap("data").get("avatar");
            String role = (String) response.jsonPath().getMap("data").get("role");
            String idUser = (String) response.jsonPath().getMap("data").get("id");
            userData = response.jsonPath().getObject("data", UserData.class);

            int statusCode = response.getStatusCode();
            String success = response.jsonPath().getString("success");
            int customStatusCode = response.jsonPath().getInt("statusCode");
            String user = response.jsonPath().getString("data");

            softAssertions.assertThat(email).isEqualTo(userData.getEmail());
            softAssertions.assertThat(name).isEqualTo(userData.getName());
            softAssertions.assertThat(avatar).isEqualTo(userData.getAvatar());
            softAssertions.assertThat(role).isEqualTo(userData.getRole());
            softAssertions.assertThat(idUser).isNotNull();
            softAssertions.assertThat(200).isEqualTo(statusCode);
            softAssertions.assertThat("true").isEqualTo(success);
            softAssertions.assertThat(errorCode.USERNAME_SIZE_NOT_VALID).isEqualTo(customStatusCode);

            softAssertions.assertAll();

        }
        @Epic("GET /v1/user/{id}")
        @Feature("Positive test")
        @Test
        public void GetUserInfoByIdNegative() {
            Response response = request
                    .when()
                    .contentType(ContentType.JSON)
                    .get(routes.getUserInfoId)
                    .then().log().all()
                    .extract().response();

            int statusCode = response.getStatusCode();
            String success = response.jsonPath().getString("success");
            int customStatusCode = response.jsonPath().getInt("statusCode");


            softAssertions.assertThat(401).isEqualTo(statusCode);
            softAssertions.assertThat("true").isEqualTo(success);
            softAssertions.assertThat(errorCode.UNAUTHORISED).isEqualTo(customStatusCode);

            softAssertions.assertAll();

        }

    }


