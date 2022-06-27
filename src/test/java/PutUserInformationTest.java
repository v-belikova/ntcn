import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class PutUserInformationTest {

        SoftAssertions softAssertions = new SoftAssertions();
        Routes routes = new Routes();
        ErrorCode errorCode = new ErrorCode();
        Methods methods = new Methods();
        JSONObject requestBody = new JSONObject();
        RequestSpecification request = RestAssured.given();
        UserData userDate = new UserData();
        //WrongLoginTest loginTest = new WrongLoginTest();

        String inputEmail = "mustyacy@mail.ru";
        String inputPass = "123456789";
        String inputName = "mustyacy";
        String inputAvatar = "src/main/resources/picture.jpeg";
        String inputRole = "Boss";

        @Story("PUT /v1/user")
        @Description("Positive test")
        @Test
        public void putUserTest() {
            requestBody.put("email", inputEmail);
            requestBody.put("avatar", inputAvatar);
            requestBody.put("name", inputName);
            requestBody.put("role", inputRole);
            requestBody.put("password", inputPass);


            request.header("Content-Type", "application/json");
            request.body(requestBody.toString());

            Response response = request.log().all().post(routes.postLogin).then().
                    contentType(ContentType.JSON).log().all().extract().response();

            String token = (String) response.jsonPath().getMap("data").get("token");
            token = token.replace("Bearer ", "");


            response = request.log().all().auth().oauth2(token).put(routes.updateUserInfo).then().
                    contentType(ContentType.JSON).log().all().extract().response();


            String avatar = (String)response.jsonPath().getMap("data").get("avatar");
            String email = (String)response.jsonPath().getMap("data").get("email");
            String id = (String)response.jsonPath().getMap("data").get("id");
            String name = (String)response.jsonPath().getMap("data").get("name");
            String role = (String)response.jsonPath().getMap("data").get("role");
            userDate = response.jsonPath().getObject("data",UserData.class);

            int statusCode = response.getStatusCode();
            String success = response.jsonPath().getString("success");
            int customStatusCode = response.jsonPath().getInt("statusCode");


            softAssertions.assertThat(name).isEqualTo(inputName);
            softAssertions.assertThat(role).isEqualTo(inputRole);
            softAssertions.assertThat(email).isEqualTo(inputEmail);
            softAssertions.assertThat(avatar).isEqualTo(inputAvatar);
            softAssertions.assertThat(id).isNotNull();
            softAssertions.assertThat(200).isEqualTo(statusCode);
            softAssertions.assertThat("true").isEqualTo(success);

            softAssertions.assertAll();
        }
        @Story("PUT /v1/user")
        @Description("Negative test")
        @Test
        public void putUserNegativeTest() {
            String inputEmail1 = "mustyacy@mail.ru";
            String inputPass1 = "123456789";
            String inputName1 = "mustyacy";
            String inputAvatar1 = "src/main/resources/picture.jpeg";
            String inputRole1 = "Boss";

            requestBody.put("email", inputEmail1);
            requestBody.put("avatar", inputAvatar1);
            requestBody.put("name", inputName1);
            requestBody.put("role", inputRole1);
            requestBody.put("password", inputPass1);


            request.header("Content-Type", "application/json");
            request.body(requestBody.toString());

            Response response =request.log().all().put(routes.deleteUser).then().
                    contentType(ContentType.JSON).log().all().extract().response();


            userDate = response.jsonPath().getObject("data",UserData.class);
            int statusCode = response.getStatusCode();
            String success = response.jsonPath().getString("success");
            int customStatusCode = response.jsonPath().getInt("statusCode");



            softAssertions.assertThat(401).isEqualTo(statusCode);
            softAssertions.assertThat("true").isEqualTo(success);
            softAssertions.assertThat(errorCode.UNAUTHORISED).isEqualTo(customStatusCode);


            softAssertions.assertAll();
        }

    }


