package io.gihub.apilogic.xml.parser.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XmlParserConfig {

  private String contextPath;
  private List<String> arrayPaths = new ArrayList<>();
  private List<Map<String, Integer>> move = new ArrayList<>();
  private List<PropertyOperation> propertyOperations = new ArrayList<>();

  public XmlParserConfig() {
  }

  public XmlParserConfig(String contextPath,
                         List<String> arrayPaths,
                         List<PropertyOperation> propertyOperations) {
    this.contextPath = contextPath;
    this.arrayPaths = arrayPaths;
    this.propertyOperations = propertyOperations;
  }

  public String getContextPath() {
    return contextPath;
  }

  public void setContextPath(String contextPath) {
    this.contextPath = contextPath;
  }

  public List<String> getArrayPaths() {
    return arrayPaths;
  }

  public void setArrayPaths(List<String> arrayPaths) {
    this.arrayPaths = arrayPaths;
  }

  public List<PropertyOperation> getPropertyOperations() {
    return propertyOperations;
  }

  public void setPropertyOperations(List<PropertyOperation> propertyOperations) {
    this.propertyOperations = propertyOperations;
  }

  public List<Map<String, Integer>> getMove() {
    return move;
  }

  public void setMove(List<Map<String, Integer>> move) {
    this.move = move;
  }
}
