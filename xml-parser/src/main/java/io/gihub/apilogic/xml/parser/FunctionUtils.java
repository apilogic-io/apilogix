package io.gihub.apilogic.xml.parser;

import io.gihub.apilogic.xml.parser.config.XmlPath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
      input.put(rootXmlPath.getCurrentPath(), arr);
      rootXmlPath.getChildren().forEach(ch -> {
        Map el = new HashMap();
        represent(ch, arrayRepresentation, el);
        arr.add(el);
      });
    } else {
      rootXmlPath.getChildren().forEach(ch -> {
        Map el = new HashMap();
        represent(ch, arrayRepresentation, el);
        if (el.get(ch.getCurrentPath()) != null) {
          input.putAll(el);
        } else {
          Map res = new HashMap();
          res.put(ch.getCurrentPath(), el);
          input.putAll(res);
        }
      });
    }
  }
}
