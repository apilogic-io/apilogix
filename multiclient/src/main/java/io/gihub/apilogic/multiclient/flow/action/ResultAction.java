package io.gihub.apilogic.multiclient.flow.action;

import io.gihub.apilogic.multiclient.client.MultiClientEvent;

import java.util.Map;

public interface ResultAction<T> {

    void execute(MultiClientEvent<T> initialEvent, Map<String, Object> result);

}
