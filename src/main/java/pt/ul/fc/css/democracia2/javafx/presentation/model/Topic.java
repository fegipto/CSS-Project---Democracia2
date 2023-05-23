package pt.ul.fc.css.democracia2.javafx.presentation.model;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * Class that represents a Topic
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
@Entity
public class Topic {

  @Id @GeneratedValue() private Long id;

  @ManyToOne
  @JoinColumn(name = "parent_id")
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
  public Topic(@NonNull String name) {
    this.name = name;
  }

  /**
   * Constructs a new Topic object using a name,parent
   *
   * @param name the name of the Topic
   * @param parent the parent of the Topic
   */
  public Topic(@NonNull String name, Topic parent) {
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
}
