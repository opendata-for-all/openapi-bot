package com.xatkit.plugins.openapi.platform.action;

import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.openapi.platform.OpenAPIPlatform;
import com.xatkit.plugins.openapi.platform.utils.OpenAPIUtils;

import edu.uoc.som.openapi2.API;
import edu.uoc.som.openapi2.Operation;

public class IsAuthenticationRequiredOperation extends RuntimeAction<OpenAPIPlatform> {

	String operationId;
	API api;

	public IsAuthenticationRequiredOperation(OpenAPIPlatform platform, XatkitSession session, String operationId) {
		super(platform, session);
		api = (API) session.get(OpenAPIPlatform.LOADED_API_KEY);
		this.operationId = operationId;
	}

	@Override
	protected Object compute() throws Exception {
		Map<String, Object> result = new HashedMap<String, Object>();
		Operation operation = api.getOperationById(operationId);
		if (operation == null) {
			result.put("found", false);
			result.put("operations", api.getAllOperations());
			return result;
		}
		result.put("found", true);
		boolean answer = OpenAPIUtils.isAuthenticationRequired(operation);
		result.put("answer", answer);
		if (answer == true)
			result.put("options", OpenAPIUtils.getAuthentication(operation));
		return result;

	}
}