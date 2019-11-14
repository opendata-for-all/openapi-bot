package com.xatkit.plugins.openapi.config;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import com.github.tomakehurst.wiremock.WireMockServer;

import fr.inria.atlanmod.commons.log.Log;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

public class InitWireMocServer implements BeforeAllCallback, ExtensionContext.Store.CloseableResource{

	private static boolean started = false;
	public static String CONFIF_FILENAME = "config.properties";
	private WireMockServer wireMockServer;
	
	@Override
	public void beforeAll(ExtensionContext context) throws IOException {
		if (!started) {
			Log.info("Starting WireMockServer");
			Properties config = new Properties();
			config.load(InitWireMocServer.class.getClassLoader().getResourceAsStream(CONFIF_FILENAME));

			wireMockServer = new WireMockServer(
					options().port(Integer.valueOf(config.getProperty("wireMockServerPort")))); // No-args constructor
																								// will start on port
																								// 8080, no HTTPS
			wireMockServer.start();

			Collection<File> jsonDefinitions = FileUtils.listFiles(new File(getClass().getClassLoader().getResource("definitions/json").getFile()), null, true);
			for(File jsonDef : jsonDefinitions) {
				Log.info("Deploying  "+jsonDef.getName());
				stubFor(get(urlEqualTo(config.getProperty("wireMockServerBasePath") + "/json/" + jsonDef.getName()))
					.willReturn(aResponse().withHeader("Content-Type", "application/json")
							.withBody(FileUtils.readFileToByteArray(jsonDef))));
			}

			started = true;
			context.getStore(GLOBAL).put("InitWireMocServer", this);
		}
	}

	@Override
	public void close() throws Throwable {
		Log.info("Shutting down WireMockServer");
		wireMockServer.shutdown();
		
	}


}