package com.xatkit.plugins.openapi.platform.action;

import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.openapi.platform.OpenAPIPlatform;
import edu.uoc.som.openapi2.API;

public class GetNumberOfPaths extends RuntimeAction<OpenAPIPlatform> {

    public GetNumberOfPaths(OpenAPIPlatform platform, XatkitSession session) {
        super(platform, session);
    }

    @Override
    protected Object compute() throws Exception {
        API api = runtimePlatform.getApi(session);
        return Integer.toString(api.getPaths().size());
    }
}
