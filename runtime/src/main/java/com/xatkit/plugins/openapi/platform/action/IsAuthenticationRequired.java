package com.xatkit.plugins.openapi.platform.action;

import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.openapi.platform.OpenAPIPlatform;

import edu.uoc.som.openapi2.API;

public class IsAuthenticationRequired extends RuntimeAction<OpenAPIPlatform> {

    public IsAuthenticationRequired(OpenAPIPlatform platform, XatkitSession session) {
        super(platform, session);
    }

    @Override
    protected Object compute() throws Exception {
        API api = (API) session.get(OpenAPIPlatform.LOADED_API_KEY);
        return api.getSecurity();
    }
}