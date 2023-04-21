package io.gihub.apilogic.xml.parser.config;

import java.util.List;

/**
 * Configuration class
 */
public class PropertyOperation {

  /**
   * property
   */
  private String property;


  /**
   * operations
   */
  private List<String> operations;

  /**
   * Default constructor
   */
  public PropertyOperation() {
  }

  /**
   * Constructor
   * @param property String
   * @param operations list of strings
   */
  public PropertyOperation(String property, List<String> operations) {
    this.property = property;
    this.operations = operations;
  }

  /**
   * @return getter for property
   */
  public String getProperty() {
    return property;
  }

  /**
   * @param property setter
   */
  public void setProperty(String property) {
    this.property = property;
  }

  /**
   * @return getter operations
   */
  public List<String> getOperations() {
    return operations;
  }

  /**
   * @param operations setter
   */
  public void setOperations(List<String> operations) {
    this.operations = operations;
  }
}
