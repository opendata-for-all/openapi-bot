package com.xatkit.plugins.openapi.platform.action;

import com.google.gson.stream.MalformedJsonException;
import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.openapi.platform.OpenAPIPlatform;
import edu.uoc.som.openapi2.API;
import edu.uoc.som.openapi2.io.OpenAPI2Builder;
import edu.uoc.som.openapi2.io.exceptions.OpenAPIProcessingException;
import edu.uoc.som.openapi2.io.exceptions.OpenAPIValidationException;
import edu.uoc.som.openapi2.io.model.SerializationFormat;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class LoadAPI extends RuntimeAction<OpenAPIPlatform> {

	private String url;

	public LoadAPI(OpenAPIPlatform platform, XatkitSession session, String url) {
		super(platform, session);
		this.url = url;
	}

	@Override
	protected Object compute() {
		Map<String, Object> result = new HashMap<String, Object>();

		API api = null;
		try {
			InputStream inputStream = new URL(url).openStream();
			Reader reader = new InputStreamReader(inputStream);
			String defintion = IOUtils.toString(reader);
			DumperOptions options = new DumperOptions();
			options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
			options.setPrettyFlow(true);
			Yaml yaml = new Yaml(options);
			Map<String, Object> map = (Map<String, Object>) yaml.load(defintion);
			// we assume for now that the specification is in JSON format
			api = new OpenAPI2Builder().setSerializationFormat(SerializationFormat.JSON).fromText(defintion);
			result.put("loaded", true);
			result.put("api", api);
			// we store the loaded API in the session.
			session.store(OpenAPIPlatform.LOADED_API_KEY, api);

		} catch (MalformedURLException e) {
			result.put("loaded", false);
			result.put("error", e.getMessage());
		}  catch (OpenAPIValidationException e) {
			e.printStackTrace();
		} catch (OpenAPIProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
