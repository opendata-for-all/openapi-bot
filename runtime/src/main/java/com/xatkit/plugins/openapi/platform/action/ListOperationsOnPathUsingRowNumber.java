package com.xatkit.plugins.openapi.platform.action;

import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.chat.platform.action.FormatList;
import com.xatkit.plugins.openapi.platform.OpenAPIPlatform;

import edu.uoc.som.openapi2.Operation;
import edu.uoc.som.openapi2.Path;
import edu.uoc.som.openapi2.commons.OpenAPIUtils;

import java.util.List;

public class ListOperationsOnPathUsingRowNumber extends RuntimeAction<OpenAPIPlatform> {

	private int pathId;

	public ListOperationsOnPathUsingRowNumber(OpenAPIPlatform platform, XatkitSession session, String pathIdentifier) {
		super(platform, session);
		pathId = Integer.parseInt(pathIdentifier);
	}

	@Override
	protected List<Operation> compute() throws Exception {
		List<Path> storedPaths = (List<Path>) session.get(FormatList.LAST_FORMATTED_LIST);
		Path path = storedPaths.get(pathId);
		return  OpenAPIUtils.getAllOperationsOnPath(path);
	}

}
