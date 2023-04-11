package pt.ul.fc.css.democracia2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.democracia2.domain.Citizen;

public interface CitizenRepository extends JpaRepository<Citizen, Long> {
    
}