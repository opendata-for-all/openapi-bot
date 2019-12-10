package com.xatkit.plugins.openapi.platform;

import java.util.Map;

import org.apache.commons.configuration2.Configuration;
import com.xatkit.core.XatkitCore;
import com.xatkit.core.platform.RuntimePlatform;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.openapi.platform.utils.FormattingUtils;

import edu.uoc.som.openapi2.API;
import edu.uoc.som.openapi2.Operation;
import edu.uoc.som.openapi2.Parameter;
import edu.uoc.som.openapi2.Path;
import edu.uoc.som.openapi2.Property;
import edu.uoc.som.openapi2.commons.OpenAPIUtils;
import edu.uoc.som.openapi2.mapping.PropertyToParameter;
import edu.uoc.som.openapi2.mapping.PropertyToProperty;

/**
 * A {@link RuntimePlatform} class that interacts with an OpenAPI definition.
 *
 */
public class OpenAPIPlatform extends RuntimePlatform {

    public static final String LOADED_API_KEY = "xatkit.plugins.openapi.api";
    public static final String PATH_IN_CONTEXT = "xatkit.plugins.openapi.path";

    
    
    /**
     * Constructs a new {@link RuntimePlatform} from the provided {@link XatkitCore} and {@link Configuration}.
     * <p>
     * This constructor defines the formatters for the OpenAPI elements.
     * 
     * @param xatkitCore	the {@link XatkitCore} instance associated to this runtimePlatform
     * @param configuration	the {@link Configuration} used to retrieve the OpenAPI properties
     * @throws NullPointerException     if the provided {@code xatkitCore} or {@code configuration} is {@code null}
     */
    public OpenAPIPlatform(XatkitCore xatkitCore, Configuration configuration) {
        super(xatkitCore, configuration);
        
        //We register here some formatters. We may need to move this to another place
        this.xatkitCore.getFormatter("Default").registerFormatFunction(Path.class, Path::getRelativePath);
        this.xatkitCore.getFormatter("Default").registerFormatFunction(Operation.class,o -> FormattingUtils.formatOperation(o));
        this.xatkitCore.getFormatter("Default").registerFormatFunction(Property.class, p -> new StringBuffer().append(p.getName())
       		 .append(p.getRequired()?"(required)":"")
       		 .toString());
        
        this.xatkitCore.getFormatter("Default").registerFormatFunction(Map.Entry.class, e -> e.getKey()+"");
        this.xatkitCore.getFormatter("Default").registerFormatFunction(Property.class, p -> p.getName());
        this.xatkitCore.getFormatter("Default").registerFormatFunction(Parameter.class,
       		 p -> p.getName());
        this.xatkitCore.getFormatter("Default").registerFormatFunction(PropertyToParameter.class, p -> p.getSource().getName()+": the parameter "+p.getTarget().getName()+" of the operation "+ FormattingUtils.formatOperation(OpenAPIUtils.getOperation(p.getTarget())));
        this.xatkitCore.getFormatter("Default").registerFormatFunction(PropertyToProperty.class, p -> p.getSource().getName()+": the property "+p.getTarget().getName()+" of the definition "+ OpenAPIUtils.getDefinition(p.getTarget()).getName());
    }
    

    
   
    /**
     * Returns the loaded {@link API}
     * 
     * @param session	the {@link XatkitSession} instance 
     * @return	the loaded {@link API}
     */
    public API getApi(XatkitSession session) {
        return (API) session.get(LOADED_API_KEY);
    }
    
}
