package pt.ul.fc.css.democracia2.repositories;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pt.ul.fc.css.democracia2.domain.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {

  public Optional<Topic> findByName(String string);

  @Query(value = "SELECT * FROM Topic", nativeQuery = true)
  public ArrayList<Topic> getTopics();
}
