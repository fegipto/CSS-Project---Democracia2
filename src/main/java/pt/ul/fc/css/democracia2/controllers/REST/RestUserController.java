package pt.ul.fc.css.democracia2.controllers.REST;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.ul.fc.css.democracia2.DTO.DelegateDTO;
import pt.ul.fc.css.democracia2.domain.Delegate;
import pt.ul.fc.css.democracia2.repositories.DelegateRepository;
@RestController()
@RequestMapping("api")
public class RestUserController {
    @Autowired
    private DelegateRepository delegateRepository;
    

    @GetMapping("/delegate/name/{name}")
    ResponseEntity<DelegateDTO> getDelegateByName(@PathVariable String name) {
        Optional<Delegate> delegateOptional = delegateRepository.findByName(name);
        if (delegateOptional.isPresent()) {
            DelegateDTO delegateDTO = new DelegateDTO(delegateOptional.get());
            return ResponseEntity.ok(delegateDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}
