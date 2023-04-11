package pt.ul.fc.css.democracia2.repositories;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ul.fc.css.democracia2.domain.Bill;
import pt.ul.fc.css.democracia2.domain.BillStatus;

public interface BillRepository extends JpaRepository<Bill, Long> {

  @Query("SELECT b FROM Bill WHERE b.status = 'VOTING'")
  List<Bill> getVotableBills();

  @Modifying
  @Transactional
  @Query(
      "INSERT INTO Bill(title, status, description, validity) VALUES (:title, :status, :description, :validity)")
  void createBill(
      @Param("title") String title,
      @Param("status") BillStatus status,
      @Param("description") String description,
      @Param("validity") LocalDateTime validity);
}
