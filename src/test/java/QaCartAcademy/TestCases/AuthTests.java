package QaCartAcademy.TestCases;

import org.testng.annotations.Test;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
public class AuthTests {
    String url = "https://todo.qacart.com/";
    String infoEndPoint = "api/v1/courses/";
    Header authHeader = new Header("Authorization","");
    @Test
    public void getCourseDetails(){

        given()
                .baseUri(url)
                .header(authHeader).auth().oauth2("Token")
                .when()
                .get(infoEndPoint)
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }
}
