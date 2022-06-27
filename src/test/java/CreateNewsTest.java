import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class CreateNewsTest {
    SoftAssertions softAssertions = new SoftAssertions();
    Routes routes = new Routes();
    ErrorCode errorCode = new ErrorCode();
    Methods methods = new Methods();
    JSONObject requestBody = new JSONObject();
    JSONObject requestBody1 = new JSONObject();
    RequestSpecification request = given();
    UserData userData = new UserData();
    POJO pojo = new POJO();


    String description = "more";
    String image = "src/main/resources/avatar4.jpeg";
    ArrayList<String> tags = new ArrayList<>();
    String title = "more";


    @Epic("POST /v1/news")
    @Feature("Positive test")
    @Test
    public void createNewsTest() {
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


        requestBody1.put("description", description);
        requestBody1.put("image", image);
        requestBody1.put("title", title);
        requestBody1.put("tags", tags);

        request.header("Content-Type", "application/json");
        request.body(requestBody1.toString());


        response = request.log().all().
                auth().oauth2(token).
                post(routes.createNews).
                then().
                contentType(ContentType.JSON).log().all().extract().response();


        int idUser = response.jsonPath().get("id");
        pojo = response.jsonPath().getObject("data", POJO.class);

        int statusCode = response.getStatusCode();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");

        softAssertions.assertThat(idUser).isNotNull();
        softAssertions.assertThat(200).isEqualTo(statusCode);
        softAssertions.assertThat("true").isEqualTo(success);


        softAssertions.assertAll();
    }

    @Epic("POST /v1/news")
    @Feature("Negative test")
    @Test
    public void createNewsNegativeTest() {
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

        String token = (String) response.jsonPath().getMap("data").get("token");
        token = token.replace("Bearer ", "");


        requestBody1.put("description", description);
        requestBody1.put("image", image);
        requestBody1.put("title", title);
        requestBody1.put("tags", tags);

        request.header("Content-Type", "application/json");
        request.body(requestBody1.toString());


        response = request.log().all().
                post(routes.createNews).
                then().
                contentType(ContentType.JSON).log().all().extract().response();


        //int idUser = response.jsonPath().get("id");
        pojo = response.jsonPath().getObject("data", POJO.class);

        int statusCode = response.getStatusCode();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");

        //softAssertions.assertThat(idUser).isNotNull();
        softAssertions.assertThat(401).isEqualTo(statusCode);
        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(errorCode.UNAUTHORISED).isEqualTo(customStatusCode);

        softAssertions.assertAll();


    }
}






