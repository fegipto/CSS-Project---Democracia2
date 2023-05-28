package pt.ul.fc.css.democracia2.controllers.rest;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ul.fc.css.democracia2.DTO.CitizenDTO;
import pt.ul.fc.css.democracia2.domain.Citizen;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;

/**
 * Class that represents a RestUserController
 * 
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
@RestController()
@RequestMapping("api")
public class RestUserController {
  @Autowired private CitizenRepository citizenRepository;

  @GetMapping("/citizen/name/{name}")
  ResponseEntity<CitizenDTO> getCitizenByName(@PathVariable String name) {
    Optional<Citizen> citizenOptional = citizenRepository.findByName(name);
    if (citizenOptional.isPresent()) {
      CitizenDTO citizenDTO = new CitizenDTO(citizenOptional.get());
      return ResponseEntity.ok(citizenDTO);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
