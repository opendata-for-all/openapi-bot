package com.xatkit.plugins.openapi.platform.action;

import java.util.List;

import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.openapi.platform.OpenAPIPlatform;
import edu.uoc.som.openapi2.API;
import edu.uoc.som.openapi2.Path;

public class ListPaths extends RuntimeAction<OpenAPIPlatform> {

    public ListPaths(OpenAPIPlatform platform, XatkitSession session) {
        super(platform, session);
    }

    @Override
    protected List<Path> compute() throws Exception {
        API api = runtimePlatform.getApi(session);
        return api.getPaths();
    }
}
