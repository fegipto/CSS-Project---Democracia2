package pt.ul.fc.css.democracia2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pt.ul.fc.css.democracia2.domain.Topic;

import java.util.ArrayList;

public interface TopicRepository extends JpaRepository<Topic, Long> {

  public Topic findByName(String string);

  @Query(value = "SELECT * FROM Topic", nativeQuery = true)
  public ArrayList<Topic> getTopics();
}
