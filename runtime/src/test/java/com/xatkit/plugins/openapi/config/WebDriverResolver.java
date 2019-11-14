package com.xatkit.plugins.openapi.config;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverResolver implements ParameterResolver {
	 
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, 
      ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType()
          .equals(FirefoxDriver.class);
    }
 
    @Override
    public Object resolveParameter(ParameterContext parameterContext, 
      ExtensionContext extensionContext) throws ParameterResolutionException {
    	System.out.println("driver: "+extensionContext.getStore(GLOBAL).get("driver"));
        return extensionContext.getRoot().getStore(GLOBAL).get("driver");
    }
}