package io.gihub.apilogic.multiclient.flow.dispatcher;

import io.gihub.apilogic.multiclient.client.MultiClientEvent;
import io.gihub.apilogic.multiclient.flow.RestMultiClientFlow;
import io.gihub.apilogic.multiclient.flow.SoapMultiClientFlow;
import io.gihub.apilogic.multiclient.flow.action.AsyncResultAction;
import io.gihub.apilogic.multiclient.flow.processor.FileTypeEnum;
import io.gihub.apilogic.transformations.processor.FileUtils;
import io.gihub.apilogic.transformations.processor.RequestType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor
public class DataDispatcherFactory<T> {

    private final SoapMultiClientFlow<T> soapMultiClientFlow = new SoapMultiClientFlow<>();
    private final RestMultiClientFlow<T> restMultiClientFlow = new RestMultiClientFlow<>();
    private AsyncResultAction<T> asyncResultAction;

    public DataDispatcherFactory(AsyncResultAction<T> action) {
        this.asyncResultAction = action;
    }

    @SneakyThrows
    public Map<String, Object> dispatch(MultiClientEvent<T> multiclientEvent) {
        var clientConfigs = getClientConfigs(multiclientEvent);
        return clientConfigs.parallelStream().collect(Collectors.toMap(DispatchProcessingRequest::getProvider, config -> {
            var requestType = config.getRequestType();
            Map<String, Object> result;
            if (requestType.equals(RequestType.SOAP)) {
                result =  soapMultiClientFlow.execute(config.getClientFiles(), multiclientEvent);
            } else {
                result =  restMultiClientFlow.execute(config.getClientFiles(), multiclientEvent);
            }
            if(asyncResultAction != null) {
                asyncResultAction.execute(multiclientEvent, result);
            }
            return result;
        }));
    }

    private List<DispatchProcessingRequest> getClientConfigs(MultiClientEvent<T> multiclientEvent) {
        return multiclientEvent.getEvents().stream().map(event -> {
            var files = clientRequestFiles(event);
            boolean isSoap = files.stream().anyMatch(file -> file.getKey().equals(FileTypeEnum.TEMPLATE));
            var reqType = isSoap ? RequestType.SOAP : RequestType.REST;
            return new DispatchProcessingRequest(event, reqType, files);
        }).collect(Collectors.toList());

    }

    @SneakyThrows
    private List<ClientRequestFile> clientRequestFiles(String path) {
        Path myPath = FileUtils.getFilePath(path);
        try (Stream<Path> walk = Files.walk(myPath)) {
            return walk
                    .map(input -> {
                        if (!input.toString().equals(myPath.toString())) {
                            var file = input.getFileName().toString().split("\\.")[0];
                            var name = FileTypeEnum.getByKey(file);
                            return new ClientRequestFile(name, input);
                        }
                        return null;
                    }).filter(Objects::nonNull).collect(Collectors.toList());
        }
    }


    @Data
    @AllArgsConstructor
    public static class ClientRequestFile {
        private FileTypeEnum key;
        private Path filePath;
    }


}
