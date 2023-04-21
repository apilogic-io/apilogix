package io.gihub.apilogic.multiclient.flow.processor.impl;

import com.amazonaws.http.HttpMethodName;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gihub.apilogic.multiclient.client.RequestModel;
import io.gihub.apilogic.transformations.processor.Processor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
public class HttpClientProcessor extends Processor<RequestModel, RequestModel> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public RequestModel process(RequestModel input) {
        log.info("Invoking request {}, {}, {}, {}", input.getUrl(), input.getMethod(), input.getHttpHeaders(), input.getBody());
        var request = HttpRequest.newBuilder()
                .uri(new URI(input.getUrl()));
        if(input.getHttpHeaders() != null && !input.getHttpHeaders().isEmpty()) {
            input.getHttpHeaders().forEach(request::header);
        }
        if (HttpMethodName.POST.name().equals(input.getMethod())) {
            request.POST(getBody(input));
        } else if (HttpMethodName.GET.name().equals(input.getMethod())) {
            request.GET();
        }
        var client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request.build(), HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() / 100 != 2) {
            log.error("Failed to send request with response {}, {}", response.statusCode(), response.body());
            throw new RuntimeException(response.body());
        } else {
            log.info("Received response {}", response.body());
            input.setResponse(response.body());
            return input;
        }
    }

    @SneakyThrows
    private HttpRequest.BodyPublisher getBody(RequestModel requestModel) {
        if(requestModel.getData() != null && !requestModel.getData().isEmpty()) {
            return HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(requestModel.getData()));
        }
        else {
            return HttpRequest.BodyPublishers.ofString(requestModel.getBody());
        }
    }

}
