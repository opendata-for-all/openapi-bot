package com.xatkit.plugins.openapi.platform;

import com.xatkit.core.XatkitCore;
import com.xatkit.core.platform.RuntimePlatform;
import com.xatkit.core.session.XatkitSession;
import edu.uoc.som.openapi2.API;
import edu.uoc.som.openapi2.Operation;
import edu.uoc.som.openapi2.Path;
import org.apache.commons.configuration2.Configuration;

public class OpenAPIPlatform extends RuntimePlatform {

    public static final String LOADED_API_KEY = "xatkit.plugins.openapi.api";

    public OpenAPIPlatform(XatkitCore xatkitCore, Configuration configuration) {
        super(xatkitCore, configuration);
        this.xatkitCore.getFormatter("Default").registerFormatFunction(Path.class, Path::getRelativePath);
        this.xatkitCore.getFormatter("Default").registerFormatFunction(Operation.class,
                o -> o.getHTTPMethod() + ": " + o.getSummary());
    }

    public API getApi(XatkitSession session) {
        return (API) session.get(LOADED_API_KEY);
    }
}
