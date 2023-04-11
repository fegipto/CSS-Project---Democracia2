package pt.ul.fc.css.democracia2.repositories;

import javax.decorator.Delegate;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.democracia2.domain.Citizen;
import pt.ul.fc.css.democracia2.domain.Topic;

public interface CitizenRepository extends JpaRepository<Citizen, Long> {
  @Query("SELECT c FROM Citizen c WHERE c.cc = :cc")
  Citizen getCitizen(@Param("cc") Long cc);

  @Modifying
  @Query(
      "INSERT INTO Citizen (CIT_NAME, CIT_CC, CIT_TOKEN) VALUES (:citizenName, :citizenCC, :citizenToken)")
  void addCitizen(
      @Param("citizenName") String name,
      @Param("citizenCC") long cc,
      @Param("citizenToken") long token);

  @Modifying
  @Transactional
  @Query("UPDATE Citizen c SET c.chosenDelegate[:topic] = :delegate WHERE c.id = :citizenId")
  void addDelegate(
      @Param("citizenId") Long citizenId,
      @Param("topic") Topic topic,
      @Param("delegate") Delegate delegate);
}
