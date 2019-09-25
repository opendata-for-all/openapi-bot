package com.xatkit.plugins.openapi.platform.utils;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;

import edu.uoc.som.openapi2.API;
import edu.uoc.som.openapi2.Operation;
import edu.uoc.som.openapi2.Path;
import edu.uoc.som.openapi2.SecurityRequirement;

public class OpenAPIUtils {

	/**
	 * Get the list of operations which include security
	 * 
	 * @param api The input API
	 * @return The list of operations
	 */
	public static List<Operation> getOperationsIncludingSecurity(API api) {

		List<Operation> result = new ArrayList<Operation>();
		for (Operation operation : api.getAllOperations()) {
			if (!operation.getSecurity().isEmpty()) {
				result.add(operation);
			}
		}
		return null;

	}

	/**
	 * Returns whether the operation need authentication or not
	 * 
	 * @param operation The input operation
	 * @return {true} if the operation needs authentication or {false} otherwise
	 */
	public static boolean isAuthenticationRequired(Operation operation) {
		API api = ((Path) operation.eContainer()).getApi();
		if (!api.getSecurity().isEmpty()) {
			if (operation.getSecurity().size() == 1 && operation.getSecurity().get(0).getSecuritySchemes().isEmpty())
				return false;
			else
				return true;
		} else if (operation.getSecurity().isEmpty())
			return false;
		else if (operation.getSecurity().get(0).getSecuritySchemes().isEmpty())
			return false;
		else
			return true;
	}

	public static List<SecurityRequirement> getAuthentication(Operation operation) {
		API api = ((Path) operation.eContainer()).getApi();
		if (!api.getSecurity().isEmpty()) {
			if (operation.getSecurity().size() == 1 && operation.getSecurity().get(0).getSecuritySchemes().isEmpty())
				return new ArrayList<SecurityRequirement>();
			if (!operation.getSecurity().isEmpty())
				return operation.getSecurity();
			else
				return api.getSecurity();
		} else
			return operation.getSecurity();
	}

	public static List<Operation> getAllOperationsOnPath(Path path) {
		List<Operation> operations = new ArrayList<>();
		if (nonNull(path.getGet())) {
			operations.add(path.getGet());
		}
		if (nonNull(path.getPost())) {
			operations.add(path.getPost());
		}
		if (nonNull(path.getDelete())) {
			operations.add(path.getDelete());
		}
		if (nonNull(path.getPatch())) {
			operations.add(path.getPatch());
		}
		if (nonNull(path.getOptions())) {
			operations.add(path.getOptions());
		}
		if (nonNull(path.getPut())) {
			operations.add(path.getPut());
		}
		if (nonNull(path.getHead())) {
			operations.add(path.getHead());
		}
		return operations;
	}

	public static Operation getOperationByPathandHTTPMethod(String relativePath, String httpMethod, API api) {
		Path path = api.getPathByRelativePath(relativePath);
		if (nonNull(path)) {
			return getOperationByPathandHTTPMethod(path, httpMethod);
		}
		return null;

	}

	public static Operation getOperationByPathandHTTPMethod(Path path, String httpMethod) {
		return path.getOperationByHTTPMethod(httpMethod);
	}
}
