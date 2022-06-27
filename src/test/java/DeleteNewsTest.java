import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class DeleteNewsTest {
    SoftAssertions softAssertions = new SoftAssertions();
    Routes routes = new Routes();
    ErrorCode errorCode = new ErrorCode();
    Methods methods = new Methods();
    JSONObject requestBody = new JSONObject();
    JSONObject requestBody1 = new JSONObject();
    JSONObject requestBody2 = new JSONObject();
    RequestSpecification request = given();
    UserData userData = new UserData();
    POJO pojo = new POJO();


    String description = "more";
    String image = "src/main/resources/avatar4.jpeg";
    ArrayList<String> tags = new ArrayList<>();
    String title = "more";

    String description1 = "more1";
    String image1 = "src/main/resources/avatar5.jpeg";
    ArrayList<String> tags1= new ArrayList<>();
    String title1 = "more1";



    @Epic("DELETE/v1/news/{id}")
    @Feature("Positive test")
    @Test
    public void deleteNewsTest() {
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

        requestBody2.put("description", description1);
        requestBody2.put("image", image1);
        requestBody2.put("title", title1);
        requestBody2.put("tags", tags1);

        request.header("Content-Type", "application/json");
        request.body(requestBody1.toString());


        response = request
                .when()
                .contentType(ContentType.JSON)
                .delete(routes.deleteNews + idUser)
                .then().log().all()
                .extract().response();


        int statusCode = response.getStatusCode();
        String success = response.jsonPath().getString("success");


        softAssertions.assertThat(200).isEqualTo(statusCode);
        softAssertions.assertThat("true").isEqualTo(success);


        softAssertions.assertAll();
    }
    @Epic("DELETE /v1/news/{id}")
    @Feature("Negative test")
    @Test
    public void deleteNewsNegativeTest() {
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



        pojo = response.jsonPath().getObject("data", POJO.class);

        requestBody2.put("description", description1);
        requestBody2.put("image", image1);
        requestBody2.put("title", title1);
        requestBody2.put("tags", tags1);

        request.header("Content-Type", "application/json");
        request.body(requestBody1.toString());


        response = request
                .when()
                .contentType(ContentType.JSON)
                .delete(routes.deleteNews)
                .then().log().all()
                .extract().response();


        int statusCode = response.getStatusCode();
        String success = response.jsonPath().getString("success");


        softAssertions.assertThat(401).isEqualTo(statusCode);
        softAssertions.assertThat("true").isEqualTo(success);


        softAssertions.assertAll();

    }
}

