package io.gihub.apilogic.multiclient.client;

import java.util.Map;

public interface AuthorizableEventBody<T> extends EventBody<T> {
    Map<String, Object> getAuthorization();
}
