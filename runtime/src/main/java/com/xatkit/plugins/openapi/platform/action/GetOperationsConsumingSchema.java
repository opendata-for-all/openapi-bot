package com.xatkit.plugins.openapi.platform.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.openapi.platform.OpenAPIPlatform;

import edu.uoc.som.openapi2.API;
import edu.uoc.som.openapi2.Operation;
import edu.uoc.som.openapi2.Schema;
import edu.uoc.som.openapi2.commons.OpenAPIUtils;

public class GetOperationsConsumingSchema extends RuntimeAction<OpenAPIPlatform>{
	
	private String schemaName;

	public GetOperationsConsumingSchema(OpenAPIPlatform runtimePlatform, XatkitSession session, String schemaName) {
		super(runtimePlatform, session);
		this.schemaName = schemaName;
	}

	@Override
	protected  Map<String, Object> compute() throws Exception {
		 API api = runtimePlatform.getApi(session);
		 Schema schema = com.xatkit.plugins.openapi.platform.utils.OpenAPIUtils.getSchemaByNameIgnoreCase(api, schemaName);
		 Map<String, Object> result = new HashMap<String, Object>();
		 
		 if(schema == null) {
				result.put("found", false);
				result.put("reason", "SchemaNotFound");
	    		result.put("options",api.getDefinitions().keySet().stream().collect(Collectors.toList()));
		 }
		 else {
			 List<Operation> operations = OpenAPIUtils.getAllOperationsConsumingSchema(api, schema);
			 if(operations.isEmpty()) {
			 result.put("found",false);
			 result.put("reason", "Empty");}
			 else  {
				 result.put("found", true);
				 result.put("value", operations);
			 }
		 }
		return result;
	}
	
	

}
