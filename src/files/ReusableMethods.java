package files;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {
	
	public static JsonPath rawToJson(String response) {
		
		JsonPath js_ = new JsonPath(response);
		return js_ ;		
	}

}
