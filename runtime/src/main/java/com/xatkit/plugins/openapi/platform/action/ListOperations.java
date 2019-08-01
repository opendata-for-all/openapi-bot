package com.xatkit.plugins.openapi.platform.action;

import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.chat.platform.action.FormatList;
import com.xatkit.plugins.openapi.platform.OpenAPIPlatform;
import edu.uoc.som.openapi2.Operation;
import edu.uoc.som.openapi2.Path;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class ListOperations extends RuntimeAction<OpenAPIPlatform> {

    private int pathId;

    public ListOperations(OpenAPIPlatform platform, XatkitSession session, String pathIdentifier) {
        super(platform, session);
        pathId = Integer.parseInt(pathIdentifier);
    }

    @Override
    protected Object compute() throws Exception {
        List<Path> storedPaths = (List<Path>) session.get(FormatList.LAST_FORMATTED_LIST);
        Path path = storedPaths.get(pathId);
        List<Operation> operations = new ArrayList<>();
        if(nonNull(path.getGet())) {
            operations.add(path.getGet());
        }
        if(nonNull(path.getPost())) {
            operations.add(path.getPost());
        }
        if(nonNull(path.getDelete())) {
            operations.add(path.getDelete());
        }
        if(nonNull(path.getPatch())) {
            operations.add(path.getPatch());
        }
        if(nonNull(path.getOptions())) {
            operations.add(path.getOptions());
        }
        if(nonNull(path.getPut())) {
            operations.add(path.getPut());
        }
        if(nonNull(path.getHead())) {
            operations.add(path.getHead());
        }
        return operations;
    }

    private String createOperationDescription(Operation operation) {
        return operation.getHTTPMethod() + ": " + operation.getDescription();
    }
}
