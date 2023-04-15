package pt.ul.fc.css.democracia2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pt.ul.fc.css.democracia2.domain.Bill;
import pt.ul.fc.css.democracia2.domain.BillStatus;
import pt.ul.fc.css.democracia2.domain.Citizen;
import pt.ul.fc.css.democracia2.domain.Delegate;
import pt.ul.fc.css.democracia2.domain.Topic;
import pt.ul.fc.css.democracia2.repositories.BillRepository;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;
import pt.ul.fc.css.democracia2.repositories.DelegateRepository;
import pt.ul.fc.css.democracia2.repositories.TopicRepository;

@SpringBootTest
@Transactional
class ClosingExpiredBillsServiceTests extends MockDatabaseTests {

  @Autowired private ClosingExpiredBillsService closingExpiredBillsService;
  // @Autowired private VotingService votingService;

  @Autowired private DelegateRepository delegateRepository;
  @Autowired private TopicRepository topicRepository;
  @Autowired private BillRepository billRepository;
  @Autowired private CitizenRepository citizenRepository;

  @BeforeEach
  void initVotableBill() {
    Optional<Delegate> delegate1 = delegateRepository.findByName("Delegate 1");
    Optional<Topic> topic = topicRepository.findByName("Education");
    assertTrue(delegate1.isPresent());
    assertTrue(topic.isPresent());

    Bill added1 =
        delegate1
            .get()
            .proposeBill(
                "Bill Votable",
                "null",
                new byte[] {},
                LocalDateTime.now().plusSeconds(1),
                topic.get());
    List<Citizen> citizens = citizenRepository.findAll();
    int count = 0;
    for (Citizen cit : citizens) {
      added1.supportBill(cit);
      if (count == 10000) {

        break;
      }
      count++;
    }
    billRepository.save(added1);
  }

  @Test
  public void testScheduledExpiredBills() throws InterruptedException {
    Optional<Delegate> delegate1 = delegateRepository.findByName("Delegate 1");
    Optional<Topic> topic = topicRepository.findByName("Education");
    assertTrue(delegate1.isPresent());
    assertTrue(topic.isPresent());
    Bill added2 =
        delegate1
            .get()
            .proposeBill(
                "Bill 2", "null", new byte[] {}, LocalDateTime.now().plusSeconds(2), topic.get());
    billRepository.save(added2);
    assertEquals(BillStatus.CREATED, added2.getStatus());
    Thread.sleep(2000);
    closingExpiredBillsService.scheduledExpiredBills();
    assertEquals(BillStatus.EXPIRED, added2.getStatus());
  }
}
