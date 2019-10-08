package com.xatkit.plugins.openapi.platform.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.openapi.platform.OpenAPIPlatform;
import edu.uoc.som.openapi2.API;
import edu.uoc.som.openapi2.Schema;
import edu.uoc.som.openapi2.commons.Heuristics;
import edu.uoc.som.openapi2.mapping.PropertyToParameter;
import edu.uoc.som.openapi2.mapping.PropertyToProperty;

public class GetSchemasReusingSchemaParts extends RuntimeAction<OpenAPIPlatform>{
	
	private String schemaName;

	public GetSchemasReusingSchemaParts(OpenAPIPlatform runtimePlatform, XatkitSession session, String schemaName) {
		super(runtimePlatform, session);
		this.schemaName = schemaName;
	}

	@Override
	protected Object compute() throws Exception {
		 API api = runtimePlatform.getApi(session);
		 Schema schema = api.getDefinitions().get(schemaName);
		 Map<String, Object> result = new HashMap<String, Object>();
		 
		 if(schema == null) {
				result.put("found", false);
				result.put("reason", "SchemaNotFound");
	    		result.put("options",api.getDefinitions().keySet().stream().collect(Collectors.toList()));
		 }
		 else {
			 List<PropertyToProperty> propertyToProperties = Heuristics.discoverPropertyLinks(api.getDefinitions().get(api.getDefinitions().indexOfKey(schemaName)), api);
			 if(propertyToProperties.isEmpty()) {
			 result.put("found",false);
			 result.put("reason", "Empty");}
			 else  {
				 result.put("found", true);
				 result.put("value", propertyToProperties);
			 }
		 }
		return result;
	}
	
	

}
