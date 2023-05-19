package pt.ul.fc.css.democracia2.controllers.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import pt.ul.fc.css.democracia2.DTO.BillDTO;

@Controller
public class NonExpiredController {
    Logger logger = LoggerFactory.getLogger(NonExpiredController.class);

    public NonExpiredController() {
        super();
    }


    @GetMapping("/bills/open")
    public String index(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<BillDTO>> responseEntity = restTemplate.exchange(
                "http://localhost:8080/api/bills/open",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BillDTO>>() {
                });
        List<BillDTO> bills = responseEntity.getBody();

        model.addAttribute("bills", bills);
        return "bills_non_expired";
    }


}
