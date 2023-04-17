package pt.ul.fc.css.democracia2.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.democracia2.domain.Delegate;

/**
 * Interface that represents a DelegateRepository
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
public interface DelegateRepository extends JpaRepository<Delegate, Long> {

  /**
   * Method that gets from the DataBase the Delegate using a name
   *
   * @param name the name of the Delegate to search for
   * @return the Delegate corresponding to the name
   */
  public Optional<Delegate> findByName(String name);

  /**
   * Method that gets from the DataBase the Delegate using a token
   *
   * @param token the token of the Delegate to search for
   * @return the Delegate corresponding to the token
   */
  public Optional<Delegate> findByToken(long token);
}
