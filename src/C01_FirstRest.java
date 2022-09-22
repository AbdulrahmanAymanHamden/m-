import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.* ;

import org.testng.Assert;

import files.Payload;
import files.ReusableMethods;

public class C01_FirstRest {
	

	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Validate that Add place API is working as expected

		// given --> Set Input Details
		// when  --> Submit API ( Send Request )
		// then  --> Validate API Response 
		
		RestAssured.baseURI = ("https://rahulshettyacademy.com");
	
		/******************************************************************** Add Place *********************************************************************/
		// Get response body into Var
		String response = 
				
		// Given
		given().log().all().
		queryParam("key", "qaclick123").
		header("Content-Type", "application/json").
		body(Payload.AddPlace()).
		
		// When
		when().post("maps/api/place/add/json").
		
		// Then
		then().
		assertThat().
				// Status Code 
				statusCode(200).
				// Check Response Body
				body("scope", equalTo("APP")).
				// Check Response Header
				header("Server", "Apache/2.4.41 (Ubuntu)").
				// Get response into String Var
				extract().response().asString();
		
		System.out.println(response);
		
		// Extract Place ID 
		JsonPath js = ReusableMethods.rawToJson(response);
		String placeID = js.get("place_id");
		System.out.println(placeID);
		
		/******************************************************************** Update Place *********************************************************************/
		
		String newAddress = "70 winter walk, USA";
		// Given
		given().log().all().
		queryParam("key", "qaclick123").
		header("Content-Type", "application/json").
		body("{\r\n"
				+ "\"place_id\":\""+ placeID +"\",\r\n"
				+ "\"address\":\""+ newAddress +"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}").
		
		// When
		when().put("maps/api/place/update/json").
		
		//Then
		then().
		assertThat().log().all().
		        // Status Code
				statusCode(200).
				// Check return message
				body("msg", equalTo("Address successfully updated"));
		
		
		/******************************************************************** Get Place *********************************************************************/
		
		// Given
		String getPlaceResponse =  
				
		given().log().all().
		queryParam("key", "qaclick123").queryParam("place_id", placeID).
		
		// When
		when().get("maps/api/place/get/json").
		
		//Then
		then().
		assertThat().log().all().
		        // Status Code
				statusCode(200).extract().response().asString();
		
		JsonPath js_place = ReusableMethods.rawToJson(getPlaceResponse);
		String currentAddress = js_place.getString("address");
		
		System.out.println(currentAddress);
		
		Assert.assertEquals (currentAddress, newAddress);
		
	}

	
	
}
