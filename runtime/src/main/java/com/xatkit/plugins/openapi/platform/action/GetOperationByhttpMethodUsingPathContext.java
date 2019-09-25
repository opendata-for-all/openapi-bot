package com.xatkit.plugins.openapi.platform.action;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.openapi.platform.OpenAPIPlatform;
import com.xatkit.plugins.openapi.platform.utils.OpenAPIUtils;

import edu.uoc.som.openapi2.Operation;
import edu.uoc.som.openapi2.Path;

public class GetOperationByhttpMethodUsingPathContext extends RuntimeAction<OpenAPIPlatform> {
	
	
	private String httpMethod;
	private Path path;

    public GetOperationByhttpMethodUsingPathContext(OpenAPIPlatform platform, XatkitSession session, String httpMethod) {
        super(platform, session);
		this.httpMethod = httpMethod;
		path = (Path) session.get(OpenAPIPlatform.PATH_IN_CONTEXT);
    }

    @Override
    protected Object compute() throws Exception {
    	
    	Operation operation = OpenAPIUtils.getOperationByPathandHTTPMethod(path, httpMethod);
    	
    
    	Map<String, Object> result = new HashMap<String, Object>();
    	if(nonNull(operation)) {
    		result.put("found", true);
    		result.put("value", operation);
    		result.put("responses",operation.getResponses().entrySet().stream().collect(Collectors.toList()));
    	}
    	else {
    		result.put("found", false);
    		result.put("operations",OpenAPIUtils.getAllOperationsOnPath(path));
    	}
        return result;
    }
}
