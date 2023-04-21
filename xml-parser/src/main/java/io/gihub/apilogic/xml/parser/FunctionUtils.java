package io.gihub.apilogic.xml.parser;

import io.gihub.apilogic.xml.parser.config.PropertyOperation;
import io.gihub.apilogic.xml.parser.config.XmlPath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

/**
 * Util class
 */
public class FunctionUtils {

  /**
   *
   * @param url the path to file
   * @return returns the content of file as xml String
   * @throws IOException when read file fails
   */
  public static String getRequestXml(URL url) throws IOException {
    return readLineByLineJava8(url);
  }

  /**
   *
   * @param filePath the path to file
   * @return returns the content of file as xml String
   * @throws IOException when read file fails
   */
  private static String readLineByLineJava8(URL filePath) throws IOException {
    if (filePath == null) {
      throw new RuntimeException();
    }

    try (BufferedReader in = new BufferedReader(new InputStreamReader(filePath.openStream()))) {
      StringBuilder contentBuilder = new StringBuilder();
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        contentBuilder.append(inputLine).append("\n");
      }
      return contentBuilder.toString();
    }
  }

  /**
   *
   * @param rootXmlPath the root element of the xml
   * @param arrayRepresentation all the arrays in the xml
   * @param input the map value of already processed xml
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public static void represent(XmlPath rootXmlPath,
                               List<String> arrayRepresentation,
                               Map input) {
    boolean isArray = arrayRepresentation.stream().anyMatch(s -> s.equals(rootXmlPath.getPath()));
    input.putAll(rootXmlPath.getValues());
    if (isArray) {
      var arr = new ArrayList<>();
      input.put(rootXmlPath.getPropertyName(), arr);
      rootXmlPath.getChildren().forEach(ch -> {
        Map el = new HashMap();
        represent(ch, arrayRepresentation, el);
        if(!el.isEmpty()) {
          arr.add(el);
        }
      });
    } else {
      rootXmlPath.getChildren().forEach(ch -> {
        Map el = new HashMap();
        represent(ch, arrayRepresentation, el);
        if (el.get(ch.getPropertyName()) != null) {
          input.putAll(el);
        } else {
          Map res = new HashMap();
          res.put(ch.getPropertyName(), el);
          input.putAll(res);
        }
      });
    }
  }

  /**
   *
   * @param actualKey the xml key to be processed
   * @param propertyOperations the operations applied
   * @return the name of the property
   */
  public static String resolveThePropertyName(String actualKey, List<PropertyOperation> propertyOperations) {
    var property = actualKey;
    for (PropertyOperation propertyOperation: propertyOperations) {
      var regex = propertyOperation.getProperty();
      if(property.matches(regex)) {
        for (String operation: propertyOperation.getOperations()) {
          if(operation.contains("replace")) {
            var replace = replaceArgs(operation);
            property = property.replace(replace[0], replace[1].replace("'", ""));
          }
          if(operation.contains("camelize")) {
            property = property.substring(0, 1).toLowerCase(Locale.ROOT) + property.substring(1);
          }
          if(operation.contains("ignore")) {
            return null;
          }
        }
      }
    }
    return property;
  }

  /**
   *
   * @param replace replace sting regex with values
   * @return processed string
   */
  private static String[] replaceArgs(String replace) {
    replace = replace.replace("replace(", "").trim();
    replace = replace.replace(")", "").trim();
    return replace.split(",");
  }

}
