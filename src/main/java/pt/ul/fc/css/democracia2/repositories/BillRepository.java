package pt.ul.fc.css.democracia2.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pt.ul.fc.css.democracia2.domain.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {

  @Query("SELECT b FROM Bill WHERE b.status = 'VOTING'")
  List<Bill> getVotableBills();
}
