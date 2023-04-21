package io.gihub.apilogic.multiclient.flow.processor.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.gihub.apilogic.multiclient.client.RequestModel;
import io.gihub.apilogic.transformations.processor.Processor;
import io.gihub.apilogic.transformations.processor.XmlToJsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.Map;

@Slf4j
public class XmlFlattenParserProcessor extends Processor<RequestModel, Map<String, Object>> {

    private final Path flatten;
    private final XmlToJsonUtil xmlToJsonUtil = new XmlToJsonUtil();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public XmlFlattenParserProcessor(Path flatten) {
        this.flatten = flatten;
    }

    @Override
    public Map<String, Object> process(RequestModel input) {
        try {
            Map<String, Object> response = xmlToJsonUtil.parseXml(flatten, input.getResponse());
            log.info(objectMapper.writeValueAsString(response));
            return response;
        } catch (Exception e) {
            log.error(e.getMessage());
            return xmlToJsonUtil.parseXmlFromPath(flatten, input.getResponse());
        }
    }
}
