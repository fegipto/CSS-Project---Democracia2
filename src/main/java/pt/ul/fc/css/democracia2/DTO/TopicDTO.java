package pt.ul.fc.css.democracia2.DTO;

import pt.ul.fc.css.democracia2.domain.Topic;

/**
 * Class that represents a TopicDTO
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
public class TopicDTO {
  private Long id;
  private TopicDTO parent;
  private String name;

  /**
   * Constructs a new TopicDTO object using an id,parent,name
   *
   * @param id the id of the TopicDTO
   * @param parent the parent of the TopicDTO
   * @param name the name of the TopicDTO
   */
  public TopicDTO(Long id, TopicDTO parent, String name) {
    this.id = id;
    this.parent = parent;
    this.name = name;
  }

  /**
   * Constructs a new TopicDTO object using a name
   *
   * @param name the name of the TopicDTO
   */
  public TopicDTO(String name) {
    this.name = name;
  }

  /**
   * Constructs a new TopicDTO object using a topic
   *
   * @param topic the topic to use to create the DTO
   */
  public TopicDTO(Topic topic) {
    this.name = topic.getName();
    if (topic.getParent() != null) this.parent = new TopicDTO(topic.getParent());
  }

  /**
   * Method that gets the parent of the TopicDTO
   *
   * @return the parent of the corresponding TopicDTO
   */
  public TopicDTO getParent() {
    return parent;
  }

  /**
   * Method that gets the name of the TopicDTO
   *
   * @returnthe the name of the corresponding TopicDTO
   */
  public String getName() {
    return name;
  }

  /**
   * Method that gets the id of the TopicDTO
   *
   * @return the id of the corresponding TopicDTO
   */
  public Long getId() {
    return id;
  }
}
