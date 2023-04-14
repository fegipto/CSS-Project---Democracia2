package pt.ul.fc.css.democracia2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.democracia2.domain.Delegate;

public interface DelegateRepository extends JpaRepository<Delegate, Long> {
  public Delegate findByName(String name);
}
