package io.gihub.apilogic.xml.parser;

import io.gihub.apilogic.xml.parser.config.PropertyOperation;
import io.gihub.apilogic.xml.parser.config.XmlPath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class FunctionUtils {

  public static String getRequestXml(URL url) throws IOException {
    return readLineByLineJava8(url);
  }

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

  private static String[] replaceArgs(String replace) {
    replace = replace.replace("replace(", "").trim();
    replace = replace.replace(")", "").trim();
    return replace.split(",");
  }

}
