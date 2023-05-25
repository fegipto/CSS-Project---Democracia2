package pt.ul.fc.css.democracia2.controllers.REST;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
class RestCustomerController {

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
  ResponseEntity<?> supportBill(@RequestBody Pair<Long, Long> pair) {
    try {
      supportBillService.supportBill(pair.getFirst(), pair.getSecond());
      return ResponseEntity.ok().body(null);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/bill/{citizenId}/voted/{billId}")
  Optional<Boolean> getVote(@PathVariable Long citizenId, @PathVariable Long billId) {
    return votingService.getOmmitedVote(citizenId, billId);
  }

  @PostMapping("/bill/vote")
  ResponseEntity<?> voteBill(@RequestBody Pair<Pair<Long, Long>, Boolean> info) {
    try {
      boolean sucess =
          votingService.vote(
              info.getFirst().getFirst(), info.getFirst().getSecond(), info.getSecond());
      return ResponseEntity.ok().body(sucess);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/test/votable/bills")
  void generateVotableBills() {
    demoService.initVotableBills();
  }
}
