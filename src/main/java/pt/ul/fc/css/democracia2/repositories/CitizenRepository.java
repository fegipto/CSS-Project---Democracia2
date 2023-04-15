package pt.ul.fc.css.democracia2.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.democracia2.domain.Citizen;

public interface CitizenRepository extends JpaRepository<Citizen, Long> {
  public Optional<Citizen> findByName(String name);

  public Optional<Citizen> findByCc(long cc);

  public Optional<Citizen> findByToken(long token);
}
