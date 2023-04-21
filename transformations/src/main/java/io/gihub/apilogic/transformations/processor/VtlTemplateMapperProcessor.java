package io.gihub.apilogic.transformations.processor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.Map;

@AllArgsConstructor
public class VtlTemplateMapperProcessor extends Processor<Map<String, Object>, Map<String, Object>> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Path template;
    @SneakyThrows
    @Override
    public Map<String, Object> process(Map<String, Object> input) {

        var result = getVtlResult(template, input);
        return objectMapper.readValue(result, new TypeReference<>() {});
    }
}
