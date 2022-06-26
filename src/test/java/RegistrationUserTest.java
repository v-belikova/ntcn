import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RegistrationUserTest {
    SoftAssertions softAssertions = new SoftAssertions();
    Routes routes = new Routes();
    ErrorCode errorCode = new ErrorCode();
    Methods methods = new Methods();
    JSONObject requestBody = new JSONObject();
    RequestSpecification request = given();
    UserData userData = new UserData();
    POJO pojo = new POJO();


    String wrongEmail1 = methods.generateRandomHexString(6);
    String correctEmail = methods.generateRandomHexString(3) + "@email.com";

    String password = methods.generateRandomHexString(6);
    String emptyPassword = "";

    String correctName = methods.generateRandomHexString(6);

    String correctRole = "user";

    String correctAvatar = "src/main/resources/avatar1.png";


    @Epic("POST /v1/auth/register")
    @Feature("Positive test")
    @Test
    public void correctReg() {

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


        String email = (String) response.jsonPath().getMap("data").get("email");
        String name = (String) response.jsonPath().getMap("data").get("name");
        String avatar = (String) response.jsonPath().getMap("data").get("avatar");
        String role = (String) response.jsonPath().getMap("data").get("role");
        String idUser = (String) response.jsonPath().getMap("data").get("id");
        String tokenUser = (String) response.jsonPath().getMap("data").get("token");
        pojo = response.jsonPath().getObject("data", POJO.class);

        int statusCode = response.getStatusCode();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");

        softAssertions.assertThat(email).isEqualTo(pojo.getEmail());
        softAssertions.assertThat(name).isEqualTo(pojo.getName());
        softAssertions.assertThat(avatar).isEqualTo(pojo.getAvatar());
        softAssertions.assertThat(role).isEqualTo(pojo.getRole());
        softAssertions.assertThat(idUser).isNotNull();
        softAssertions.assertThat(tokenUser).isNotNull();
        softAssertions.assertThat(200).isEqualTo(statusCode);
        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(errorCode.USERNAME_SIZE_NOT_VALID).isEqualTo(customStatusCode);

        softAssertions.assertAll();
    }

    @Epic("POST /v1/auth/register")
    @Feature("Negative test")
    @Test
    public void incorrectEmail1() {
        requestBody.put("email", wrongEmail1);
        requestBody.put("password", password);
        requestBody.put("name", correctName);
        requestBody.put("role", correctRole);
        requestBody.put("avatar", correctAvatar);

        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());

        Response response = request.log().all().post(routes.postRegistration).then().
                contentType(ContentType.JSON).log().all().extract().response();

        methods.showBodyPostLogin(request, routes);//метод печатать

        int statusCode = response.getStatusCode(); //переменная статус кода
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        Integer codes = (Integer) response.jsonPath().getList("codes").get(0);

        softAssertions.assertThat(400).isEqualTo(statusCode);
        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(errorCode.USER_EMAIL_NOT_VALID).isEqualTo(customStatusCode);
        softAssertions.assertThat(errorCode.USER_EMAIL_NOT_VALID).isEqualTo(codes);

        softAssertions.assertAll();
    }
    @Epic("POST /v1/auth/register")
    @Feature("Negative test incorrectPassword")
    @Test
    public void incorrectPassword() {
        requestBody.put("email", correctEmail);
        requestBody.put("password", emptyPassword);
        requestBody.put("name", correctName);
        requestBody.put("role", correctRole);
        requestBody.put("avatar", correctAvatar);

        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());

        Response response = request.post(routes.postLogin).then().
                contentType(ContentType.JSON).extract().response();

        Methods.showBodyPostLogin(request, routes);

        int statusCode = response.getStatusCode();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        Integer codes = (Integer) response.jsonPath().getList("codes").get(0);

        softAssertions.assertThat(400).isEqualTo(statusCode);
        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(errorCode.PASSWORD_NOT_VALID).isEqualTo(customStatusCode);
        softAssertions.assertThat(errorCode.PASSWORD_NOT_VALID).isEqualTo(codes);

        softAssertions.assertAll();
    }

}
