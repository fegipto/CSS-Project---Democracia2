package pt.ul.fc.css.democracia2.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ul.fc.css.democracia2.domain.Bill;
import pt.ul.fc.css.democracia2.domain.BillStatus;

public interface BillRepository extends JpaRepository<Bill, Long> {

  Optional<Bill> findByTitle(String title);

  @Query("SELECT b FROM Bill b WHERE b.status = :status")
  List<Bill> getBillsByStatus(@Param("status") BillStatus status);

  @Query("SELECT b FROM Bill b WHERE b.status != :status")
  List<Bill> getBillsNotInStatus(@Param("status") BillStatus status);

  @Query(value = "SELECT * FROM Bill", nativeQuery = true)
  List<Bill> getBills();
}
