package com.xatkit.plugins.openapi.platform.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.openapi.platform.OpenAPIPlatform;
import edu.uoc.som.openapi2.API;
import edu.uoc.som.openapi2.SecurityScheme;

public class SecuritySchemeList extends RuntimeAction<OpenAPIPlatform>{

	public SecuritySchemeList(OpenAPIPlatform runtimePlatform, XatkitSession session) {
		super(runtimePlatform, session);
	}

	@Override
	protected List<Map.Entry<String, SecurityScheme>> compute() throws Exception {
		API api = (API) session.get(OpenAPIPlatform.LOADED_API_KEY);
		List<Map.Entry<String, SecurityScheme>> securityDefinitions = new ArrayList<Map.Entry<String, SecurityScheme>>();
		securityDefinitions.addAll(api.getSecurityDefinitions());
		return securityDefinitions;
	}

}
