package com.xatkit.plugins.openapi.platform;

import java.util.Map;

import org.apache.commons.configuration2.Configuration;

import com.xatkit.core.XatkitCore;
import com.xatkit.core.platform.RuntimePlatform;
import com.xatkit.core.session.XatkitSession;

import edu.uoc.som.openapi2.API;
import edu.uoc.som.openapi2.Operation;
import edu.uoc.som.openapi2.Path;

public class OpenAPIPlatform extends RuntimePlatform {

    public static final String LOADED_API_KEY = "xatkit.plugins.openapi.api";

    public OpenAPIPlatform(XatkitCore xatkitCore, Configuration configuration) {
        super(xatkitCore, configuration);
        this.xatkitCore.getFormatter("Default").registerFormatFunction(Path.class, Path::getRelativePath);
        this.xatkitCore.getFormatter("Default").registerFormatFunction(Operation.class,
                o -> o.getHTTPMethod() + ": " + o.getSummary());
        this.xatkitCore.getFormatter("Default").registerFormatFunction(Map.Entry.class, e -> e.getKey()+"");
//        Formatter formatter = new Formatter();
//        this.xatkitCore.registerFormatter("LOL", formatter);
//        formatter.registerFormatFunction(Path.class, p -> "lol" + p.getRelativePath());
    }

    public API getApi(XatkitSession session) {
        return (API) session.get(LOADED_API_KEY);
    }
}
