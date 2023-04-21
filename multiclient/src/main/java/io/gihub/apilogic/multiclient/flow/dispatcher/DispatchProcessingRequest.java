package io.gihub.apilogic.multiclient.flow.dispatcher;

import io.gihub.apilogic.transformations.processor.RequestType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DispatchProcessingRequest {
    private String provider;
    private RequestType requestType;
    private List<DataDispatcherFactory.ClientRequestFile> clientFiles;
}
