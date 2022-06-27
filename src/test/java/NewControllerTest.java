import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.File;

public class NewControllerTest {
    Routes routes = new Routes();
    SoftAssertions softAssertions = new SoftAssertions();
    ErrorCode errorCode = new ErrorCode();
    Methods methods = new Methods();
    JSONObject requestBody = new JSONObject();
    RequestSpecification request = RestAssured.given();
    UserData userDate = new UserData();


    String fileName;
    String fileName1;

    @Epic("POST /v1/file/uploadFile")
    @Feature("Positive test")
    @Test
    public void FilePostTest() {


        request.log().all().header("Content-Type", "multipart/form-data")
                .multiPart(new File("src/main/resources/avatar4.jpeg"));


        Response response = request.log().all()
                .when().post(routes.fileControl)
                .then().contentType(ContentType.JSON).log().all().
                extract().response();

        fileName = response.jsonPath().getString("data");
        int statusCode = response.getStatusCode();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");

        softAssertions.assertThat(200).isEqualTo(statusCode);
        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(errorCode.USERNAME_SIZE_NOT_VALID).isEqualTo(customStatusCode);


        softAssertions.assertAll();

    }
    @Epic("POST /v1/file/uploadFile")
    @Feature("Negative test")
    @Test
    public void FilePostNegativeTest() {

        request.header("Content-Type", "multipart/form-data")
                .multiPart(new File("src/main/resources/avatar4.jpeg"));


        request.body(requestBody.toString());

        Response response = request.log().all()
                .when()
                .post(routes.updateNews)
                .then().contentType(ContentType.JSON).log().all().
                extract().response();

        fileName = response.jsonPath().getString("data");
        int statusCode = response.getStatusCode();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");

        softAssertions.assertThat(401).isEqualTo(statusCode);
        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(errorCode.UNAUTHORISED).isEqualTo(customStatusCode);


        softAssertions.assertAll();

    }
   /* @Epic("GET /v1/file/{fileName}")
    @Feature("Positive test")
    @Test
    public void FileGetTest() {

        request.log().all().header("Content-Type", "multipart/form-data")
                .multiPart(new File("src/main/resources/avatar5.jpeg"));

        //request.body(requestBody.toString());

        Response response = request.log().all().when()
                .post(routes.fileControl).then()
                .contentType(ContentType.JSON).log().all().
                extract().response();

        fileName1 = response.jsonPath().getString("data");

        response = request
                .when()
                .get(routes.getFileControl + fileName1).then()
                .contentType(ContentType.HTML).log().all()
                .extract().response();


    }

   /* @Epic("GET /v1/file/{fileName}")
    @Feature("Negative test")
    @Test
    public void FileGetNegativeTest() {

        Response response = request
                .when()
                .get(routes.getFileControl + fileName).then()
                .contentType(ContentType.JSON).log().all()
                .extract().response();
    }*/

}
