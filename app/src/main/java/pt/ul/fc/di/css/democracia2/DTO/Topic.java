package pt.ul.fc.di.css.democracia2.DTO;

import java.util.Objects;

/**
 * Class that represents a Topic
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
public class Topic {

  private Long id;

  private Topic parent;

  /** Constructs a new Topic object */
  public Topic() {}

  /**
   * Method that gets the parent of the Topic
   *
   * @return the parent of the corresponding Topic
   */
  public Topic getParent() {
    return parent;
  }

  private String name;

  /**
   * Method that gets the name of the Topic
   *
   * @return the name of the corresponding Topic
   */
  public String getName() {
    return name;
  }

  /**
   * Constructs a new Topic object using a name
   *
   * @param name the name of the Topic
   */
  public Topic(String name) {
    this.name = name;
  }

  /**
   * Constructs a new Topic object using a name,parent
   *
   * @param name the name of the Topic
   * @param parent the parent of the Topic
   */
  public Topic(String name, Topic parent) {
    this.name = name;
    this.parent = parent;
  }

  @Override
  public String toString() {
    return parent + ">" + name;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, parent);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Topic other = (Topic) obj;
    return Objects.equals(name, other.name) && Objects.equals(parent, other.parent);
  }

  /**
   * Method that gets the id of the Topic
   *
   * @return the id of the corresponding Topic
   */
  public long getId() {
    return id;
  }

  /**
   * Method that sets the id of the Topic
   *
   * @param id the id of the corresponding Topic
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Method that sets the parent of the Topic
   *
   * @param parent the parent of the corresponding Topic
   */
  public void setParent(Topic parent) {
    this.parent = parent;
  }

  /**
   * Method that sets the name of the Topic
   *
   * @param name the name of the corresponding Topic
   */
  public void setName(String name) {
    this.name = name;
  }
}
