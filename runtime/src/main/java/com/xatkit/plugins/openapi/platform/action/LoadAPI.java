package com.xatkit.plugins.openapi.platform.action;

import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.openapi.platform.OpenAPIPlatform;
import edu.uoc.som.openapi2.API;
import edu.uoc.som.openapi2.io.OpenAPI2Builder;
import edu.uoc.som.openapi2.io.exceptions.OpenAPIProcessingException;
import edu.uoc.som.openapi2.io.exceptions.OpenAPIValidationException;
import edu.uoc.som.openapi2.io.exceptions.UnsupportedOpenAPIVersionException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class LoadAPI extends RuntimeAction<OpenAPIPlatform> {

	private String url;

	public LoadAPI(OpenAPIPlatform platform, XatkitSession session, String _url) {
		super(platform, session);
		this.url = _url;
	}

	@Override
	protected Map<String,Object> compute() {
		Map<String, Object> result = new HashMap<String, Object>();

		API api = null;
		try {
			
			api = new OpenAPI2Builder().fromURL(url);
			result.put("loaded", true);
			result.put("api", api);
			// we store the loaded API in the session.
			session.store(OpenAPIPlatform.LOADED_API_KEY, api);

		} catch (MalformedURLException e) {
			result.put("loaded", false);
			result.put("reason", "MalformedURLException");
			result.put("errorMessage", e.getMessage());
		
		}  catch (OpenAPIValidationException e) {
			result.put("loaded", false);
			result.put("reason", "OpenAPIValidationException");
			result.put("report", e.getReport().getErrorMessages());
			result.put("errorMessage", e.getMessage());
		
		} catch (OpenAPIProcessingException e) {
			result.put("loaded", false);
			result.put("reason", "OpenAPIProcessingException");
			result.put("errorMessage", e.getMessage());
		} catch (IOException e) {
			result.put("loaded", false);
			result.put("reason", "IOException");
			result.put("errorMessage", e.getMessage());

		} catch(UnsupportedOpenAPIVersionException e){
			result.put("loaded", false);
			result.put("reason", "UnsupportedOpenAPIVersionException");
			result.put("version", e.getOpenAPIVersion());
			result.put("errorMessage", e.getMessage());
		}
		catch (Exception e) {
			result.put("loaded", false);
			result.put("reason", "Unknown");
			e.printStackTrace();
		}
		return result;
	}
}