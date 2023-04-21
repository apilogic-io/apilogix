package io.gihub.apilogic.xml.parser.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Xml parser configurations
 */
public class XmlParserConfig {

  /**
   * the context, the root of the xml
   */
  private String contextPath;

  /**
   * the path to array representation in xml
   */
  private List<String> arrayPaths = new ArrayList<>();

  /**
   * Move the elements in output map representation
   */
  private List<Map<String, Integer>> move = new ArrayList<>();

  /**
   * extra property operations
   */
  private List<PropertyOperation> propertyOperations = new ArrayList<>();

  /**
   * default constructor
   */
  public XmlParserConfig() {
  }

  /**
   *
   * @param contextPath the context path
   * @param arrayPaths the array path
   * @param propertyOperations the property operations
   */
  public XmlParserConfig(String contextPath,
                         List<String> arrayPaths,
                         List<PropertyOperation> propertyOperations) {
    this.contextPath = contextPath;
    this.arrayPaths = arrayPaths;
    this.propertyOperations = propertyOperations;
  }

  /**
   *
   * @return getter
   */
  public String getContextPath() {
    return contextPath;
  }

  /**
   *
   * @param contextPath setter
   */
  public void setContextPath(String contextPath) {
    this.contextPath = contextPath;
  }

  /**
   *
   * @return getter
   */
  public List<String> getArrayPaths() {
    return arrayPaths;
  }

  /**
   *
   * @param arrayPaths setter
   */
  public void setArrayPaths(List<String> arrayPaths) {
    this.arrayPaths = arrayPaths;
  }

  /**
   *
   * @return getter
   */
  public List<PropertyOperation> getPropertyOperations() {
    return propertyOperations;
  }

  /**
   *
   * @param propertyOperations setter
   */
  public void setPropertyOperations(List<PropertyOperation> propertyOperations) {
    this.propertyOperations = propertyOperations;
  }

  /**
   *
   * @return getter
   */
  public List<Map<String, Integer>> getMove() {
    return move;
  }

  /**
   *
   * @param move setter
   */
  public void setMove(List<Map<String, Integer>> move) {
    this.move = move;
  }
}
