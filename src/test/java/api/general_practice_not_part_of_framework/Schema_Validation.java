package api.general_practice_not_part_of_framework;

import org.testng.annotations.Test;

import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class Schema_Validation {
	
	@Test
	public void validate_schema()
	{
		given()
		.baseUri("https://reqres.in")
		
		.when()
		.get("/api/users/2")
		
		.then()
		.assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema.json"));
		
	}

}

// Serialization - Process of converting POJO object into JSON before sending request and it's 
//handled internally automatically

// Deserialization - converting JSON back into POJO and taken care automatically.
