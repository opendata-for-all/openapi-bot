package com.xatkit.plugins.openapi.platform.utils;

import static java.util.Objects.nonNull;


import edu.uoc.som.openapi2.JSONDataType;
import edu.uoc.som.openapi2.Operation;
import edu.uoc.som.openapi2.Parameter;
import edu.uoc.som.openapi2.ParameterLocation;
import edu.uoc.som.openapi2.Response;
import edu.uoc.som.openapi2.Schema;
import edu.uoc.som.openapi2.commons.OpenAPIUtils;

public class FormattingUtils {

	public static String formatOperation(Operation o) {
		return new StringBuilder().append(o.getHTTPMethod()).append(" ").append(
				(nonNull(o.getOperationId()) && !o.getOperationId().equals("")) ? "(" + o.getOperationId() + ")" : "")
				.append(": ").append(o.getSummary()).toString();
	}

	public static String formatResponse(Response response) {
		StringBuffer result = new StringBuffer();
		result.append(response.getDescription());
		if (response.getSchema() != null) {
			result.append(": "+formatSchema(response.getSchema()));
		}
		else result.append(": no schema defined");
		return result.toString();
	}

	public static String formatSchema(Schema schema) {
		StringBuffer result = new StringBuffer();
		if (schema.getName() != null && schema.getType().equals(JSONDataType.ARRAY)) {
			result.append(schema.getName()).append(" (array of ").append(formatSchema(schema.getItems())).append(")");
		} else {
			if (schema.getType().equals(JSONDataType.ARRAY)) {
				result.append("array of ").append(formatSchema(schema.getItems()));
			} else {
				if(schema.getName()!=null)
				result.append(schema.getName());
				else {
					if(OpenAPIUtils.isPrimitive(schema.getType()))
						result.append(schema.getType());
				}
			}
		}
		return result.toString();
	}
	public static String formatParameter (Parameter parameter) {
		StringBuffer result = new StringBuffer();
		if(parameter.getName()!=null) {
			result.append(parameter.getName());
			}
		
		switch (parameter.getLocation()) {
		
		case BODY: result.append(" (body parameter)"); break;
		case FORM_DATA: result.append(" (form parameter)"); break;
		case HEADER:  result.append(" (header parameter)"); break;
		case PATH :  result.append(" (path parameter)"); break;
		case QUERY:  result.append(" (query parameter)"); break;
		default:  result.append(" (unknown location)"); break;
		
		}
		if(parameter.getRequired()!= null && parameter.getRequired())
			result.append(", required");
		result.append(result.toString().equals("")?"":": ");
		if(parameter.getLocation().equals(ParameterLocation.BODY) && parameter.getSchema()!= null){
			result.append(formatSchema(parameter.getSchema()));
		} else {
			if(OpenAPIUtils.isArrayOfPrimitives(parameter)) {
				result.append("list of "+parameter.getItems());
			}
			else result.append(parameter.getType().toString());
		}
		return result.toString();
}
public static String formatObject (Object object, String prefix, String suffix) {
	if(object instanceof Response) {
		String result = formatResponse((Response) object);
		return prefix +result + suffix;}
	return "";
}
public static String formatProperty(edu.uoc.som.openapi2.Property property) {
	StringBuffer result = new StringBuffer();
	result.append(property.getName());
	if(property.getRequired()!= null && property.getRequired())
		result.append("(required)");
	result.append(": ");
	if(property.getSchema()!= null)
		result.append(formatSchema(property.getSchema()));
	return result.toString();
}
}
