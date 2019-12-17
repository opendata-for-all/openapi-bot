package com.xatkit.plugins.openapi.platform.action;

import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.openapi.platform.OpenAPIPlatform;

import edu.uoc.som.openapi2.API;
import edu.uoc.som.openapi2.Path;
import edu.uoc.som.openapi2.commons.OpenAPIUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetPathDetailsUsingRowNumber extends RuntimeAction<OpenAPIPlatform> {
	  private Integer rowNumber;

	    public GetPathDetailsUsingRowNumber(OpenAPIPlatform platform, XatkitSession session, Integer rowNumber) {
	        super(platform, session);
	        this.rowNumber  = rowNumber;
	    }

	    @Override
	    protected Map<String,Object> compute() throws Exception {
	        
	    	 API api = runtimePlatform.getApi(session);
	    	 List<Path> paths = (List<Path>) session.get(OpenAPIPlatform.PATH_LIST_IN_CONTEXT);
	    	 Path path = paths.get(rowNumber);
	    	 session.store(OpenAPIPlatform.PATH_IN_CONTEXT, path);
	    	 Map<String,Object> result = new HashMap<String, Object>();
	    		 if(path!=null) {
	    			 result.put("found", true);
	    			 result.put("value", path);
	    			 result.put("operations", OpenAPIUtils.getAllOperationsOnPath(path));
	    		 }
	    		 else {
	    			 result.put("found", false);
	    			 result.put("options", api.getPaths());
	    		 }
	    			 
	    	return result;
	    }

}
