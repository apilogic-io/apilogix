package io.gihub.apilogic.xml.parser.config;

public class YamlPathConfig {

  public YamlPathConfig(String path, Object array) {
    this.path = path;
    if(array != null) {
      this.array = Boolean.parseBoolean(array.toString());
    }
  }

  private String path;
  private boolean array;

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public boolean isArray() {
    return array;
  }

  public void setArray(boolean array) {
    this.array = array;
  }
}
