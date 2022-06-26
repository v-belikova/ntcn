import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class GetAllNewsTest {
    SoftAssertions softAssertions = new SoftAssertions();
    Methods methods = new Methods();
    ErrorCode errorCode = new ErrorCode();
    String URL = "https://news-feed.dunice-testing.com/api/v1/news";
    Routes routes = new Routes();

    @Epic("GET /v1/news")
    @Feature("Positive test")
    @Test
    public void allNewsPageGetTest() {

        int newsCount = given()
                .contentType(ContentType.JSON)
                .queryParam("page","1")
                .queryParam("perPage", "1")
                .when()
                .get(URL)
                .then().log().body()
                .extract().response().path("data.numberOfElements");
        int iter = (int) Math.ceil(newsCount / 100.0);
        int NewsAmount = 0;
        while (iter > 0) {
            List<NewsId> nArr = given()
                    .contentType(ContentType.JSON)
                    .queryParam("page", iter)
                    .queryParam("perPage", "100")
                    .when()
                    .get(URL)
                    .then()
                    .extract().jsonPath().getList("data.content", NewsId.class);
            NewsAmount += nArr.size();
            iter--;
        }
        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("page", "1")
                .queryParam("perPage", "100")
                .when()
                .get(URL)
                .then()
                .extract().response();
        int statusCode = response.getStatusCode();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        softAssertions.assertThat(200).isEqualTo(statusCode);
        softAssertions.assertThat(errorCode.USERNAME_SIZE_NOT_VALID).isEqualTo(customStatusCode);
        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(newsCount).isEqualTo(NewsAmount);
        softAssertions.assertAll();
    }
}
