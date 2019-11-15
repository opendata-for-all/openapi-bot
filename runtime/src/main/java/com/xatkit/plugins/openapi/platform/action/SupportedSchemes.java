package com.xatkit.plugins.openapi.platform.action;

import java.util.List;

import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.openapi.platform.OpenAPIPlatform;
import edu.uoc.som.openapi2.API;
import edu.uoc.som.openapi2.SchemeType;

public class SupportedSchemes extends RuntimeAction<OpenAPIPlatform>{

	public SupportedSchemes(OpenAPIPlatform runtimePlatform, XatkitSession session) {
		super(runtimePlatform, session);
	}

	@Override
	protected List<SchemeType> compute() throws Exception {
		API api = (API) session.get(OpenAPIPlatform.LOADED_API_KEY);
		return api.getSchemes();
	}

}
