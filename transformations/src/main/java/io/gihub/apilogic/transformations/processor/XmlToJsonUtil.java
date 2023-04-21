package io.gihub.apilogic.transformations.processor;

import io.gihub.apilogic.xml.parser.XmlParser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;

@Slf4j
public class XmlToJsonUtil {

    @SneakyThrows
    public Map<String, Object> parseXml(Path path, String xml) {
        var url = this.getClass().getResource(path.toString());
        if (url != null) {
            return XmlParser.parse(xml, url);
        } else {
            log.warn("Client configurations not found exception");
            throw new RuntimeException();
        }
    }

    @SneakyThrows
    public Map<String, Object> parseXmlFromPath(Path path, String xml) {
        var url = new File(path.toString()).toURI().toURL();
        return XmlParser.parse(xml, url);
    }


}
