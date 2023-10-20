package tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GetRequest {
    public String url = "https://api.zippopotam.us/";

    @Test
    public void testData() {
        given().
                when().
                    get(url+"us/90210").
                then().
                    assertThat().
                    body("places[0].'place name'", equalTo("Beverly Hills"));
    }

    //Check Status Code with Data Provider
    @Test(dataProvider = "urlData")
    public void testStatusCode(String countryCode , String postalCode) {
        given().
                when().
                get(url+countryCode+"/"+postalCode).
                then().
                assertThat().
                statusCode(200);
    }
    //End of Status Code Check

    @DataProvider
    public Object[][] urlData(){
        Object [] [] data = new Object[3][2];
        data [0][0] = "us";		data [0][1] = "99950";       //Postal code Range (00210 : 99950)
        data [1][0] = "de";     data [1][1] = "99998";       //Postal code Range (01067 : 99998)
        data [2][0] = "ca";		data [2][1] = "Y1A";         //Postal code Range (A0A : Y1A)
        return data;
    }

    //Check response content type
    @Test(dataProvider = "urlData")
    public void testResponseContentType(String countryCode , String postalCode){
        given().
                when().
                get(url+countryCode+"/"+postalCode).
                then().
                assertThat().
                contentType(ContentType.JSON);
    }

}
