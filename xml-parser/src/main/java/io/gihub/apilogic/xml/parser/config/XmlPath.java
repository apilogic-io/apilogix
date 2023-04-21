package io.gihub.apilogic.xml.parser.config;

import io.gihub.apilogic.xml.parser.FunctionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class to represent the processed paths tree
 */
public class XmlPath {
  /**
   * the currentPath
   */
  private String currentPath;
  /**
   * the propertyName
   */
  private String propertyName;
  /**
   * the path
   */
  private String path;
  /**
   * the parent
   */
  private XmlPath parent;
  /**
   * the path map values
   */
  private Map<String, String> values = new HashMap<>();
  /**
   * the path siblings
   */
  private List<XmlPath> siblings = new ArrayList<>();
  /**
   * the path children
   */
  private List<XmlPath> children = new ArrayList<>();

  /**
   * Constructor of the tree element
   * @param path the path
   * @param tree the tree
   * @param move configuration
   * @param propertyOperations configuration
   */
  public XmlPath(String path,
                 ArrayDeque<XmlPath> tree,
                 List<Map<String, Integer>> move,
                 List<PropertyOperation> propertyOperations) {
    this.currentPath = path;
    init(tree, move, propertyOperations);
  }

  /**
   *
   * @param tree the tree
   * @param moveList configuration
   * @param propertyOperations configuration
   */
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

  /**
   *
   * @return getter
   */
  public Map<String, String> getValues() {
    return values;
  }

  /**
   *
   * @param values setter
   */
  public void setValues(Map<String, String> values) {
    this.values = values;
  }

  /**
   *
   * @return getter
   */
  public String getCurrentPath() {
    return currentPath;
  }

  /**
   *
   * @return getter
   */
  public String getPath() {
    return path;
  }

  /**
   *
   * @param path setter
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   *
   * @return getter
   */
  public XmlPath getParent() {
    return parent;
  }

  /**
   *
   * @param parent setter
   */
  public void setParent(XmlPath parent) {
    this.parent = parent;
  }

  /**
   *
   * @return getter
   */
  public List<XmlPath> getSiblings() {
    return siblings;
  }

  /**
   *
   * @param siblings setter
   */
  public void setSiblings(List<XmlPath> siblings) {
    this.siblings = siblings;
  }

  /**
   *
   * @return getter
   */
  public List<XmlPath> getChildren() {
    return children;
  }

  /**
   *
   * @param children setter
   */
  public void setChildren(List<XmlPath> children) {
    this.children = children;
  }

  /**
   *
   * @return getter
   */
  public String getPropertyName() {
    return propertyName;
  }

  /**
   *
   * @param o object
   * @return boolean
   */
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
