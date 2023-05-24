package pt.ul.fc.css.democracia2.javafx.presentation.model;

import javafx.beans.property.*;

import java.util.Objects;

/**
 * Class that represents a Topic
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
public class Topic {
    private final LongProperty id = new SimpleLongProperty();

    public final LongProperty idProperty() {
      return this.id;
    }

    public final Long getId() {
      return this.idProperty().get();
    }

    public final void setId(final Long id) {
      this.idProperty().set(id);
    }

    private final ObjectProperty<Topic> parent = new SimpleObjectProperty<>();

    public final ObjectProperty<Topic> parentProperty() {
      return this.parent;
    }

    public final Topic getParent() {
      return this.parentProperty().get();
    }

    public final void setParent(final Topic parent) { this.parentProperty().set(parent); }

    private final StringProperty name = new SimpleStringProperty();

    public final StringProperty nameProperty() {
      return this.name;
    }

    public final String getName() {
      return this.nameProperty().get();
    }

    public final void setName(final String name) {
      this.nameProperty().set(name);
    }

    public Topic(Long id, String name, Topic parent) {
        setId(id);
        setName(name);
        setParent(parent);
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
    public final boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      Topic other = (Topic) obj;
      return Objects.equals(name, other.name) && Objects.equals(parent, other.parent);
    }
}
