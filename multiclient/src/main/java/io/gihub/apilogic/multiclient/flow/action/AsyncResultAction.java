package io.gihub.apilogic.multiclient.flow.action;

import io.gihub.apilogic.multiclient.client.MultiClientEvent;

import java.util.Map;

public class AsyncResultAction<T> implements ResultAction<T> {

    private final AsyncResultActionDelegate<T> asyncResultActionDelegate;

    public AsyncResultAction(AsyncResultActionDelegate<T> asyncResultActionDelegate) {
        this.asyncResultActionDelegate = asyncResultActionDelegate;
    }

    @Override
    public void execute(MultiClientEvent<T> initialEvent, Map<String, Object> result) {
        asyncResultActionDelegate.sendMessage(initialEvent, result);
    }
}
