package pt.ul.fc.css.democracia2.controllers.web;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.ul.fc.css.democracia2.DTO.BillDTO;
import pt.ul.fc.css.democracia2.DTO.CitizenDTO;
import pt.ul.fc.css.democracia2.domain.Topic;
import pt.ul.fc.css.democracia2.repositories.TopicRepository;
import pt.ul.fc.css.democracia2.services.ConsultNonExpiredBillService;
import pt.ul.fc.css.democracia2.services.DemoService;
import pt.ul.fc.css.democracia2.services.ListAvailableVotesService;
import pt.ul.fc.css.democracia2.services.ProposeBillService;
import pt.ul.fc.css.democracia2.services.SupportBillService;
import pt.ul.fc.css.democracia2.services.VotingService;

@Controller
public class WebBillController {
  Logger logger = LoggerFactory.getLogger(WebBillController.class);
  @Autowired private ListAvailableVotesService billsService;

  @Autowired private ConsultNonExpiredBillService consultBillService;
  @Autowired private ProposeBillService proposeBillService;
  @Autowired private SupportBillService supportBillService;
  @Autowired private VotingService votingService;
  @Autowired private DemoService demoService;
  @Autowired private TopicRepository topicRepository;

  public WebBillController() {
    super();
  }

  @GetMapping({"/", "/bills/votable"})
  public String index(Model model, HttpSession session) {
    CitizenDTO citizen = (CitizenDTO) session.getAttribute("citizen");
    List<BillDTO> bills = billsService.listAvailableVotes();
    model.addAttribute("bills", bills);

    String[] votes = new String[bills.size()];
    if (citizen != null) {
      int i = 0;
      for (BillDTO bill : bills) {
        votes[i] = getUserVote(citizen.getCc(), bill.getId());
        i++;
      }
    }
    model.addAttribute("billsVotes", votes);

    return "bills_votable";
  }

  @PostMapping("/bills/propose")
  public String proposeBill(
      Model model,
      @ModelAttribute("bill") BillDTO bill,
      @RequestParam("time") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime time) {
    bill.setValidity(time);
    // Create post request
    try {
      BillDTO createdBill = proposeBillService.presentBill(bill);

      logger.debug("Bill added to the database.");
      return "redirect:/bill/" + createdBill.getId();
    } catch (Exception e) {
      model.addAttribute("bill", new BillDTO());
      model.addAttribute("error", e.getMessage());
      return "bill_new";
    }
  }

  @GetMapping("/bills/open")
  public String getOpenBills(Model model) {

    List<BillDTO> bills = consultBillService.listNonExpired();

    model.addAttribute("bills", bills);

    return "bills_non_expired";
  }

  @GetMapping("/bill/new")
  public String newBill(final Model model) {
    model.addAttribute("bill", new BillDTO());
    List<Topic> topics = topicRepository.findAll();

    model.addAttribute("topics", topics);
    return "bill_new";
  }

  @GetMapping("/bill/{id}")
  public String getBillById(Model model, @PathVariable Long id) {

    Optional<BillDTO> bill = consultBillService.getBill(id);

    if (bill.isPresent()) {
      model.addAttribute("bill", bill.get());
      return "bill_detail";
    }
    return null;
  }

  @PostMapping("/bill/support")
  public String supportBill(
      Model model, @RequestParam("billId") Long billId, @RequestParam("citizenId") Long citizenId) {
    // Call the REST endpoint

    supportBillService.supportBill(citizenId, billId);

    return "redirect:/bill/" + billId;
  }

  @PostMapping("/bill/vote")
  public String voteBill(
      Model model,
      @RequestParam("billId") Long billId,
      @RequestParam("citizenToken") Long citizenToken,
      @RequestParam("vote") String vote,
      RedirectAttributes redirectAttributes) {
    // Process the vote based on the "vote" parameter value ("yes" or "no")
    boolean voteB = vote.equals("yes");
    boolean success = votingService.vote(citizenToken, billId, voteB);
    redirectAttributes.addFlashAttribute("success", "" + success);

    return "redirect:/bills/votable";
  }

  public String getUserVote(Long citizenId, Long billId) {
    // Call the REST endpoint
    if (votingService.hasVoted(citizenId, billId)) {
      return "voted";
    }

    Optional<Boolean> vote = votingService.getOmmitedVote(citizenId, billId);

    return (vote.isEmpty()) ? null : ((vote.get()) ? "yes" : "no");
  }

  @GetMapping("/test/votable/bills")
  public String generateVotableBills() {
    demoService.initVotableBills();
    return "redirect:/bills/votable";
  }
}
