package pt.ul.fc.css.democracia2;

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
import pt.ul.fc.css.democracia2.domain.Citizen;
import pt.ul.fc.css.democracia2.domain.Delegate;
import pt.ul.fc.css.democracia2.domain.Topic;
import pt.ul.fc.css.democracia2.repositories.BillRepository;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;
import pt.ul.fc.css.democracia2.repositories.DelegateRepository;
import pt.ul.fc.css.democracia2.repositories.TopicRepository;
import pt.ul.fc.css.democracia2.services.ConsultNonExpiredBillService;

@SpringBootTest
@Transactional
public class ConsultNonExpiredBillServiceTests extends MockDatabaseTests {

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
                  LocalDateTime.of(2023, 11, 5, 0, 0, 0, 0),
                  topic.get());
      billRepository.save(added);
    }
    for (int i = 100; i < 125; i++) {
      Bill added =
          delegate
              .get()
              .proposeBill(
                  "Bill " + i,
                  "null",
                  new byte[] {},
                  LocalDateTime.now().plusSeconds(1),
                  topic.get());
      added.expire(citizenRepository);
      billRepository.save(added);
    }
    List<Citizen> citizens = citizenRepository.findAll();
    for (int i = 125; i < 175; i++) {
      Bill added =
          delegate
              .get()
              .proposeBill(
                  "Bill " + i,
                  "null",
                  new byte[] {},
                  LocalDateTime.of(2023, 11, 5, 0, 0, 0, 0),
                  topic.get());
      for (Citizen cit : citizens) {
        added.supportBill(cit);
      }
      billRepository.save(added);
    }
    for (int i = 175; i < 200; i++) {
      Bill added =
          delegate
              .get()
              .proposeBill(
                  "Bill " + i,
                  "null",
                  new byte[] {},
                  LocalDateTime.now().plusSeconds(1),
                  topic.get());
      for (Citizen cit : citizens) {
        added.supportBill(cit);
      }
      added.expire(citizenRepository);
      billRepository.save(added);
    }

    List<BillDTO> t = consultNonExpiredBillService.listNonExpired();
    assertTrue(t.size() == 175);
  }
}