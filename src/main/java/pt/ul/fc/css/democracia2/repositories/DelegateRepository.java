package pt.ul.fc.css.democracia2.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.democracia2.domain.Delegate;

public interface DelegateRepository extends JpaRepository<Delegate, Long> {
  public Optional<Delegate> findByName(String name);

  public Optional<Delegate> findByToken(long token);
}
