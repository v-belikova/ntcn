import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class GetOneNewsTest {

    SoftAssertions softAssertions = new SoftAssertions();
    Routes routes = new Routes();
    JSONObject requestBody = new JSONObject();
    JSONObject requestBody1 = new JSONObject();
    RequestSpecification request = RestAssured.given();


    @Epic("GET /v1/news/find")
    @Feature("Positive test")
    @Test
    public void GetOneNews() {


        request.header("Content-Type", "application/json");
        request.body(requestBody1.toString());

        Response response = request.log().all()
                .get(routes.getOneNews)
                .then()
                .contentType(ContentType.JSON).log().all().extract().response();


        Headers allHeaders = response.getHeaders();
        Integer id = (Integer) response.jsonPath().getList("content.id").get(0);
        String title = (String) response.jsonPath().getList("content.title").get(0);
        String description = (String) response.jsonPath().getList("content.description").get(0);
        String image = (String) response.jsonPath().getList("content.image").get(0);
        String username = (String) response.jsonPath().getList("content.username").get(0);


        int statusCode = response.getStatusCode();
        String success = response.jsonPath().getString("success");

        softAssertions.assertThat(allHeaders).isNotNull();
        softAssertions.assertThat(id).isNotNull();
        softAssertions.assertThat(title).isNotNull();
        softAssertions.assertThat(description).isNotNull();
        softAssertions.assertThat(image).isNotNull();
        softAssertions.assertThat(username).isNotNull();
        softAssertions.assertThat(200).isEqualTo(statusCode);

        softAssertions.assertAll();

    }
    @Epic("GET /v1/news/find")
    @Feature("Negative test")
    @Test
    public void GetOneNegativeNotValidURLNews() {


        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());

        Response response = request.log().all()
                .queryParam("page", "1")
                .queryParam("perPage", "1")
                .queryParam("tags", "")
                .get(routes.getOneNews1)
                .then()
                .contentType(ContentType.JSON).log().all().extract().response();


        int statusCode = response.getStatusCode();

        softAssertions.assertThat(404).isEqualTo(statusCode);

        softAssertions.assertAll();
    }





}