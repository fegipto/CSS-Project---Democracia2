package pt.ul.fc.css.democracia2.DTO;

import pt.ul.fc.css.democracia2.domain.Topic;

public class TopicDTO {
  private Long id;
  private TopicDTO parent;
  private String name;

  public TopicDTO(Long id, TopicDTO parent, String name) {
    this.id = id;
    this.parent = parent;
    this.name = name;
  }

  public TopicDTO(String name) {
    this.name = name;
  }

  public TopicDTO(Topic topic) {
    this.name = topic.getName();
    if (topic.getParent() != null) this.parent = new TopicDTO(topic.getParent());
  }

  public TopicDTO getParent() {
    return parent;
  }

  public String getName() {
    return name;
  }

  public Long getId() {
    return id;
  }
}
