package com.xatkit.plugins.openapi.platform.utils;

import java.util.List;

import edu.uoc.som.openapi2.Operation;
import edu.uoc.som.openapi2.Path;

public class FormatterUtils {

	public static String formatPath(Path path) {
		List<Operation> operations = OpenAPIUtils.getAllOperationsOnPath(path);
		StringBuffer formattedPath = new StringBuffer();
		formattedPath.append("The relative path of this path item is "+path.getRelativePath());
		if(operations.isEmpty()) {
			formattedPath.append("\n");
			formattedPath.append("There are no operations defined on this path.");
		}
		else {
			formattedPath.append("There are"+operations.size()+" available on this path:");
			for(Operation operation:operations)
				formattedPath.append(" "+operation.getHTTPMethod().toUpperCase());
			formattedPath.append(".");
			
		}
	
		return formattedPath.toString();
		
	}
}
