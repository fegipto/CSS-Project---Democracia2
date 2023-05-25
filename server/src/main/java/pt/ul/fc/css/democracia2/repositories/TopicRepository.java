package pt.ul.fc.css.democracia2.repositories;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pt.ul.fc.css.democracia2.domain.Topic;

/**
 * Interface that represents a TopicRepository
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
public interface TopicRepository extends JpaRepository<Topic, Long> {

  /**
   * Method that gets from the DataBase the Topic using a name
   *
   * @param string the name of the Topic to search for
   * @return the Topic corresponding to the name
   */
  public Optional<Topic> findByName(String string);

  /**
   * Method that gets from the DataBase all the Topics available
   *
   * @return the Topics available
   */
  @Query(value = "SELECT * FROM Topic", nativeQuery = true)
  public ArrayList<Topic> getTopics();
}
