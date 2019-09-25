package com.xatkit.plugins.openapi.platform.action;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.openapi.platform.OpenAPIPlatform;
import edu.uoc.som.openapi2.API;
import edu.uoc.som.openapi2.Operation;

public class GetOperationById extends RuntimeAction<OpenAPIPlatform> {
	
	
	private String operationId;
	private API api;

    public GetOperationById(OpenAPIPlatform platform, XatkitSession session, String operationId) {
        super(platform, session);
        api = (API) session.get(OpenAPIPlatform.LOADED_API_KEY);
		this.operationId = operationId;
    }

    @Override
    protected Object compute() throws Exception {
    	Operation operation = api.getOperationById(operationId);
    	Map<String, Object> result = new HashMap<String, Object>();
    	if(nonNull(operation)) {
    		result.put("found", true);
    		result.put("value", operation);
    		result.put("responses",operation.getResponses().entrySet().stream().collect(Collectors.toList()));
    	}
    	else {
    		result.put("found", false);
    	}
        return result;
    }
}
