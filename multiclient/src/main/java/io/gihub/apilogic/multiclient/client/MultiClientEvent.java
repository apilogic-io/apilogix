package io.gihub.apilogic.multiclient.client;

import java.util.List;
import java.util.Map;

public interface MultiClientEvent<T> {
    List<String> getEvents();
    EventBody<T> getBody();
}
