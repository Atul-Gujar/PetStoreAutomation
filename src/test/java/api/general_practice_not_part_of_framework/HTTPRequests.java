package api.general_practice_not_part_of_framework;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

public class HTTPRequests {
	int id;
	
	@Test(priority = 1)
	public void getUser()
	{
		  given()
		  .baseUri("https://reqres.in")
		  .header("x-api-key", "reqres-free-v1")
		  .when()
		  .get("/api/users/2")
		  .then()
		  .statusCode(200)
		  .body("data.id", equalTo(2))
		  .body("data.email", equalTo("janet.weaver@reqres.in"))
          .body("data.first_name", equalTo("Janet"))
          .body("data.last_name", equalTo("Weaver"))
          .body("data.avatar", equalTo("https://reqres.in/img/faces/2-image.jpg"))
          .body("support.url", equalTo("https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral"))
          .body("support.text", equalTo("Tired of writing endless social media content? Let Content Caddy generate it for you."))
		  .log().all();
	}
	
	@Test(priority = 2)
	public void createUser()
	{
		//using hashmap to create request body and converting it into json, not recommended
		
		HashMap data = new HashMap();
		data.put("name", "Atul");
		data.put("job", "leader");
		
		Response response = given()
		.baseUri("https://reqres.in")
		.header("x-api-key", "reqres-free-v1")
		.body(data)
		.contentType("application/json")
		
		.when()
		.post("/api/users");
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 201);
		String name = response.jsonPath().get("name").toString();
		assertEquals(name, "Atul");
	    //assertEquals(response.jsonPath().getString("name"),"Atul");
	    assertEquals(response.jsonPath().getString("job"),"leader");
		id=response.jsonPath().getInt("id");	
	}
	
	@Test(priority = 3, dependsOnMethods = {"createUser"})
	public void updateUser()
	{
		HashMap data = new HashMap();
		data.put("name", "Atul1");
		data.put("job", "leader1");
		
		Response response = given()
		.baseUri("https://reqres.in")
		.header("x-api-key", "reqres-free-v1")
		.contentType("application/json")
		.body(data)
		
		.when()
		.put("/api/users/"+id);
		
		response.then().log().all().statusCode(200);
		assertEquals(response.jsonPath().getString("name"),"Atul1");
		assertEquals(response.jsonPath().getString("job"), "leader1");
	}
	
	@Test(priority = 4, dependsOnMethods = {"createUser"})
	public void deleteUser()
	{
		Response respone=given()
		.baseUri("https://reqres.in")
		.header("x-api-key", "reqres-free-v1")
		.when()
		.delete("/api/users/"+id);
		
		respone.then().log().all();
		Assert.assertEquals(respone.getStatusCode(),204);
		
		
	}
	
	@Test(priority = 5)
	public void queryParamTest()
	{
		//https://reqres.in/api/users?page=2
		
		given()
		.baseUri("https://reqres.in")
		.header("x-api-key", "reqres-free-v1")
		.queryParam("page",2)
		
		.when()
		.get("/api/users")
		
		.then()
		.statusCode(200)
		.log().all();
		
	}
	
	@Test(priority = 6)
	public void pathParameterTest()
	{
		given()
		  .baseUri("https://reqres.in")
		  .header("x-api-key", "reqres-free-v1")
		  .pathParam("userid", 2)
		  
		  .when()
		  .get("/api/users/{userid}")
		  
		  .then()
		  .statusCode(200)
		  .log().all();
	}
	
	@Test(priority = 7)
	public void get_cookieInfo()
	{
		Response response =given()
		
		.when()
		.get("https://www.google.com/");
		
		response.then().log().all();
		
		Map<String, String> map = response.getCookies();
		
		for(String k:map.keySet())
		{
			String cookie_value = response.getCookie(k);
			System.out.println(k+ " "+cookie_value);
		}
		
		// values of cookies cant be same everytime hence cant validate for values
		
		// validating headers of response which are constant
		assertEquals(response.getHeader("Content-Type"), "text/html; charset=ISO-8859-1");
		assertEquals(response.getHeader("Content-Encoding"), "gzip");
		assertEquals(response.getHeader("Server"), "gws");
		
		Headers headers = response.getHeaders();
				
		for(Header hd:headers)
		{
			System.out.println(hd.getName()+ "  "+hd.getValue());
		}
		
		// to log only body use .log().body() 
		// to print only cookies .log().cookies()
		// to print only headers .log().headers()
	}
	
	@Test(priority = 8)
	public void JsonResponseValidation()
	{
		// to traverse response object first we need to convert it to string and then to JSONObject
		
		 Response response = given()
		  .baseUri("https://reqres.in")
		  .header("x-api-key", "reqres-free-v1")
		  .contentType(ContentType.JSON)
				  
		  .when()
		  .get("/api/users/2");
		
		  JSONObject jo = new JSONObject(response.getBody().asString()); // JSONObject will help for dynamic responses
		  System.out.println(jo.getJSONObject("data").getInt("id"));
		  System.out.println(jo.getJSONObject("data").getString("email"));
		  System.out.println(jo.getJSONObject("support").get("url").toString());
		  
		 
		
	}
}
