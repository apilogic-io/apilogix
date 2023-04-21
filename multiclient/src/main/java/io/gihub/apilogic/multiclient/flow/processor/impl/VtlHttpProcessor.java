package io.gihub.apilogic.multiclient.flow.processor.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.gihub.apilogic.multiclient.client.RequestModel;
import io.gihub.apilogic.transformations.processor.Processor;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.Collections;

public class VtlHttpProcessor extends Processor<String, RequestModel> {

    private final Path template;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public VtlHttpProcessor(Path template) {
        this.template = template;
    }

    @SneakyThrows
    @Override
    public RequestModel process(String body) {
        var result = getVtlResult(template, Collections.emptyMap());
        var reqModel = objectMapper.readValue(result, RequestModel.class);
        reqModel.setBody(body);
        return reqModel;
    }
}
