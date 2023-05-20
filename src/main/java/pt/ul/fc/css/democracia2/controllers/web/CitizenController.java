package pt.ul.fc.css.democracia2.controllers.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pt.ul.fc.css.democracia2.DTO.CitizenDTO;

@Controller
public class CitizenController {
    private CitizenDTO cachedCitizen;

    public CitizenDTO getCitizen(String citizenName, Model model) {
        if (cachedCitizen != null) {
            return cachedCitizen;
        } else {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<CitizenDTO> responseEntity = restTemplate.getForEntity(
                    "http://localhost:8080/api/citizen/name/" + citizenName,
                    CitizenDTO.class
            );
            cachedCitizen = responseEntity.getBody();
            model.addAttribute("citizen", cachedCitizen);

            return cachedCitizen;
        }
    }

    @GetMapping("/citizen")
    public String restGetCitizen(@RequestParam("name") String citizenName, Model model,@RequestHeader(value = "referer", defaultValue = "/") String referer,
    RedirectAttributes redirectAttributes) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CitizenDTO> responseEntity = restTemplate
                .getForEntity("http://localhost:8080/api/citizen/name/" + citizenName, CitizenDTO.class);
        CitizenDTO citizen = responseEntity.getBody();
        this.cachedCitizen = citizen;
        // Pass data to the redirected view using RedirectAttributes
       redirectAttributes.addFlashAttribute("citizen", citizen);
        // Redirect back to the same page
        return "redirect:"+referer;
    }
}

