package pt.ul.fc.css.democracia2;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import pt.ul.fc.css.democracia2.services.ListAvailableVotesService;

@SpringBootTest
@Transactional
public class ListAvailableVotesTest {

  @Autowired private ListAvailableVotesService listAvailableVotesService;

  @Autowired private BillRepository billRepository;

  @Autowired private DelegateRepository delegateRepository;
  @Autowired private TopicRepository topicRepository;
  @Autowired private CitizenRepository citizenRepository;

  @Test
  public void shouldReturnOnlyVotableBills() {

    Optional<Delegate> delegate1 = delegateRepository.findByName("Delegate 1");
    Optional<Topic> topic = topicRepository.findByName("Education");
    assertTrue(delegate1.isPresent());
    assertTrue(topic.isPresent());
    Bill added1 =
        delegate1
            .get()
            .proposeBill(
                "Bill 1",
                "null",
                new byte[] {},
                LocalDateTime.of(2023, 11, 5, 0, 0, 0, 0),
                topic.get());
    Bill added2 =
        delegate1
            .get()
            .proposeBill(
                "Bill 2",
                "null",
                new byte[] {},
                LocalDateTime.of(2023, 10, 5, 0, 0, 0, 0),
                topic.get());
    assertTrue(added1.getStatus() == BillStatus.CREATED);
    assertTrue(added2.getStatus() == BillStatus.CREATED);
    List<Citizen> citizens = citizenRepository.findAll();
    int count = added1.getSupporters().size();
    for (Citizen cit : citizens) {

      if (count == 10000) {
        assertFalse(added1.supportBill(cit));
        assertFalse(added2.supportBill(cit));
        break;
      } else {
        assertTrue(added1.supportBill(cit));
        assertTrue(added2.supportBill(cit));
      }
      count++;
    }
    billRepository.save(added1);
    billRepository.save(added2);
    assertTrue(added1.getStatus() == BillStatus.VOTING);

    // ensure that only votable bills are returned
    List<BillDTO> availableVotes = listAvailableVotesService.listAvailableVotes();
    assertEquals(2, availableVotes.size());
    for (BillDTO bill : availableVotes) {
      assertTrue(bill.getStatus() == BillStatus.VOTING);
    }
  }
}
