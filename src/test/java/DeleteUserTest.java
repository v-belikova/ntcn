import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeleteUserTest {

    SoftAssertions softAssertions = new SoftAssertions();
    Routes routes = new Routes();
    ErrorCode errorCode = new ErrorCode();
    JSONObject requestBody = new JSONObject();
    RequestSpecification request = given();
    UserData userData = new UserData();


    @Epic("DELETE /v1/user")
    @Feature("Positive test")
    @Test
    public void deleteUserTest() {

        requestBody.put("avatar", userData.getAvatar());
        requestBody.put("email", userData.getEmail());
        requestBody.put("name", userData.getName());
        requestBody.put("password", userData.getPassword());
        requestBody.put("role", userData.getRole());


        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());

        Response response = request.log().all()
                .post(routes.postRegistration)
                .then()
                .contentType(ContentType.JSON).log().all().extract().response();

        String token = (String) response.jsonPath().getMap("data").get("token");
        token = token.replace("Bearer ", "");

        response = request
                .auth().oauth2(token)
                .delete(routes.deleteUser).
                then()
                .contentType(ContentType.JSON)
                .extract().response();


        int statusCode = response.getStatusCode();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");


        softAssertions.assertThat(200).isEqualTo(statusCode);
        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(errorCode.USERNAME_SIZE_NOT_VALID).isEqualTo(customStatusCode);

        softAssertions.assertAll();
    }
    @Epic("DELETE /v1/user")
    @Feature("Negative test")
    @Test
    public void deleteUserNegativeTest() {

        requestBody.put("avatar", userData.getAvatar());
        requestBody.put("email", userData.getEmail());
        requestBody.put("name", userData.getName());
        requestBody.put("password", userData.getPassword());
        requestBody.put("role", userData.getRole());

        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());

        Response response = request.log().all()
                .post(routes.postRegistration)
                .then()
                .contentType(ContentType.JSON).log().all().extract().response();

        String token = (String) response.jsonPath().getMap("data").get("token");
        token = token.replace("Bearer ", "");

        response = request.delete(routes.deleteUser).
                then().contentType(ContentType.JSON).extract().response();


        int statusCode = response.getStatusCode();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");


        softAssertions.assertThat(401).isEqualTo(statusCode);
        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(errorCode.UNAUTHORISED).isEqualTo(customStatusCode);

        softAssertions.assertAll();
    }
}

