package io.gihub.apilogic.multiclient.flow.action;

import io.gihub.apilogic.multiclient.client.MultiClientEvent;

import java.util.Map;

public interface AsyncResultActionDelegate<T> {

    void sendMessage(MultiClientEvent<T> initialEvent, Map<String, Object> message);

}
