package com.xatkit.plugins.openapi.platform.utils;

import static java.util.Objects.nonNull;

import edu.uoc.som.openapi2.Operation;

public class FormattingUtils {
	
	public static String formatOperation(Operation o) {
		return 	new StringBuilder().append(o.getHTTPMethod()).
                append(" ").
                append((nonNull(o.getOperationId()) && !o.getOperationId().equals(""))?"("+o.getOperationId()+")":"").
                append(": ").
                append (o.getSummary()).
                toString();
	}

}
