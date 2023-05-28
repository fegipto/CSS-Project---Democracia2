package pt.ul.fc.css.democracia2.controllers.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.ul.fc.css.democracia2.DTO.CitizenDTO;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;

@Controller
public class CitizenController {

  @Autowired private CitizenRepository citizenRepository;

  @GetMapping("/citizen")
  public String restGetCitizen(
      @RequestParam("name") String citizenName,
      Model model,
      @RequestHeader(value = "referer", defaultValue = "/") String referer,
      RedirectAttributes redirectAttributes,
      HttpSession session) {

    CitizenDTO citizen = new CitizenDTO(citizenRepository.findByName(citizenName).orElse(null));
    session.setAttribute("citizen", citizen);
    // Redirect back to the same page
    return "redirect:" + referer;
  }

  @PostMapping("/citizen/logout")
  public String logouString(
      Model model,
      @RequestHeader(value = "referer", defaultValue = "/") String referer,
      HttpSession session) {
    session.setAttribute("citizen", null);
    return "redirect:" + referer;
  }
}
