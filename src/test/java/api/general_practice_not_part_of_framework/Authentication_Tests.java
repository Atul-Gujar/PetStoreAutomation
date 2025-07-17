package api.general_practice_not_part_of_framework;

import org.testng.annotations.Test;

import com.aventstack.extentreports.gherkin.model.Given;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class Authentication_Tests {
	
	@Test(priority = 1)
	public void testBasicAuthentication()
	{
		given()
		.auth().basic("postman","password")
		.baseUri("https://postman-echo.com")
		
		.when()
		.get("/basic-auth")
		
		.then()
		.statusCode(200)
		.body("authenticated",equalTo(true));
		
	}
	
	@Test(priority = 2)
	public void testDigestAuthentication()
	{
		given()
		.auth().digest("postman","password")
		.baseUri("https://postman-echo.com")
		
		.when()
		.get("/digest-auth")
		
		.then()
		.statusCode(200)
		.body("authenticated",equalTo(true));
	}
	
	@Test(priority = 3)
	public void testPreemptiveAuthentication()
	{
		given()
		.auth().preemptive().basic("postman","password")
		.baseUri("https://postman-echo.com")
		
		.when()
		.get("/basic-auth")
		
		.then()
		.statusCode(200)
		.body("authenticated",equalTo(true));
	}

	@Test(priority = 4)
	public void testBearerToken()
	{
		given()
		.header("Authorization","Bearer 1e08c413aa5c99fc3c01401df805471823ee129e65a603c37061e2dda4264bdd")
		
		.when()
		.get("https://gorest.co.in/public/v2/users")
		
		.then()
		.statusCode(200)
		.body("[0].name", equalTo("Brajendra Sethi V"));
		
	}
	
	@Test(priority = 5)
	public void testOAuth2Authentication()
	{
		// To generate this access token first api we need to send client id it will give code in response.
		// In second API we will need to pass client id, secret id, code in request and then this will generate access token which can then be used to access API's
		given()
		.auth().oauth2("access_token")
		
		.when()
		.get("url")
		
		.then()
		.statusCode(200);
	}

	@Test(priority = 6)
	public void testAPIKeyAuthentication()
	{
		given()
		.queryParam("appid","f3e4567tyui9")
		.when()
		.get("https://gorest.co.in/public/v2/users")
		.then()
		.statusCode(200);
	}
}
