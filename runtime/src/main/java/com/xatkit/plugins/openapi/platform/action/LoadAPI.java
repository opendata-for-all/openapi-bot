package com.xatkit.plugins.openapi.platform.action;

import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.openapi.platform.OpenAPIPlatform;
import edu.uoc.som.openapi2.API;
import edu.uoc.som.openapi2.io.OpenAPI2Builder;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class LoadAPI extends RuntimeAction<OpenAPIPlatform> {

    private String url;

    public LoadAPI(OpenAPIPlatform platform, XatkitSession session, String url) {
        super(platform, session);
        this.url = url;
    }

    @Override
    protected Object compute() throws Exception {
        BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder buffer = new StringBuilder();
        while (reader.ready()) {
            buffer.append(reader.readLine());
        }
        API api = new OpenAPI2Builder().fromText(buffer.toString());
        session.store(OpenAPIPlatform.LOADED_API_KEY, api);
        return api;
    }
}
