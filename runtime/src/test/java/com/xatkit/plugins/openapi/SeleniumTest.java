package com.xatkit.plugins.openapi;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.xatkit.Xatkit;

public class SeleniumTest {

	public static String CHATBOT_URL = "http://localhost:5000/admin";
	public static String XATKIT_PROPERTIES = "path/to/OpenAPIExample.properties";

	@BeforeAll
	public static void init() {
		Xatkit.main(new String[] { "XATKIT_PROPERTIES" });

	}

	@DisplayName("Simulate chat test")
	@Test
	public void chatTest() {

		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.get(CHATBOT_URL);

		WebElement element = driver.findElement(By.id("xatkit-chat"));
		assertTrue(element != null);

		 driver.quit();
	}

	public static void close() {
		Xatkit.getXatkitCore().shutdown();
	}

}
