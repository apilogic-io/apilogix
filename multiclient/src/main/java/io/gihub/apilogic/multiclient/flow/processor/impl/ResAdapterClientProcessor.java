package io.gihub.apilogic.multiclient.flow.processor.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gihub.apilogic.multiclient.client.RequestModel;
import io.gihub.apilogic.transformations.processor.Processor;
import lombok.SneakyThrows;

import java.util.Map;

public class ResAdapterClientProcessor extends Processor<RequestModel, Map<String, Object>> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @SneakyThrows
    @Override
    public Map<String, Object> process(RequestModel input) {
        Map<String, Object> response = objectMapper.readValue(input.getResponse(), new TypeReference<>() {});
        getContext().put(input.getId(), response);
        return response;
    }
}
