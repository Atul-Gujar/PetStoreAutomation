// request payload creation using hashmap
// using json body
// using POJO class and passing that object as payload (plain old java object)
// request body in external file
// using org.json library


package api.general_practice_not_part_of_framework;

import java.io.File;
import java.util.HashMap;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import io.restassured.internal.support.FileReader;
import io.restassured.response.Response;

public class requestBodyCreationTypes {
	
	@Test(priority = 1)
	public void requestAsHashMap()
	{
		HashMap data = new HashMap();
		data.put("name", "Atul");
		data.put("job", "leader1");
		
		given()
		.baseUri("https://reqres.in/")
		.contentType("application/json")
		.header("x-api-key", "reqres-free-v1")
		.body(data)
		
		.when()
		.post("/api/users")
		
		.then()
		.log().all()
		.statusCode(201)
		.body("name",equalTo("Atul"))
		.body("job",equalTo("leader1"));					
	}
	
	@Test(priority = 2)
	public void requestUsingJSON()
	{
		Response response = given()
		.baseUri("https://reqres.in/")
		.contentType("application/json")
		.header("x-api-key", "reqres-free-v1")
		.body("{\r\n"
				+ "    \"name\": \"morpheus\",\r\n"
				+ "    \"job\": \"leader\"\r\n"
				+ "}")
		
		.when()
		.post("/api/users");
		
		response.then().log().all();
		assertEquals(response.getStatusCode(), 201);
		assertEquals(response.jsonPath().getString("name"), "morpheus");
		assertEquals(response.jsonPath().getString("job"), "leader");
	}
	
	@Test(priority = 3)
	public void requestUsingPOJO()
	{
		request_POJO_Class data = new request_POJO_Class();
		data.setName("Atul");
		data.setJob("technical");
		
		Response response = given()
		.baseUri("https://reqres.in/")
		.contentType("application/json")
		.header("x-api-key", "reqres-free-v1")
		.body(data)
		
		.when()
		.post("/api/users");
		
		response.then().log().all().statusCode(201);
		assertEquals(response.jsonPath().getString("name"),"Atul");
		assertEquals(response.jsonPath().getString("job"), "technical");
		
	}
 
	@Test(priority = 5)
	public void usingOrgJSONLibrary()
	{
		JSONObject data = new JSONObject();
		data.put("name","sachin1");
		data.put("job","batsman1"); 
		
		Response response = given()
				.baseUri("https://reqres.in/")
				.contentType("application/json")
				.header("x-api-key", "reqres-free-v1")
				.body(data.toString())
				
				.when()
				.post("/api/users");
				
				response.then().log().all().statusCode(201);
				assertEquals(response.jsonPath().getString("name"),"sachin1");
				assertEquals(response.jsonPath().getString("job"), "batsman1");
		
	}
	
	
	@Test(priority = 4)
	public void usingExternalFile()
	{
		File file = new File(".\\testData\\body.json");
		Response response = given()
				.baseUri("https://reqres.in/")
				.contentType("application/json")
				.header("x-api-key", "reqres-free-v1")
				.body(file)
				
				.when()
				.post("/api/users");
				
				response.then().log().all().statusCode(201);
				assertEquals(response.jsonPath().getString("name"),"sachin1");
				assertEquals(response.jsonPath().getString("job"), "batsman1");
		
	}
}
