package io.gihub.apilogic.multiclient.models;

import io.gihub.apilogic.multiclient.client.AuthorizableEventBody;
import lombok.Data;

import java.util.Map;

@Data
public class AuthorizableEventBodyImpl<T> implements AuthorizableEventBody<T> {

    private Map<String, Object> authorization;
    private T data;

}
