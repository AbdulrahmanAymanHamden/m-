import org.codehaus.groovy.util.SingleKeyHashMap.Copier;

import files.Payload;
import files.ReusableMethods;
import io.restassured.path.json.JsonPath;

public class C02_ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JsonPath js = ReusableMethods.rawToJson(Payload.CoursePrice());
		
		int coursesNum = js.getList("courses").size();
		int purchase = js.getInt("dashboard.purchaseAmount");
		String title = js.getString("courses[0].title");
		
		int sumOfPrices = 0 ;
		int coursePrice = 0 ;
		
		for(int i = 0 ; i < coursesNum ; i++)
		{
			String courseTitle = js.getString("courses["+i+"].title");
		    coursePrice = js.getInt("courses["+i+"].price");
			
			
			System.out.println("Title : " + courseTitle);
			System.out.println("Price : " + coursePrice);
 		}

		
		for(int i = 0 ; i < coursesNum ; i++)
		{
			String courseTitle = js.getString("courses["+i+"].title");
			
			int copies = js.getInt("courses["+i+"].copies");
			coursePrice = js.getInt("courses["+i+"].price");
			
			sumOfPrices += (coursePrice*copies);
			
			if(courseTitle.equals("RPA"))
			{
				System.out.println("RPA copies : " + copies);
				break;
			}
			
 		}
		
		
		System.out.println(coursesNum);
		System.out.println(purchase);
		System.out.println(title);
		System.out.println(sumOfPrices);
		
		if(purchase == sumOfPrices)
		{
			System.out.println("Equal");
		}
	}

}
