package RestAssuredTestProject.RestAssuredTestProject;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static org.hamcrest.Matchers.*;

import files.payLoad;
//comments aaded by vara
//added new method

public class Basics {

	public static void main(String[] args) {
	//validate if Add Place API is working as expected.
		
		//given - all input details
		//when - submit the API  - resource, http method
		//then - validate the Response
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body(payLoad.addPlace()).when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();
		
		//Add Place -> update place with new address -> Get Place to validate if new address is present in response.
		System.out.println(response);
		JsonPath js = new JsonPath(response); // for parsing JSON
		String placeId = js.getString("place_id");
		System.out.println(placeId);
		
		//Update place
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\n"
				+ "\"place_id\":\""+placeId+"\",\n"
				+ "\"address\":\"70 summer  walk, USA\",\n"
				+ "\"key\":\"qaclick123\"\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		
		//
//		JsonPath jsPutResponse = new JsonPath(putResponse);
//		String msg = jsPutResponse.getString(putResponse);
//		
//		System.out.println(msg);
		
	} 
}
