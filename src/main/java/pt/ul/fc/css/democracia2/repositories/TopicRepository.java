package pt.ul.fc.css.democracia2.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pt.ul.fc.css.democracia2.domain.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {

  @Query("SELECT t FROM Topic t")
  List<Topic> getTopics();
}
