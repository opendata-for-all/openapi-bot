package com.xatkit.plugins.openapi.config;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import com.xatkit.Xatkit;

import fr.inria.atlanmod.commons.log.Log;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

import java.io.IOException;
import java.util.Properties;

public class StartXatkit implements  BeforeAllCallback,ExtensionContext.Store.CloseableResource {

    private static boolean started = false;
	public static String CONFIF_FILENAME = "config.properties";

    @Override
    public void beforeAll(ExtensionContext context) throws IOException, InterruptedException {
        if (!started) {
        	Properties config = new Properties();
        	config.load(StartXatkit.class.getClassLoader().getResourceAsStream(CONFIF_FILENAME));
        	//Deploy Xatkit
        	Log.info("Starting Xatkit engine");
        	Xatkit.main(new String[] { config.getProperty("openAPIProperties") });
        	
        	started = true;
			context.getRoot().getStore(GLOBAL).put("StartXatkit", this);
        	
        }
    }



	@Override
	public void close() throws Throwable {
		Log.info("Shutting down Xatkit engine");
		Xatkit.getXatkitCore().shutdown();
		
	}


}