package pt.ul.fc.css.democracia2;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import pt.ul.fc.css.democracia2.services.UpdateBillsService;

@SpringBootTest
class UpdateBillsTests extends MockDatabaseTests {

  @Autowired private UpdateBillsService updateBillsService;

  @Autowired private DelegateRepository delegateRepository;

  @Autowired private TopicRepository topicRepository;

  @Autowired private BillRepository billRepository;

  @Autowired private CitizenRepository citizenRepository;

  @BeforeEach
  void initVotableBill() {
    Optional<Delegate> delegate1 = delegateRepository.findByName("Delegate 1");
    Optional<Topic> topic = topicRepository.findByName("Education");

    if (delegate1.isPresent() && topic.isPresent()) {
      Bill added1 =
          delegate1
              .get()
              .proposeBill(
                  "Bill Votable",
                  "null",
                  new byte[] {},
                  LocalDateTime.now().plusSeconds(2),
                  topic.get());

      List<Citizen> citizens = citizenRepository.findAll();
      for (Citizen c : citizens) {
        added1.supportBill(c);
      }

      billRepository.save(added1);
    }
  }

  @Test
  @Transactional
  public void testScheduledExpiredBills() throws InterruptedException {
    Optional<Delegate> delegate1 = delegateRepository.findByName("Delegate 1");
    Optional<Topic> topic = topicRepository.findByName("Education");

    if (delegate1.isPresent() && topic.isPresent()) {
      Bill added2 =
          delegate1
              .get()
              .proposeBill(
                  "Bill 2", "null", new byte[] {}, LocalDateTime.now().plusSeconds(2), topic.get());

      billRepository.save(added2);
      assertEquals(BillStatus.CREATED, added2.getStatus());
      Thread.sleep(2000);
      updateBillsService.scheduledExpiredBills();
      assertEquals(BillStatus.EXPIRED, added2.getStatus());
      Bill votable = delegate1.get().getBills().get(0);
      assertEquals(BillStatus.VOTING, votable.getStatus()); // validity extended to 15 days

      // To test if it expires properly without waiting 15 days calling the domain manually
      votable.expire(citizenRepository);
      assertEquals(
          BillStatus.ACCEPTED,
          votable.getStatus()); // no one voted, but the proponent automattically did
    }
  }
}
