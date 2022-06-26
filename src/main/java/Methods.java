import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Random;

public class Methods {
    Routes routes = new Routes();
    RequestSpecification request = RestAssured.given();
    public static String generateRandomHexString (int length){
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < length){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, length);
    }

    public static void showBodyPostLogin(RequestSpecification request, Routes routes) {
        request.post(routes.postLogin).then().
                contentType(ContentType.JSON).extract().response().prettyPrint();
    }

    /*public String getAuthToken(String login, String password) {
        JSONObject requestParams = new JSONObject();
        requestParams.put("login", login);
        requestParams.put("password", password);

        Response LoginResponse = given()
                .body(requestParams.toJSONString())
                .when().contentType(ContentType.JSON)
                .post(routes.postLogin)
                .then()
                .extract().response();

        return LoginResponse.jsonPath().getString("token");
    }*/

}
