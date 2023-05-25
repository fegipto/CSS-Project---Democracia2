package pt.ul.fc.css.democracia2.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.democracia2.domain.Citizen;

/**
 * Interface that represents a CitizenRepository
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
public interface CitizenRepository extends JpaRepository<Citizen, Long> {

  /**
   * Method that gets from the DataBase the Citizen using a name
   *
   * @param name the name of the Citizen to search for
   * @return the Citizen corresponding to the name
   */
  public Optional<Citizen> findByName(String name);

  /**
   * Method that gets from the DataBase the Citizen using a cc
   *
   * @param cc the cc of the Citizen to search for
   * @return the Citizen corresponding to the cc
   */
  public Optional<Citizen> findByCc(long cc);

  /**
   * Method that gets from the Citizen the Delegate using a token
   *
   * @param token the token of the Citizen to search for
   * @return the Citizen corresponding to the token
   */
  public Optional<Citizen> findByToken(long token);
}
