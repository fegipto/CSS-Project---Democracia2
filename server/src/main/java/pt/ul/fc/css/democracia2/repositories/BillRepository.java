package pt.ul.fc.css.democracia2.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ul.fc.css.democracia2.domain.Bill;
import pt.ul.fc.css.democracia2.domain.BillStatus;

/**
 * Interface that represents a BillRepository
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
public interface BillRepository extends JpaRepository<Bill, Long> {

  /**
   * Method that gets from the DataBase the Bill using a title
   *
   * @param title the title of the Bill to search for
   * @return the Bill corresponding to the title
   */
  Optional<Bill> findByTitle(String title);

  /**
   * Method that gets from the DataBase all Bills in a certain status
   *
   * @param status the status of the Bills to search for
   * @return the Bills corresponding to the status
   */
  @Query("SELECT b FROM Bill b WHERE b.status = :status")
  List<Bill> getBillsByStatus(@Param("status") BillStatus status);

  /**
   * Method that gets from the DataBase all Bills not in a certain status
   *
   * @param status the status opposed to the status of the Bills to search for
   * @return the Bills corresponding to all the status but the one given
   */
  @Query("SELECT b FROM Bill b WHERE b.status != :status")
  List<Bill> getBillsNotInStatus(@Param("status") BillStatus status);

  /**
   * Method that gets from the DataBase all the Biils available
   *
   * @return the Bills available
   */
  @Query(value = "SELECT * FROM Bill", nativeQuery = true)
  List<Bill> getBills();
}
