package com.xatkit.plugins.openapi.platform.action;

import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.openapi.platform.OpenAPIPlatform;
import com.xatkit.plugins.openapi.platform.utils.OpenAPIUtils;
import edu.uoc.som.openapi2.API;
import edu.uoc.som.openapi2.Operation;
import edu.uoc.som.openapi2.Path;
import edu.uoc.som.openapi2.Schema;

public class GetAnythingByName extends RuntimeAction<OpenAPIPlatform> {
	
	
	private String objectName;
	private API api;

    public GetAnythingByName(OpenAPIPlatform platform, XatkitSession session, String objectName) {
        super(platform, session);
        api = (API) session.get(OpenAPIPlatform.LOADED_API_KEY);
		this.objectName = objectName;
    }

    @Override
    protected Object compute() throws Exception {
    	Operation operation = OpenAPIUtils.getOperationByIdIgnoreCase(api,objectName);
    	if(operation != null)
    		return operation;
    	Path path = OpenAPIUtils.getPathByNameIgnoreCase(api, objectName);
    	if(path != null)
    		return path;
    	Schema schema = OpenAPIUtils.getSchemaByNameIgnoreCase(api, objectName);
    	if(schema != null)
    		return schema;
    	
        return null;
    }
}
