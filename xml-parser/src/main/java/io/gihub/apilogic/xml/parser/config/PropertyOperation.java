package io.gihub.apilogic.xml.parser.config;

import java.util.List;

public class PropertyOperation {

  private String property;
  private List<String> operations;

  public PropertyOperation() {
  }

  public PropertyOperation(String property, List<String> operations) {
    this.property = property;
    this.operations = operations;
  }

  public String getProperty() {
    return property;
  }

  public void setProperty(String property) {
    this.property = property;
  }

  public List<String> getOperations() {
    return operations;
  }

  public void setOperations(List<String> operations) {
    this.operations = operations;
  }
}
