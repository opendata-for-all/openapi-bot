package com.xatkit.plugins.openapi.platform.action;

import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.openapi.platform.OpenAPIPlatform;
import edu.uoc.som.openapi2.API;
import edu.uoc.som.openapi2.io.OpenAPI2Builder;
import edu.uoc.som.openapi2.io.model.SerializationFormat;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

public class LoadAPI extends RuntimeAction<OpenAPIPlatform> {

	private String url;

	public LoadAPI(OpenAPIPlatform platform, XatkitSession session, String url) {
		super(platform, session);
		this.url = url;
	}

	@Override
	protected Object compute() throws Exception {
		InputStream inputStream = new URL(url).openStream();
		Reader reader = new InputStreamReader(inputStream);
		String defintion = IOUtils.toString(reader);
		Map<String, Object> result = new HashMap<String, Object>();

		API api = null;
		try {
			//we assume for  now that the specification is in JSON format
			api = new OpenAPI2Builder().setSerializationFormat(SerializationFormat.JSON).fromText(defintion);
			result.put("loaded",true);
			result.put("api", api);
			session.store(OpenAPIPlatform.LOADED_API_KEY, api);
		} catch (Exception e) {
			result.put("loaded",false);
			result.put("error",e.getMessage());
		}

		return result;
	}
}
