package io.gihub.apilogic.multiclient.flow;

import io.gihub.apilogic.multiclient.client.MultiClientEvent;
import io.gihub.apilogic.multiclient.flow.dispatcher.DataDispatcherFactory;
import io.gihub.apilogic.multiclient.flow.processor.FileTypeEnum;
import io.gihub.apilogic.transformations.processor.Pipeline;
import java.util.List;
import java.util.Map;

public class RestMultiClientFlow<T> implements MultiClientFlow<T> {

    @Override
    public Map<String, Object> execute(List<DataDispatcherFactory.ClientRequestFile> files, MultiClientEvent<T> multiClientEvent) {
        var httpTemplate = getFilePath(files, FileTypeEnum.HTTP_TEMPLATE);
        var httpAuthTemplate = getFilePath(files, FileTypeEnum.HTTP_AUTH_TEMPLATE);
        var reqTransformation = getFilePath(files, FileTypeEnum.REQ_TRANSFORMATION);
        var resTransformation = getFilePath(files, FileTypeEnum.RES_TRANSFORMATION);
        Pipeline<MultiClientEvent<T>, Map<String, Object>> pipeline = new Pipeline<>(reqJoltTransformStep(reqTransformation))
                .pipe(reqVtlRestHttpClientStep(httpAuthTemplate))
                .pipe(reqHttpClientStep())
                .pipe(resAdapterClientStep())
                .pipe(reqVtlRestHttpClientStep(httpTemplate))
                .pipe(reqHttpClientStep())
                .pipe(resAdapterClientStep())
                .pipe(resJoltTransformStep(resTransformation, multiClientEvent));
        return pipeline.execute(multiClientEvent);
    }
}
