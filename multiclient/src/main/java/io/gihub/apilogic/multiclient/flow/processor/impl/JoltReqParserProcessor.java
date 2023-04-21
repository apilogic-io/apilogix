package io.gihub.apilogic.multiclient.flow.processor.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gihub.apilogic.multiclient.client.MultiClientEvent;
import io.gihub.apilogic.multiclient.flow.processor.FileTypeEnum;
import io.gihub.apilogic.transformations.processor.AbstractJoltParserProcessor;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.Map;

public class JoltReqParserProcessor<T> extends AbstractJoltParserProcessor<MultiClientEvent<T>, Map<String, Object>> {

    private ObjectMapper objectMapper = new ObjectMapper();
    public JoltReqParserProcessor(Path reqTransformations) {
        super(reqTransformations);
    }

    @SneakyThrows
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> process(MultiClientEvent<T> event) {
        Map<String, Object> input = objectMapper.convertValue(event, new TypeReference<>() {});
        var result = getJsonTransformation().transform(input);
        getContext().put(FileTypeEnum.REQ_TRANSFORMATION.getKey(), result);
        return (Map<String, Object>) result;
    }
}
