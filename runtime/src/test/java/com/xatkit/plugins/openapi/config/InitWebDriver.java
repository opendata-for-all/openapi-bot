package com.xatkit.plugins.openapi.config;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


import fr.inria.atlanmod.commons.log.Log;

public class InitWebDriver implements BeforeAllCallback,ExtensionContext.Store.CloseableResource {

    private static boolean started = false;
	public static String CONFIF_FILENAME = "config.properties";
	private WebDriver driver;

    @Override
    public void beforeAll(ExtensionContext context) throws IOException, InterruptedException {
        if (!started) {
        	Properties config = new Properties();
        	config.load(StartXatkit.class.getClassLoader().getResourceAsStream(CONFIF_FILENAME));
        	Log.info("Initializing Firefox driver");
        	//Run Selenium
        	driver = new FirefoxDriver();
    		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    		driver.get(config.getProperty("xatkitUrl"));
    		
        	
    		started = true;
            context.getRoot().getStore(GLOBAL).put("InitWebDriver", this);
            context.getRoot().getStore(GLOBAL).put("driver", driver);
        }
    }

		

	@Override
	public void close() throws Throwable {
		Log.info("Closing Firefox driver");
		driver.close();
		
		
	}


}