package pt.ul.fc.css.democracia2.DTO;

import pt.ul.fc.css.democracia2.domain.Topic;

import java.util.Objects;

public class TopicDTO {
    private Long id;
    private TopicDTO parent;
    private String name;

    public TopicDTO() {
        // No-argument constructor
    }

    public TopicDTO(String name) {
        this.name = name;
    }

    public TopicDTO(Topic topic) {
        this.name = topic.getName();
        if (topic.getParent() != null)
            this.parent = new TopicDTO(topic.getParent());
    }

    public TopicDTO getParent() {
        return parent;
    }

    public String getName() {
        return name;
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
        TopicDTO other = (TopicDTO) obj;
        return Objects.equals(name, other.name) && Objects.equals(parent, other.parent);
    }
}
