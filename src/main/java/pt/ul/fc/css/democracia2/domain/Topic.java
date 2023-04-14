package pt.ul.fc.css.democracia2.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import org.springframework.lang.NonNull;

@Entity
public class Topic {

  @Id @GeneratedValue private Long id;

  @ManyToOne
  @JoinColumn(name = "parent_id")
  private Topic parent;

  public Topic() {}

  public Topic getParent() {
    return parent;
  }

  private String name;

  public String getName() {
    return name;
  }

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
}
