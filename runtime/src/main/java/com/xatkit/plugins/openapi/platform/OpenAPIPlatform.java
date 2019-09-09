package com.xatkit.plugins.openapi.platform;

import java.util.Map;

import org.apache.commons.configuration2.Configuration;

import com.xatkit.core.XatkitCore;
import com.xatkit.core.platform.Formatter;
import com.xatkit.core.platform.RuntimePlatform;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.openapi.platform.utils.FormatterUtils;

import edu.uoc.som.openapi2.API;
import edu.uoc.som.openapi2.Operation;
import edu.uoc.som.openapi2.Path;

public class OpenAPIPlatform extends RuntimePlatform {

    public static final String LOADED_API_KEY = "xatkit.plugins.openapi.api";

    public OpenAPIPlatform(XatkitCore xatkitCore, Configuration configuration) {
        super(xatkitCore, configuration);
       registerFormatters();
    }

    public API getApi(XatkitSession session) {
        return (API) session.get(LOADED_API_KEY);
    }
    
    private void registerFormatters() {
    	 this.xatkitCore.getFormatter("Default").registerFormatFunction(Path.class, Path::getRelativePath);
         this.xatkitCore.getFormatter("Default").registerFormatFunction(Operation.class,
                 o -> o.getHTTPMethod() + ": " + o.getSummary());
         this.xatkitCore.getFormatter("Default").registerFormatFunction(Map.Entry.class, e -> e.getKey()+"");
         Formatter formatter = new Formatter();
         this.xatkitCore.registerFormatter("Minimal", formatter);
         formatter.registerFormatFunction(Path.class,FormatterUtils::formatPath);
    }
   
}
