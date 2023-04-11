package pt.ul.fc.css.democracia2.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pt.ul.fc.css.democracia2.domain.Citizen;

public interface CitizenRepository extends JpaRepository<Citizen, Long> {
  @Query("SELECT t FROM Citizen t WHERE t.cc = :cc")
  Citizen getCitizen(@Param("cc") Long cc);
}
