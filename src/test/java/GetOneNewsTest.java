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

public class GetOneNewsTest {

    SoftAssertions softAssertions = new SoftAssertions();
    Routes routes = new Routes();
    ErrorCode errorCode = new ErrorCode();
    Methods methods = new Methods();
    JSONObject requestBody = new JSONObject();
    RequestSpecification request = RestAssured.given();
    Content content = new Content();


    @Epic("Valid GET /v1/news/find")
    @Feature("Positive test")
    @Test
    public void GetOneNews(){


        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());

        Response response = request.log().all()
                .queryParam("page", "1")
                .queryParam("perPage", "1")
                .queryParam("tags", "")
                .get( routes.getOneNews)
                .then()
                .contentType(ContentType.JSON).log().all().extract().response();


        Headers allHeaders = response.getHeaders();
        Integer id = (Integer) response.jsonPath().getList("id").get(0);
        String title = (String) response.jsonPath().getMap("content").get("title");
        String description = (String) response.jsonPath().getMap("content").get("description");
        String image = (String) response.jsonPath().getMap("content").get("image");
        String username = (String) response.jsonPath().getMap("content").get("username");
        Integer numberOfElements = (Integer) response.jsonPath().getList("content").get(0);
        Integer idTag = (Integer) response.jsonPath().getMap("data/content/tags").get("id");
        String titleTag = (String) response.jsonPath().getMap("data/content/tags").get("title");
        content = response.jsonPath().getObject("content", Content.class);

        int statusCode = response.getStatusCode();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        Integer codes = (Integer) response.jsonPath().getList("codes").get(0);


        softAssertions.assertThat(id).isEqualTo(content.getId());
        softAssertions.assertThat(title).isEqualTo(content.getTitle());
        softAssertions.assertThat(description).isEqualTo(content.getDescription());
        softAssertions.assertThat(image).isEqualTo(content.getImage());
        softAssertions.assertThat(username).isEqualTo(content.getUsername());
        softAssertions.assertThat(numberOfElements).isEqualTo(content.getNumberOfElements());
        softAssertions.assertThat(200).isEqualTo(statusCode);
        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(errorCode.USERNAME_SIZE_NOT_VALID).isEqualTo(customStatusCode);
        softAssertions.assertThat(errorCode.USER_EMAIL_NOT_VALID).isEqualTo(codes);

        softAssertions.assertAll();













    }
}
