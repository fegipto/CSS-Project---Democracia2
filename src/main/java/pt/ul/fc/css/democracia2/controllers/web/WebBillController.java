package pt.ul.fc.css.democracia2.controllers.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pt.ul.fc.css.democracia2.DTO.BillDTO;
import pt.ul.fc.css.democracia2.DTO.DelegateDTO;
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

    @GetMapping("/bill/new")
    public String newBill(final Model model) {
        model.addAttribute("bill", new BillDTO());
        model.addAttribute("delegateName", "");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Topic>> responseEntity = restTemplate.exchange(
                "http://localhost:8080/api/topics",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Topic>>() {
                });
        List<Topic> topics = responseEntity.getBody();

        model.addAttribute("topics", topics);
        return "bill_new";
    }

    @PostMapping("/bills/propose")
    public String proposeBill(Model model, @ModelAttribute("bill") BillDTO bill,
            @ModelAttribute("delegate") DelegateDTO delegate) {
        bill.setProponentId(delegate.getCC());
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
        } catch (Exception e) {
            model.addAttribute("bill", new BillDTO());
            model.addAttribute("delegateName", delegate.getName());
            model.addAttribute("error", "Failed to add bill"+e.getMessage());
            return "bill_new";
        }
    }

    @RequestMapping("/bills/votable")
    public String getBills(Model model) {
        return "bills_votable";
    }

    @GetMapping("/delegate")
    public String getDelegate(@RequestParam("name") String delegateName, Model model,
            RedirectAttributes redirectAttributes) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<DelegateDTO> responseEntity = restTemplate
                .getForEntity("http://localhost:8080/api/delegate/name/" + delegateName, DelegateDTO.class);
        DelegateDTO delegate = responseEntity.getBody();

        // Pass data to the redirected view using RedirectAttributes
        redirectAttributes.addFlashAttribute("delegate", delegate);

        // Redirect to the "/bill/new" view
        return "redirect:/bill/new";
    }


    @GetMapping("/bill/{id}")
    public String getCustomerById(Model model, @PathVariable Long id) {
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
}
