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
import pt.ul.fc.css.democracia2.domain.Delegate;
import pt.ul.fc.css.democracia2.domain.Topic;
import pt.ul.fc.css.democracia2.repositories.BillRepository;
import pt.ul.fc.css.democracia2.repositories.DelegateRepository;
import pt.ul.fc.css.democracia2.repositories.TopicRepository;
import pt.ul.fc.css.democracia2.services.ConsultNonExpiredBillService;

@SpringBootTest
@Transactional
public class ConsultNonExpiredBillServiceTests extends MockDatabaseTests {

  @Autowired private ConsultNonExpiredBillService consultNonExpiredBillService;

  @Autowired private BillRepository billRepository;

  @Autowired private DelegateRepository delegateRepository;

  @Autowired private TopicRepository topicRepository;

  @Test
  public void testListNonExpired() {
    Optional<Delegate> delegate = delegateRepository.findByName("Delegate 1");
    Optional<Topic> topic = topicRepository.findByName("Education");
    assertTrue(delegate.isPresent());
    assertTrue(topic.isPresent());

    Bill added1 =
        delegate
            .get()
            .proposeBill(
                "Bill 1",
                "null",
                new byte[] {},
                LocalDateTime.of(2023, 11, 5, 0, 0, 0, 0),
                topic.get());
    Bill added2 =
        delegate
            .get()
            .proposeBill(
                "Bill 2",
                "null",
                new byte[] {},
                LocalDateTime.of(2023, 10, 5, 0, 0, 0, 0),
                topic.get());
    billRepository.save(added1);
    billRepository.save(added2);

    List<BillDTO> t = consultNonExpiredBillService.listNonExpired();

    assertTrue(t.size() == 2);
  }
}
