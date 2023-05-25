package pt.ul.fc.css.democracia2.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pt.ul.fc.css.democracia2.DTO.BillDTO;
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
public class ConsultNonExpiredBillServiceTests {

  @Autowired private ConsultNonExpiredBillService consultNonExpiredBillService;

  @Autowired private BillRepository billRepository;

  @Autowired private DelegateRepository delegateRepository;

  @Autowired private CitizenRepository citizenRepository;

  @Autowired private TopicRepository topicRepository;

  @Test
  public void testListNonExpired() {
    Optional<Delegate> delegate = delegateRepository.findByName("Delegate 1");
    Optional<Topic> topic = topicRepository.findByName("Education");
    assertTrue(delegate.isPresent());
    assertTrue(topic.isPresent());

    for (int i = 0; i < 100; i++) {
      Bill added =
          delegate
              .get()
              .proposeBill(
                  "Bill " + i,
                  "null",
                  new byte[] {},
                  LocalDateTime.now().plusMonths(3),
                  topic.get());
      billRepository.save(added);
    }

    List<BillDTO> t = consultNonExpiredBillService.listNonExpired();
    assertTrue(t.size() == 100);
  }

  @Test
  public void testListExpired() throws InterruptedException {
    Optional<Delegate> delegate = delegateRepository.findByName("Delegate 1");
    Optional<Topic> topic = topicRepository.findByName("Education");
    assertTrue(delegate.isPresent());
    assertTrue(topic.isPresent());

    for (int i = 0; i < 5; i++) {
      Bill added =
          delegate
              .get()
              .proposeBill(
                  "Bill " + i,
                  "null",
                  new byte[] {},
                  LocalDateTime.now().plusSeconds(1),
                  topic.get());
      Thread.sleep(1000);
      assertTrue(added.isExpired());
      added.expire(citizenRepository);
      billRepository.save(added);
    }

    List<BillDTO> t = consultNonExpiredBillService.listNonExpired();
    assertTrue(t.size() == 0);
  }

  @Test
  public void testGetNonExpiredBill() {
    Optional<Delegate> delegate = delegateRepository.findByName("Delegate 1");
    Optional<Topic> topic = topicRepository.findByName("Education");
    assertTrue(delegate.isPresent());
    assertTrue(topic.isPresent());

    Bill added =
        delegate
            .get()
            .proposeBill(
                "Bill ", "null", new byte[] {}, LocalDateTime.now().plusMonths(3), topic.get());
    assertFalse(added.isExpired());
    billRepository.save(added);

    Optional<BillDTO> t = consultNonExpiredBillService.getBill(added.getId());
    assertTrue(t.get().getStatus() == BillStatus.CREATED);
  }

  @Test
  public void testGetExpiredBill() throws InterruptedException {
    Optional<Delegate> delegate = delegateRepository.findByName("Delegate 1");
    Optional<Topic> topic = topicRepository.findByName("Education");
    assertTrue(delegate.isPresent());
    assertTrue(topic.isPresent());

    Bill added =
        delegate
            .get()
            .proposeBill(
                "Bill ", "null", new byte[] {}, LocalDateTime.now().plusSeconds(1), topic.get());
    Thread.sleep(1000);
    assertTrue(added.isExpired());
    added.expire(citizenRepository);
    billRepository.save(added);

    Optional<BillDTO> t = consultNonExpiredBillService.getBill(added.getId());
    assertTrue(t.get().getStatus() == BillStatus.EXPIRED);
  }

  @Test
  public void testGetApprovedBill() throws InterruptedException {
    Optional<Delegate> delegate = delegateRepository.findByName("Delegate 1");
    Optional<Topic> topic = topicRepository.findByName("Education");
    assertTrue(delegate.isPresent());
    assertTrue(topic.isPresent());

    Bill added =
        delegate
            .get()
            .proposeBill(
                "Bill ", "null", new byte[] {}, LocalDateTime.now().plusSeconds(1), topic.get());
    Thread.sleep(1000);
    List<Citizen> citizens = citizenRepository.findAll();

    int count = added.getSupporters().size();
    for (Citizen cit : citizens) {

      if (count == 10000) {
        assertFalse(added.supportBill(cit));
        break;
      } else {
        assertTrue(added.supportBill(cit));
        assertFalse(added.supportBill(cit));
      }
      count++;
    }

    added.expire(citizenRepository); // skip isExpired requires, so we dont wait 15 days
    billRepository.save(added);

    Optional<BillDTO> t = consultNonExpiredBillService.getBill(added.getId());
    assertTrue(t.get().getStatus() == BillStatus.ACCEPTED);
  }
}
