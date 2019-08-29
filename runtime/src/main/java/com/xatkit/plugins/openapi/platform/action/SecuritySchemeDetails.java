package com.xatkit.plugins.openapi.platform.action;

import java.util.HashMap;
import java.util.Map;

import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.openapi.platform.OpenAPIPlatform;
import edu.uoc.som.openapi2.API;
import edu.uoc.som.openapi2.SecurityScheme;

public class SecuritySchemeDetails extends RuntimeAction<OpenAPIPlatform>{

	String securityDefinitionId;
	public SecuritySchemeDetails(OpenAPIPlatform runtimePlatform, XatkitSession session, String securityDefinitionId) {
		super(runtimePlatform, session);
		this.securityDefinitionId = securityDefinitionId;
	}

	@Override
	protected Object compute() throws Exception {
		API api = (API) session.get(OpenAPIPlatform.LOADED_API_KEY);
		SecurityScheme securityScheme = api.getSecurityDefinitions().get(securityDefinitionId);
		Map<String, Object> result = new HashMap<String, Object>();
		if(securityScheme != null) {
			result.put("found", true);
			result.put("value",securityScheme);
		}
		else
			result.put("found", false);
		return result;
	}
}