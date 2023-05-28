package pt.ul.fc.css.democracia2.controllers.REST;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pt.ul.fc.css.democracia2.DTO.BillDTO;
import pt.ul.fc.css.democracia2.domain.Topic;
import pt.ul.fc.css.democracia2.services.ConsultNonExpiredBillService;
import pt.ul.fc.css.democracia2.services.DemoService;
import pt.ul.fc.css.democracia2.services.ListAvailableVotesService;
import pt.ul.fc.css.democracia2.services.ProposeBillService;
import pt.ul.fc.css.democracia2.services.SupportBillService;
import pt.ul.fc.css.democracia2.services.VotingService;

@RestController()
@RequestMapping("api")
class RestBillrController {

  @Autowired private ListAvailableVotesService billsService;

  @Autowired private ConsultNonExpiredBillService consultBillService;
  @Autowired private ProposeBillService proposeBillService;
  @Autowired private SupportBillService supportBillService;
  @Autowired private VotingService votingService;
  @Autowired private DemoService demoService;

  @GetMapping("/bills/votable")
  List<BillDTO> all() {
    return billsService.listAvailableVotes();
  }

  @GetMapping("/topics")
  List<Topic> allTopics() {
    return proposeBillService.getTopics();
  }

  @GetMapping("/bill/{id}")
  Optional<BillDTO> getSpecific(@PathVariable Long id) {
    return consultBillService.getBill(id);
  }

  @GetMapping("/bills/open")
  List<BillDTO> allBills() {
    return consultBillService.listNonExpired();
  }

  @PostMapping("/bills")
  ResponseEntity<?> createBill(@RequestBody BillDTO bill) {
    try {
      BillDTO c = proposeBillService.presentBill(bill);
      return ResponseEntity.ok().body(c);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (IOException e) {
      return new ResponseEntity<>(HttpStatusCode.valueOf(500));
    }
  }

  @PostMapping("/bill/support")
  ResponseEntity<?> supportBill(@RequestParam Long citizen, @RequestParam Long bill) {
    try {
      return ResponseEntity.ok().body(supportBillService.supportBill(citizen, bill));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/bill/{citizenId}/voted/{billId}")
  String getVote(@PathVariable long citizenId, @PathVariable long billId) {
    if (votingService.hasVoted(citizenId, billId)) {
      return "voted";
    }
    Optional<Boolean> vote = votingService.getOmmitedVote(citizenId, billId);
    return (vote.isEmpty()) ? null : ((vote.get()) ? "yes" : "no");
  }

  @PostMapping("/bill/vote")
  ResponseEntity<?> voteBill(
      @RequestParam Long citizen, @RequestParam Long bill, @RequestParam Boolean vote) {
    try {
      boolean success = votingService.vote(citizen, bill, vote);
      return ResponseEntity.ok().body(success);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  // DEMO ONLY
  @GetMapping("/test/votable/bills")
  void generateVotableBills() {
    demoService.initVotableBills();
  }

  @GetMapping("/test/supportable/bills")
  void generateSuportableBills() {
    demoService.initSupportableBills();
  }
}
