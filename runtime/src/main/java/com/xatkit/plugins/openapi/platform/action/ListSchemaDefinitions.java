package com.xatkit.plugins.openapi.platform.action;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.openapi.platform.OpenAPIPlatform;
import edu.uoc.som.openapi2.API;
import edu.uoc.som.openapi2.Schema;

public class ListSchemaDefinitions extends RuntimeAction<OpenAPIPlatform> {

    public ListSchemaDefinitions(OpenAPIPlatform platform, XatkitSession session) {
        super(platform, session);
    }

    @Override
    protected List<Map.Entry<String,Schema>> compute() throws Exception {
        API api = runtimePlatform.getApi(session);
        return api.getDefinitions().entrySet().stream().collect(Collectors.toList());
    }
}
