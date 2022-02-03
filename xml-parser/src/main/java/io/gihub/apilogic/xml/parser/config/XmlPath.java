package io.gihub.apilogic.xml.parser.config;

import io.gihub.apilogic.xml.parser.FunctionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class XmlPath {
  private String currentPath;
  private String propertyName;
  private String path;
  private XmlPath parent;
  private Map<String, String> values = new HashMap<>();
  private List<XmlPath> siblings = new ArrayList<>();
  private List<XmlPath> children = new ArrayList<>();

  public XmlPath(String path,
                 ArrayDeque<XmlPath> tree,
                 List<Map<String, Integer>> move,
                 List<PropertyOperation> propertyOperations) {
    this.currentPath = path;
    init(tree, move, propertyOperations);
  }

  private void init(ArrayDeque<XmlPath> tree, List<Map<String, Integer>> moveList, List<PropertyOperation> propertyOperations) {
    String temp = tree.stream().map(XmlPath::getCurrentPath).collect(Collectors.joining("/"));
    Integer directionToMove = moveList.stream().filter(move -> move.entrySet().stream()
      .anyMatch(entry -> entry.getKey().equals(currentPath))
      ).findFirst().map(map -> map.get(currentPath)).orElse(0);
    if(temp.isEmpty()) {
      this.path = currentPath;
    }
    else {
      this.path = String.join("/", tree.stream().map(XmlPath::getCurrentPath).collect(Collectors.joining("/")), currentPath);
    }
    if(path.lastIndexOf('/') != -1) {
      String[] parentPaths = path.split("/");
      int index = parentPaths.length - 2 + directionToMove;
      List<String> parentPathList = new ArrayList<>(Arrays.asList(parentPaths).subList(0, index + 1));
      String parentPath = String.join("/", parentPathList);
      tree.stream().filter(xmlPath -> xmlPath.getPath().equals(parentPath)).findFirst().ifPresent(xmlPath -> {
        this.setParent(xmlPath);
        xmlPath.getChildren().add(this);
      });
    }
    this.propertyName = FunctionUtils.resolveThePropertyName(this.currentPath, propertyOperations);
  }

  public Map<String, String> getValues() {
    return values;
  }

  public void setValues(Map<String, String> values) {
    this.values = values;
  }

  public String getCurrentPath() {
    return currentPath;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public XmlPath getParent() {
    return parent;
  }

  public void setParent(XmlPath parent) {
    this.parent = parent;
  }

  public List<XmlPath> getSiblings() {
    return siblings;
  }

  public void setSiblings(List<XmlPath> siblings) {
    this.siblings = siblings;
  }

  public List<XmlPath> getChildren() {
    return children;
  }

  public void setChildren(List<XmlPath> children) {
    this.children = children;
  }

  public String getPropertyName() {
    return propertyName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    XmlPath xmlPath = (XmlPath) o;
    return path.equals(xmlPath.path);
  }

  @Override
  public int hashCode() {
    return Objects.hash(path);
  }
}
