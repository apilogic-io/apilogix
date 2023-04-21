package io.gihub.apilogic.multiclient.flow.processor.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.gihub.apilogic.multiclient.client.RequestModel;
import io.gihub.apilogic.transformations.processor.Processor;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

public class VtlRestHttpProcessor extends Processor<Map<String, Object>, RequestModel> {

    private final Path template;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public VtlRestHttpProcessor(Path template) {
        this.template = template;
    }

    @SneakyThrows
    @Override
    public RequestModel process(Map<String, Object> body) {
        if(template == null) {
            return null;
        }
        else {
            var result = getVtlResult(template, Collections.emptyMap());
            var reqModel = objectMapper.readValue(result, RequestModel.class);
            reqModel.setBody(objectMapper.writeValueAsString(body));
            return reqModel;
        }
    }
}
