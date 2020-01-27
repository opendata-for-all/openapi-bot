package com.xatkit.plugins.openapi.platform.action;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.openapi.platform.OpenAPIPlatform;
import com.xatkit.plugins.openapi.platform.utils.OpenAPIUtils;

import edu.uoc.som.openapi2.API;
import edu.uoc.som.openapi2.Schema;

public class GetSchemaDetails extends RuntimeAction<OpenAPIPlatform> {
	
	
	private String schemaName;
	private API api;

    public GetSchemaDetails(OpenAPIPlatform platform, XatkitSession session, String schemaName) {
        super(platform, session);
        api = (API) session.get(OpenAPIPlatform.LOADED_API_KEY);
		this.schemaName = schemaName;
    }

    @Override
    protected Map<String, Object> compute() throws Exception {
    	Schema schema = OpenAPIUtils.getSchemaByNameIgnoreCase(api, schemaName);
    	Map<String, Object> result = new HashMap<String, Object>();
    	if(nonNull(schema)) {
    		result.put("found", true);
    		result.put("value", schema);
    	}
    	else {
    		result.put("found", false);
    		result.put("options",api.getDefinitions().keySet().stream().collect(Collectors.toList()));
    	}
        return result;
    }
}
