package pt.ul.fc.css.democracia2.controllers.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.ul.fc.css.democracia2.DTO.CitizenDTO;

@Controller
public class CitizenController {
  private final RestTemplate restTemplate;

  public CitizenController(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @GetMapping("/citizen")
  public String restGetCitizen(
      @RequestParam("name") String citizenName,
      Model model,
      @RequestHeader(value = "referer", defaultValue = "/") String referer,
      RedirectAttributes redirectAttributes,
      HttpSession session) {
    ResponseEntity<CitizenDTO> responseEntity =
        restTemplate.getForEntity(
            "http://localhost:8080/api/citizen/name/" + citizenName, CitizenDTO.class);
    CitizenDTO citizen = responseEntity.getBody();
    session.setAttribute("citizen", citizen);
    // Redirect back to the same page
    return "redirect:" + referer;
  }
  // @GetMapping("/citizen")
  // public String restGetCitizen(
  //    @RequestParam("name") String citizenName,
  //    Model model,
  //    @RequestHeader(value = "referer", defaultValue = "/") String referer,
  //    RedirectAttributes redirectAttributes,
  //    HttpSession session) {
  //  RestTemplate restTemplate = new RestTemplate();
  //  ResponseEntity<CitizenDTO> responseEntity =
  //      restTemplate.getForEntity(
  //          "http://localhost:8080/api/citizen/name/" + citizenName, CitizenDTO.class);
  //  CitizenDTO citizen = responseEntity.getBody();
  //  session.setAttribute("citizen", citizen);
  //  // Redirect back to the same page
  //  return "redirect:" + referer;
  // }

  @PostMapping("/citizen/logout")
  public String logouString(
      Model model,
      @RequestHeader(value = "referer", defaultValue = "/") String referer,
      HttpSession session) {
    session.setAttribute("citizen", null);
    return "redirect:" + referer;
  }
}
