package pt.ul.fc.css.democracia2.controllers.web;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.RestTemplate;

import pt.ul.fc.css.democracia2.DTO.BillDTO;
import pt.ul.fc.css.democracia2.DTO.CitizenDTO;
import pt.ul.fc.css.democracia2.domain.Topic;

@Controller
public class WebBillController {
    Logger logger = LoggerFactory.getLogger(WebBillController.class);

    public WebBillController() {
        super();
    }

    @GetMapping({ "/", "/bills/votable" })
    public String index(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<BillDTO>> responseEntity = restTemplate.exchange(
                "http://localhost:8080/api/bills/votable",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BillDTO>>() {
                });
        List<BillDTO> bills = responseEntity.getBody();

        model.addAttribute("bills", bills);
        return "bills_votable";
    }

    @PostMapping("/bills/propose")
    public String proposeBill(Model model, @ModelAttribute("bill") BillDTO bill,
            @RequestParam("time") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime time) {
        bill.setValidity(time);
        // Create post request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<BillDTO> requestEntity = new HttpEntity<>(bill, headers);

        try {
            ResponseEntity<BillDTO> bresponseEntity = restTemplate.exchange(
                    "http://localhost:8080/api/bills",
                    HttpMethod.POST,
                    requestEntity,
                    BillDTO.class);
            BillDTO createdBill = bresponseEntity.getBody();

            logger.debug("Bill added to the database.");
            return "redirect:/bill/" + createdBill.getId();
        } catch (BadRequest e) {
            model.addAttribute("bill", new BillDTO());
            model.addAttribute("error", e.getMessage());
            return "bill_new";
        } catch (Exception e) {
            model.addAttribute("bill", new BillDTO());
            model.addAttribute("error", "Sorry failed to add bill " + bill.getTitle());
            return "bill_new";
        }
    }
    @GetMapping("/bills/open")
    public String getOpenBills(Model model, @ModelAttribute CitizenDTO citizen) {
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

    @GetMapping("/bill/new")
    public String newBill(final Model model) {
        model.addAttribute("bill", new BillDTO());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Topic>> tResponseEntity = restTemplate.exchange(
                "http://localhost:8080/api/topics",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Topic>>() {
                });
        List<Topic> topics = tResponseEntity.getBody();

        model.addAttribute("topics", topics);
        return "bill_new";
    }


    @GetMapping("/bill/{id}")
    public String getBillById(Model model, @PathVariable Long id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<BillDTO> responseEntity = restTemplate
                .getForEntity("http://localhost:8080/api/bill/" + id, BillDTO.class);
        BillDTO bill = responseEntity.getBody();

        if (bill != null) {
            model.addAttribute("bill", bill);
            return "bill_detail";
        }
        return null;
    }

    @PutMapping("/bill/support")
    public String supportBill(Model model, @RequestParam("billId") Long billId, @RequestParam("citizenId") Long citizenId) {
            // Call the REST endpoint
        String endpointUrl = "https://example.com/api/bill/support";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Long> requestBody = new HashMap<>();
        requestBody.put("billId", billId);
        requestBody.put("citizenToken", citizenId);

        HttpEntity<Map<String, Long>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Void> responseEntity = restTemplate.exchange(endpointUrl, HttpMethod.PUT, requestEntity, Void.class);

        return "redirect:/bill/"+billId;
    }
}
