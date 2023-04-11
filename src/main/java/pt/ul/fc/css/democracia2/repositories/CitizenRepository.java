package pt.ul.fc.css.democracia2.repositories;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pt.ul.fc.css.democracia2.domain.Citizen;

public interface CitizenRepository extends JpaRepository<Citizen, Long> {
  @Query("SELECT c FROM Citizen c WHERE c.cc = :cc")
  Citizen getCitizen(@Param("cc") Long cc);

  @Modifying
  @Query("INSERT INTO Citizen (param1, param2, param3) VALUES (:param1, :param2, :param3)")
  void addCitizen(
      @Param("param1") String name, @Param("param2") long cc, @Param("param3") long token);
}
