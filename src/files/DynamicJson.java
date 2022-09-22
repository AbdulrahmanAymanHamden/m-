package files;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {
	
	@Test(dataProvider = "book data")
	public void addBook(String isbn , String aisle)
	{
//		String isbn = "abcdeffghi";
//		String aisle = "223";
		String expect_ID = isbn + aisle ;
	
		// Set Base URL 
		RestAssured.baseURI = "http://216.10.245.166";
		
		//********************************** Add Book ***************************************//
		String addResponse = 
		
		given().log().all().
		header("Content-Type" , "application/json").
		body(Payload.AddBook(isbn,aisle)).
		
		
		when().post("Library/Addbook.php").
		
		
		then().log().all().
		assertThat().
			statusCode(200).
			extract().response().asString();
		
		// Extract ID value
		JsonPath js = ReusableMethods.rawToJson(addResponse);
		
		String addBookID = js.getString("ID");
		
		System.out.println(addBookID);
		Assert.assertEquals(addBookID, expect_ID);
		
		//*************************************** Delete Book ***********************************//
		String deleteResponse =
		given().log().all().
		header("Content-Type" , "application/json").
		body("{\r\n"
				+ "    \"ID\": \""+expect_ID+"\"\r\n"
				+ "}").
		
		
		when().delete("Library/DeleteBook.php").
		
		
		then().log().all().
		assertThat().
			statusCode(200).
			extract().response().asString();
		
		// Get Message Content
	    js = ReusableMethods.rawToJson(deleteResponse);	
		String deleteBookMsg = js.getString("msg");	
		Assert.assertEquals(deleteBookMsg, "book is successfully deleted");
	}
	
	@DataProvider(name = "book data")
	public Object[][] data()
	{
		Object[][] arr = new Object	[][] 
		{
			{"abc","550"},
			{"def","350"},
			{"ghi","22"}
		};
		
		return arr ;
	}
	

}
