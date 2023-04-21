package io.gihub.apilogic.multiclient.flow;


import io.gihub.apilogic.multiclient.client.MultiClientEvent;
import io.gihub.apilogic.multiclient.flow.dispatcher.DataDispatcherFactory;
import io.gihub.apilogic.multiclient.flow.processor.FileTypeEnum;
import io.gihub.apilogic.transformations.processor.Pipeline;

import java.util.List;
import java.util.Map;

public class SoapMultiClientFlow<T> implements MultiClientFlow<T> {

    @Override
    public Map<String, Object> execute(List<DataDispatcherFactory.ClientRequestFile> files, MultiClientEvent<T> multiClientEvent) {
        var template = getFilePath(files, FileTypeEnum.TEMPLATE);
        var httpTemplate = getFilePath(files, FileTypeEnum.HTTP_TEMPLATE);
        var flatten = getFilePath(files, FileTypeEnum.FLATTEN);
        var reqTransformation = getFilePath(files, FileTypeEnum.REQ_TRANSFORMATION);
        var resTransformation = getFilePath(files, FileTypeEnum.RES_TRANSFORMATION);
        Pipeline<MultiClientEvent<T>, Map<String, Object>> pipeline = new Pipeline<>(reqJoltTransformStep(reqTransformation))
                .pipe(reqVtlParseStep(template))
                .pipe(reqVtlHttpClientStep(httpTemplate))
                .pipe(reqHttpClientStep())
                .pipe(reqXmlFlattenStep(flatten))
                .pipe(resJoltTransformStep(resTransformation, multiClientEvent));
        return pipeline.execute(multiClientEvent);
    }

}
