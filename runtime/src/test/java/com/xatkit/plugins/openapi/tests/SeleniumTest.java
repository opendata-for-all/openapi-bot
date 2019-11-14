package com.xatkit.plugins.openapi.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBeMoreThan;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;




public class SeleniumTest {


	private WebDriver driver;
	
	public SeleniumTest(FirefoxDriver driver) 
	{
		this.driver = driver;
	}
	@DisplayName("Page load test")
	@Test
	void chatTest() throws FileNotFoundException, IOException {


		System.err.println(driver);
		WebElement element = driver.findElement(By.id("xatkit-chat"));
		assertTrue(element != null);

	}
	
	


	
	@DisplayName("Load valid Json definition test")
	@Test
	public void loadValidJsonDefinition() {

		 WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement element = driver.findElement(By.id("xatkit-chat"));
		
		WebElement message = element.findElement(By.name("message"));
		message.sendKeys("http://localhost:8080/openapi-directory/json/petstore.json"+Keys.ENTER);
		wait.until(numberOfElementsToBeMoreThan(By.className("rcw-response"), 2));
		List<WebElement> responses = driver.findElements(By.className("rcw-response"));
		assertAll("Petstore should be loaded", 
				() -> assertTrue(responses.size() == 3),
				() -> assertTrue(responses.get(1).getText().equals("The API Swagger Petstore, version 1.0.0, was successfully loaded")));
		
		
	
	}

	

}
