package io.gihub.apilogic.multiclient.flow;


import io.gihub.apilogic.multiclient.client.MultiClientEvent;
import io.gihub.apilogic.multiclient.client.RequestModel;
import io.gihub.apilogic.multiclient.flow.dispatcher.DataDispatcherFactory;
import io.gihub.apilogic.multiclient.flow.processor.FileTypeEnum;
import io.gihub.apilogic.multiclient.flow.processor.impl.*;
import io.gihub.apilogic.transformations.processor.ProcessorStep;
import io.gihub.apilogic.transformations.processor.Step;
import io.gihub.apilogic.transformations.processor.VtlTemplateMapperProcessor;
import io.gihub.apilogic.transformations.processor.VtlTemplateProcessor;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface MultiClientFlow<T> {
    Map<String, Object> execute(List<DataDispatcherFactory.ClientRequestFile> files, MultiClientEvent<T> body);


    default Step<RequestModel, RequestModel> reqHttpClientStep() {
        return new ProcessorStep<>(new HttpClientProcessor());
    }

    default Step<RequestModel, Map<String, Object>> reqXmlFlattenStep(Path flatten) {
        return new ProcessorStep<>(new XmlFlattenParserProcessor(flatten));
    }

    default Step<RequestModel, Map<String, Object>> resAdapterClientStep() {
        return new ProcessorStep<>(new ResAdapterClientProcessor());
    }

    default Step<MultiClientEvent<T>, Map<String, Object>> reqJoltTransformStep(Path path) {
        return new ProcessorStep<>(new JoltReqParserProcessor<>(path));
    }

    default Step<Map<String, Object>, Map<String, Object>> resJoltTransformStep(Path resTransformations, MultiClientEvent<T> multiClientEvent) {
        return new ProcessorStep<>(new JoltResParserProcessor<>(resTransformations, multiClientEvent));
    }

    default Path getFilePath(List<DataDispatcherFactory.ClientRequestFile> files, FileTypeEnum fileTypeEnum) {
        return files.stream().filter(file -> file.getKey().equals(fileTypeEnum)).findFirst().orElseThrow().getFilePath();
    }
    default Step<Map<String, Object>, String> reqVtlParseStep(Path template) {
        return new ProcessorStep<>(new VtlTemplateProcessor(template));
    }

    default Step<Map<String, Object>, Map<String, Object>> responseVtlParseStep(Path template) {
        return new ProcessorStep<>(new VtlTemplateMapperProcessor(template));
    }

    default Step<String, RequestModel> reqVtlHttpClientStep(Path template) {
        return new ProcessorStep<>(new VtlHttpProcessor(template));
    }

    default Step<Map<String, Object>, RequestModel> reqVtlRestHttpClientStep(Path template) {
        return new ProcessorStep<>(new VtlRestHttpProcessor(template));
    }

}
