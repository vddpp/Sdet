package sdetbasic.restassured;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import junit.framework.Assert;

public class CountriesAPI {
	
	@Test
	public void validCapital() {
		Response response = RestAssured
		.given()
		.when()
		.get("https://restcountries.com/v3.1/capital/tallinn");
		
		//Verify the status code
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//Parse Json and validate countryName
		String countryName = response.jsonPath().getString("[0].name.common"); 
		Assert.assertEquals(countryName, "Estonia");
		
	}
	
	@Test
	public void inValidCapital() {
		Response response = RestAssured
		.given()
		.when()
		.get("https://restcountries.com/v3.1/capital/1");
		
		//Verify the status code
		Assert.assertEquals(response.getStatusCode(), 404);
		
	}
	
	

}
