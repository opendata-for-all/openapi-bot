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
import edu.uoc.som.openapi2.Path;
import edu.uoc.som.openapi2.commons.OpenAPIUtils;

public class GetOperationByPathAndHttpMethod extends RuntimeAction<OpenAPIPlatform> {
	
	
	private String relativePath;
	private String httpMethod;
	private API api;

    public GetOperationByPathAndHttpMethod(OpenAPIPlatform platform, XatkitSession session, String operationPath, String operationHttpMethod) {
        super(platform, session);
        api = (API) session.get(OpenAPIPlatform.LOADED_API_KEY);
		relativePath = operationPath;
		httpMethod = operationHttpMethod;
    }

    @Override
    protected Map<String, Object> compute() throws Exception {
    	Map<String, Object> result = new HashMap<String, Object>();
    	Path path = api.getPathByRelativePath(relativePath);
    	if(nonNull(path)) {
    		Operation operation = path.getOperationByHTTPMethod(httpMethod);
    		if(nonNull(operation)) {
        		result.put("found", true);
        		result.put("value", operation);
        		result.put("responses",operation.getResponses().entrySet().stream().collect(Collectors.toList()));
        	}
    		else {
    			result.put("found", false);
        		result.put("reason", "OperationNotFound");
        		result.put("options",OpenAPIUtils.getAllOperationsOnPath(path));
    		}
    	}
    	else {
    		result.put("found", false);
    		result.put("reason", "PathNotFound");
    		result.put("options",api.getPaths());
    	}
    
		return result;
    }
}
