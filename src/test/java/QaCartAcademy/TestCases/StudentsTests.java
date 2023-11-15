package QaCartAcademy.TestCases;

import QaCartAcademy.POJO.LoginPojo;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class StudentsTests {
    String url = "https://todo.qacart.com/";
    String studentsEndpoint = "api/v1/students/";


    @Test
    public void testLoginWithStringJSON(){
        String loginData = """
                {
                    "email": "hatem@example.com",
                    "password": "Test1234"
                }""";

        Header contentTypeJSON = new Header("Content-Type","application/json");

        given()
                .baseUri(url)
                .body(loginData)
                .contentType(ContentType.JSON) //same as using header
                //.header(contentTypeJSON)
                .log().all()
                .when()
                .post(studentsEndpoint+"login")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }
    @Test
    public void testLoginWithJSONFile(){
        File bodyJSON = new File("src/main/resources/Login.json");
        given()
                .baseUri(url)
                .body(bodyJSON)
                .contentType(ContentType.JSON) //same as using header
                //.header(contentTypeJSON)
                .log().all()
                .when()
                .post(studentsEndpoint+"login")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }
    @Test
    public void testLoginWithHashMapBody(){
        HashMap<String ,String > bodyHashmap = new HashMap<>();
        bodyHashmap.put("email", "hatem@example.com");
        bodyHashmap.put("password", "Test1234");

        given()
                .baseUri(url)
                .body(bodyHashmap)
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .post(studentsEndpoint+"login")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    } //Use Jackson dependency for serialization
    @Test
    public void testLoginWithPOJO(){
        LoginPojo pojoBody = new LoginPojo("hatem@example.com","Test1234");
        //pojoBody.setEmail("hatem@example.com");
        //pojoBody.setPassword("Test1234");

        given()
                .baseUri(url)
                .body(pojoBody)
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .post(studentsEndpoint+"login")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }
    @Test
    public void testLoginWithURLEncoded(){
        HashMap<String ,String > formParameter = new HashMap<>();
        formParameter.put("Foo","1234");

        given()
                .baseUri(url)
                .formParams(formParameter)
                .contentType(ContentType.URLENC)
                .log().all()
                .when()
                .post(studentsEndpoint+"login")
                .then()
                .log().all()
                .assertThat().statusCode(200);
        ;
    }
    @Test
    public void testLoginWithFormData(){
        File bodyJSON = new File("src/main/resources/Login.json");

        given()
                .baseUri(url)
                .multiPart("file",bodyJSON)
                .contentType(ContentType.MULTIPART)
                .log().all()
                .when()
                .post(studentsEndpoint+"login")
                .then()
                .log().all()
                .assertThat().statusCode(200);
        ;
    }

}
