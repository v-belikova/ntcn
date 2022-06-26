import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class LoginUserTest {
    SoftAssertions softAssertions = new SoftAssertions();
    Routes routes = new Routes();
    Methods methods = new Methods();
    JSONObject requestBody = new JSONObject();
    RequestSpecification request = RestAssured.given();
    UserData userData = new UserData();
    ErrorCode errorCode = new ErrorCode();
    POJO pojo = new POJO();



    String wrongEmail1 = methods.generateRandomHexString(5);
    String password = methods.generateRandomHexString(6);

    @Epic("POST /v1/auth/login")
    @Feature("Positive test")
    @Test
    public void loginUserTest() {


        requestBody.put("email", userData.getLoginEmail());
        requestBody.put("password",userData.getPassword() );

        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());

        Response response = request
                .post(routes.postLogin)
                .then().log().all()
                .contentType(ContentType.JSON).log().all().extract().response();

        methods.showBodyPostLogin(request, routes);

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
    @Epic("POST /v1/auth/login")
    @Feature("Negative test incorrectEmail1")
    @Test
    public void incorrectEmail1() {


        requestBody.put("email", wrongEmail1);
        requestBody.put("password", password);

        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());

        Response response = request.post(routes.postLogin).then().
                contentType(ContentType.JSON).log().all().extract().response();

        methods.showBodyPostLogin(request, routes);

        int statusCode = response.getStatusCode();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        Integer codes = (Integer) response.jsonPath().getList("codes").get(0);

        softAssertions.assertThat(400).isEqualTo(statusCode);
        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(errorCode.USER_EMAIL_NOT_VALID).isEqualTo(customStatusCode);
        softAssertions.assertThat(errorCode.USER_EMAIL_NOT_VALID).isEqualTo(codes);

        softAssertions.assertAll();
    }
}

