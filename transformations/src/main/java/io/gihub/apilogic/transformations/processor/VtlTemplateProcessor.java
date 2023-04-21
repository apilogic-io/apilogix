package io.gihub.apilogic.transformations.processor;

import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.Map;

public class VtlTemplateProcessor extends Processor<Map<String, Object>, String> {

    private final Path template;
    public VtlTemplateProcessor(Path template) {
        this.template = template;
    }

    @SneakyThrows
    @Override
    public String process(Map<String, Object> input) {
        return getVtlResult(template, input);
    }
}
