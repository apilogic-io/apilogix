package io.gihub.apilogic.xml.parser;


import io.gihub.apilogic.xml.parser.config.PropertyOperation;
import io.gihub.apilogic.xml.parser.config.XmlParserConfig;
import io.gihub.apilogic.xml.parser.config.XmlPath;
import org.yaml.snakeyaml.Yaml;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static io.gihub.apilogic.xml.parser.FunctionUtils.*;

public class XmlParser {

  public static Map<String, Object> parse(File xmlFile, URL config) throws IOException, XMLStreamException {
    var xmlParserConfig = new Yaml().loadAs(config.openStream(), XmlParserConfig.class);
    var stream = new FileInputStream(xmlFile);
    return getStringObjectMap(xmlParserConfig, stream);
  }

  public static Map<String, Object> parse(InputStream xmlStream, URL config) throws IOException, XMLStreamException {
    var xmlParserConfig = new Yaml().loadAs(config.openStream(), XmlParserConfig.class);
    return getStringObjectMap(xmlParserConfig, xmlStream);
  }

  public static Map<String, Object> parse(String xml, URL config) throws IOException, XMLStreamException {
    var xmlParserConfig = new Yaml().loadAs(config.openStream(), XmlParserConfig.class);
    return xmlParser(xml, xmlParserConfig);
  }

  public static Map<String, Object> parse(URL xmlUrl, URL config) throws IOException, XMLStreamException {
    var xml = getRequestXml(xmlUrl);
    var xmlParserConfig = new Yaml().loadAs(config.openStream(), XmlParserConfig.class);
    return xmlParser(xml, xmlParserConfig);
  }

  public static Map<String, Object> xmlParser(InputStream inputStream, XmlParserConfig xmlParserConfig) throws XMLStreamException {
    return getStringObjectMap(xmlParserConfig, inputStream);
  }

  private static Map<String, Object> xmlParser(String xml, XmlParserConfig xmlParserConfig) throws XMLStreamException {
    byte[] byteArray = xml.getBytes(StandardCharsets.UTF_8);
    var inputStream = new ByteArrayInputStream(byteArray);
    return getStringObjectMap(xmlParserConfig, inputStream);
  }

  private static Map<String, Object> getStringObjectMap(XmlParserConfig xmlParserConfig, InputStream inputStream) throws XMLStreamException {
    var xmlInputFactory = XMLInputFactory.newInstance();
    var reader = xmlInputFactory.createXMLEventReader(inputStream);
    ArrayDeque<XmlPath> path = new ArrayDeque<>();
    Map<String, Object> jsonMapping = new HashMap<>();
    while (reader.hasNext()) {
      XMLEvent nextEvent = reader.nextEvent();
      if (nextEvent.isStartElement()) {
        extracted(reader, path, xmlParserConfig.getMove(), xmlParserConfig.getPropertyOperations(), nextEvent);
      }
      if (nextEvent.isEndElement()) {
        EndElement elementElement = nextEvent.asEndElement();
        if(elementElement.getName().getLocalPart().equals(xmlParserConfig.getContextPath())) {
          represent(path.getLast(), xmlParserConfig.getArrayPaths(), jsonMapping);
        }
        path.removeLast();
      }
    }
    return jsonMapping;
  }

  private static void extracted(XMLEventReader reader,
                                ArrayDeque<XmlPath> path,
                                List<Map<String, Integer>> move,
                                List<PropertyOperation> propertyOperations,
                                XMLEvent nextEvent) {
    StartElement startElement = nextEvent.asStartElement();
    XmlPath xmlPath = new XmlPath(startElement.getName().getLocalPart(), path, move, propertyOperations);
    path.addLast(xmlPath);
    Map<String, String> values = getValues(reader, startElement, path, move, propertyOperations);
    xmlPath.setValues(values);
  }

  private static Map<String, String> getValues(XMLEventReader reader,
                                               StartElement startElement,
                                               ArrayDeque<XmlPath> path,
                                               List<Map<String, Integer>> move,
                                               List<PropertyOperation> propertyOperations
                                               ) {
    Map<String, String> attrValues = getAttributesValues(startElement, propertyOperations);
    XMLEvent next;
    try {
      next = reader.nextEvent();
      if (next.isCharacters()) {
        String value = String.valueOf(next.asCharacters().getData());
        String attrKey = startElement.getName().getLocalPart();
        if (isaValueNode(value.replaceAll("\\s+", ""))) {
          attrKey = resolveThePropertyName(attrKey, propertyOperations);
          if(attrKey != null) {
            attrValues.put(attrKey, value);
          }
        }
      } else {
        if(next.isStartElement()) {
          extracted(reader, path, move, propertyOperations, next);
        }
        else if (next.isEndElement()) {
          if (!path.isEmpty()) {
            path.removeLast();
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return attrValues;
  }

  private static Map<String, String> getAttributesValues(StartElement startElement, List<PropertyOperation> propertyOperations) {
    Map<String, String> attributeValues = new HashMap<>();
    Iterator<Attribute> attributeIterator = startElement.getAttributes();
    while (attributeIterator.hasNext()) {
      Attribute attribute = attributeIterator.next();
      var key = resolveThePropertyName(attribute.getName().getLocalPart(), propertyOperations);
      if(key != null) {
        attributeValues.put(key, attribute.getValue());
      }
    }
    return attributeValues;
  }

  private static boolean isaValueNode(String temp) {
    return temp != null && !temp.isEmpty();
  }
}
