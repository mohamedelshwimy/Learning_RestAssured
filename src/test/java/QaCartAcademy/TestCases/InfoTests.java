package QaCartAcademy.TestCases;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;

public class InfoTests {
    String url = "https://todo.qacart.com/";
    String infoEndPoint = "api/v1/info/";
    @Test
    public void getCoursesInfo(){
        Header typeHeader = new Header("type","WEB");
        Header langHeader = new Header("language","JAVA");
        Headers infoHeaders = new Headers(typeHeader,langHeader);

        HashMap<String,String> infoHeadersHashMap = new HashMap<>();
        infoHeadersHashMap.put("type","WEB");
        infoHeadersHashMap.put("language","JAVA");

        given()
                    .baseUri(url)
                    .headers(infoHeadersHashMap)
                    //.headers(infoHeaders)
                    //.header(typeHeader)
                    //.headers("language","JAVA","type","WEB")
                    //.header("language","JAVA")
                    //.header("type","NONE")
                    .log().all()
                .when()
                    .get(infoEndPoint+"courses")
                .then()
                    .log().all()
                .assertThat()
                    .statusCode(200)
                    .body("count",equalTo(1));
    }
    @Test
    public void getLecturesInfoUsingParameters(){
        HashMap<String,String> queryParameters = new HashMap<>();
        queryParameters.put("type","PAID");
        queryParameters.put("mode","VIDEO");

        given()
                .baseUri(url)
                .queryParams(queryParameters)
                //.queryParams("type","PAID","mode","VIDEO")
                //.queryParam("type","PAID")
                //.queryParam("mode","VIDEO")
                .log().parameters()
        .when()
                .get(infoEndPoint+"lectures")
        .then()
                .log().all()
                .assertThat().statusCode(200);
    }

}
