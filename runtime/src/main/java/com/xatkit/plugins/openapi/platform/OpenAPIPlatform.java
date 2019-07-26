package com.xatkit.plugins.openapi.platform;

import com.xatkit.core.XatkitCore;
import com.xatkit.core.platform.RuntimePlatform;
import com.xatkit.core.session.XatkitSession;
import edu.uoc.som.openapi2.API;
import org.apache.commons.configuration2.Configuration;

public class OpenAPIPlatform extends RuntimePlatform {

    public static final String LOADED_API_KEY = "xatkit.plugins.openapi.api";

    public OpenAPIPlatform(XatkitCore xatkitCore, Configuration configuration) {
        super(xatkitCore, configuration);
    }

    public API getApi(XatkitSession session) {
        return (API) session.get(LOADED_API_KEY);
    }
}
