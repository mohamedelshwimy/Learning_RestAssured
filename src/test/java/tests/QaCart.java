package tests;


import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class QaCart {
    String url = "https://655090e17d203ab6626dedcc.mockapi.io";
    String endpoint = "/api/v1/users";
    @Test
    public void testGetRequest(){
                given().baseUri(url)
                        .when().get(endpoint)
                        .then().log().all()
                        .assertThat().statusCode(200)
                        .assertThat().contentType(ContentType.JSON)
                        .assertThat().body("[0].name",is(equalTo("Emanuel Boyer")),  //use comma to make separate assertions in the same body
                                "name",hasItems("Ryan Jerde","Dominic Kuvalis"),
                                "name",not(hasItems("Mohamed","ahmed")))
                        .assertThat().body("name",hasSize(25))
                        .assertThat().body("name.size()",equalTo(25));
    }
    @Test
    public void responseExtractTest(){
        Response response = given().baseUri(url)
                            .when().get(endpoint)
                            .then().extract().response();
        System.out.println(response.asString());

        //User Response.path
        //Can also extract using then().extract().response().path("[0].name")
        String name = response.path("[0].name");
        System.out.println(name);

        //Use JsonPath class
        String country = JsonPath.from(response.asString()).getString("[0].country");
        System.out.println(country);
        //same way but using an obj from JsonPath class
        JsonPath jsonPath = new JsonPath(response.asString());
        String id = jsonPath.getString("[0].id");
        System.out.println(id);
    }
    @Test
    public void testLogging(){
        //logging given()
        given().baseUri(url).log().method()
                .when().get(endpoint)
                .then();

        //Logging then()
        given().baseUri(url)
                .when().get(endpoint)
                .then().log().status();

        //Logging ifError
        given().baseUri(url)
                .when().get(endpoint)
                .then().log().ifError();

        //Logging ifValidationFails assertion failure
        //can be used in request or response
        given().baseUri(url)
                .when().get(endpoint)
                .then().log().ifValidationFails()
                .body("[0].id",equalTo("3")); //AssertionError JSON path [0].id doesn't match

    }

}
