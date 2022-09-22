import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import files.Payload;
import files.ReusableMethods;
import io.restassured.path.json.JsonPath;

public class C03_sumValidation {
	
	@Test
	public void sumOfCourses()
	{
		JsonPath js = ReusableMethods.rawToJson(Payload.CoursePrice());
		
		int coursesNum = js.getList("courses").size();
		
		int sumOfPrices = 0 ;
		
		for(int i = 0 ; i < coursesNum ; i++)
		{
			
			int copies = js.getInt("courses["+i+"].copies");
			int coursePrice = js.getInt("courses["+i+"].price");
			
		    sumOfPrices += (coursePrice*copies);
 		}
		
		int purchase = js.getInt("dashboard.purchaseAmount");
		
		Assert.assertEquals(sumOfPrices, purchase);
		

	}
	
}
