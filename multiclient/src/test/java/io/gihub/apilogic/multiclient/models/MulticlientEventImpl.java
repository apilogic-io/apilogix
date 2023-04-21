package io.gihub.apilogic.multiclient.models;

import io.gihub.apilogic.multiclient.client.AuthorizableEventBody;
import io.gihub.apilogic.multiclient.client.MultiClientEvent;
import lombok.Data;

import java.util.List;

@Data
public class MulticlientEventImpl<T> implements MultiClientEvent<T> {

    private List<String> events;
    private AuthorizableEventBody<T> body;

}
