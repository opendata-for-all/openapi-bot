package com.xatkit.plugins.openapi.platform.utils;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import edu.uoc.som.openapi2.API;
import edu.uoc.som.openapi2.Operation;
import edu.uoc.som.openapi2.Path;
import edu.uoc.som.openapi2.Schema;

public class OpenAPIUtils {

	public static Operation getOperationByIdIgnoreCase(API api,String operationId) {
		
		List<Operation> allOperations = api.getAllOperations();
		for(Operation operation: allOperations)
			if(operation.getOperationId()!=null && !operation.getOperationId().isEmpty() && operation.getOperationId().equalsIgnoreCase(operationId))
				return operation;
		return null;	
	}
	
	public static Schema getSchemaByNameIgnoreCase(API api, String schemaName) {
		Set<Entry<String, Schema>> allSchemas = api.getDefinitions().entrySet();
		for(Entry<String,Schema> schemaEntry : allSchemas) {
			if(schemaEntry.getKey().equalsIgnoreCase(schemaName))
				return schemaEntry.getValue();
		}
		return null;
	}
	
	public static Path getPathByNameIgnoreCase(API api, String relativePath) {
		List<Path> allPaths = api.getPaths();
		for(Path path: allPaths)
			if(path.getRelativePath()!= null && !path.getRelativePath().isEmpty() && path.getRelativePath().equalsIgnoreCase(relativePath))
				return path;
		return null;
	}
}
