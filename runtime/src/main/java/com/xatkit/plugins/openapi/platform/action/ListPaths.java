package com.xatkit.plugins.openapi.platform.action;

import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.openapi.platform.OpenAPIPlatform;
import edu.uoc.som.openapi2.API;
import edu.uoc.som.openapi2.Path;

import java.util.List;
import java.util.stream.Collectors;

public class ListPaths extends RuntimeAction<OpenAPIPlatform> {

    public ListPaths(OpenAPIPlatform platform, XatkitSession session) {
        super(platform, session);
    }

    @Override
    protected Object compute() throws Exception {
        API api = runtimePlatform.getApi(session);
        List<String> relativePaths =  api.getPaths().stream().map(Path::getRelativePath).collect(Collectors.toList());
        return relativePaths;
    }
}
