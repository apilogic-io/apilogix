package io.gihub.apilogic.multiclient.client;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RequestModel {
    private String url;
    private String id;
    private String method;
    private String body;
    private Map<String, Object> data;
    private Map<String, String> httpHeaders;
    private List<Map<String, Object>> multipartData;

    private String response;

}
