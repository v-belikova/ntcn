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


        requestBody.put("email", email);
        requestBody.put("password",userData.getPassword() );

        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());

        response = request
                .post(routes.postLogin)
                .then().log().all()
                .contentType(ContentType.JSON).log().all().extract().response();

        methods.showBodyPostLogin(request, routes);

        String email1 = (String) response.jsonPath().getMap("data").get("email");
        String name = (String) response.jsonPath().getMap("data").get("name");
        String avatar = (String) response.jsonPath().getMap("data").get("avatar");
        String role = (String) response.jsonPath().getMap("data").get("role");
        String idUser = (String) response.jsonPath().getMap("data").get("id");
        String tokenUser = (String) response.jsonPath().getMap("data").get("token");
        pojo = response.jsonPath().getObject("data", POJO.class);

        int statusCode = response.getStatusCode();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");

        softAssertions.assertThat(email1).isEqualTo(pojo.getEmail());
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

        String avatar1 = "src/main/resources/avatar1.jpeg";
        String email1 = methods.generateRandomHexString(5) + "@mail.ru";
        String name1 = "hello";
        String password1 = "Lokon";
        String role1 = "user";

        requestBody.put("avatar", avatar1);
        requestBody.put("email", email1);
        requestBody.put("name", name1);
        requestBody.put("password", password1);
        requestBody.put("role", role1);
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());

        Response response = request.log().all()
                .post(routes.postRegistration)
                .then()
                .contentType(ContentType.JSON).log().all().extract().response();


        String email = (String) response.jsonPath().getMap("data").get("email");


        requestBody.put("email", email);
        requestBody.put("password", password);

        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());

        response = request.post(routes.postLogin).then().
                contentType(ContentType.JSON).log().all().extract().response();

        methods.showBodyPostLogin(request, routes);

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

