package io.gihub.apilogic.multiclient.flow.processor.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.gihub.apilogic.multiclient.client.MultiClientEvent;
import io.gihub.apilogic.transformations.processor.AbstractJoltParserProcessor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.Map;

@Slf4j
public class JoltResParserProcessor<T> extends AbstractJoltParserProcessor<Map<String, Object>, Map<String, Object>> {

    private final MultiClientEvent<T> event;

    private final ObjectMapper objectMapper = new ObjectMapper();
    public JoltResParserProcessor(Path resTransformations, MultiClientEvent<T> event) {
        super(resTransformations);
        this.event = event;
    }

    @SneakyThrows
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> process(Map<String, Object> input) {
        var contexMap = Map.of("request", event.getBody(), "response", input);
        log.info(String.format("Context Map: \n%s", objectMapper.writeValueAsString(contexMap)));
        Map<String, Object> transform = (Map<String, Object>) getJsonTransformation().transform(contexMap);
        log.info(String.format("After transformation: \n%s", objectMapper.writeValueAsString(transform)));
        return transform;
    }
}
