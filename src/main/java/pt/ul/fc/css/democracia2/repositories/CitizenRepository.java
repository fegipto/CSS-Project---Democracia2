package pt.ul.fc.css.democracia2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.democracia2.domain.Citizen;

public interface CitizenRepository extends JpaRepository<Citizen, Long> {
  public Citizen findByName(String name);

  public Citizen findByCc(long cc);

  public Citizen findByToken(long token);
}