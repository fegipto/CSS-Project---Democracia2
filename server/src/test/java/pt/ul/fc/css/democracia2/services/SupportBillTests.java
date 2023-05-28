package pt.ul.fc.css.democracia2.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.transaction.Transactional;
import java.time.Duration;
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

/**
 * Class that tests the SupportBillService
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
@SpringBootTest
@Transactional
class SupportBillTests {

  @Autowired private SupportBillService supportBillService;

  @Autowired private BillRepository billRepository;

  @Autowired private DelegateRepository delegateRepository;
  @Autowired private TopicRepository topicRepository;
  @Autowired private CitizenRepository citizenRepository;

  @Test
  void testGetOpenToSupportBills() {

    Optional<Delegate> delegate1 = delegateRepository.findByName("Delegate 1");
    Optional<Topic> topic = topicRepository.findByName("Education");
    assertTrue(delegate1.isPresent());
    assertTrue(topic.isPresent());

    Bill added1 =
        delegate1
            .get()
            .proposeBill(
                "Bill 1", "null", new byte[] {}, LocalDateTime.now().plusMonths(3), topic.get());
    Bill added2 =
        delegate1
            .get()
            .proposeBill(
                "Bill 2", "null", new byte[] {}, LocalDateTime.now().plusMonths(4), topic.get());
    assertTrue(added1.getStatus() == BillStatus.CREATED);
    assertTrue(added2.getStatus() == BillStatus.CREATED);
    List<Citizen> citizens = citizenRepository.findAll();
    int count = added1.getSupporters().size();
    for (Citizen cit : citizens) {

      if (count == 10000) {
        assertFalse(added1.supportBill(cit));
        break;
      } else {
        assertTrue(added1.supportBill(cit));
      }
      count++;
    }
    billRepository.save(added1);
    billRepository.save(added2);
    List<BillDTO> openToSupportBills = supportBillService.getOpenToSupportBills();

    assertEquals(1, openToSupportBills.size());
    assertEquals("Bill 2", openToSupportBills.get(0).getTitle());
  }

  @Test
  void testSupportBillStatus() {
    Optional<Delegate> delegate1 = delegateRepository.findByName("Delegate 1");
    Optional<Topic> topic = topicRepository.findByName("Education");
    assertTrue(delegate1.isPresent());
    assertTrue(topic.isPresent());

    Bill added1 =
        delegate1
            .get()
            .proposeBill(
                "Bill 1", "null", new byte[] {}, LocalDateTime.now().plusMonths(3), topic.get());
    Bill added2 =
        delegate1
            .get()
            .proposeBill(
                "Bill 2", "null", new byte[] {}, LocalDateTime.now().plusMonths(4), topic.get());
    assertTrue(added1.getStatus() == BillStatus.CREATED);
    assertTrue(added2.getStatus() == BillStatus.CREATED);
    List<Citizen> citizens = citizenRepository.findAll();
    assertTrue(added2.supportBill(citizens.get(0)));

    int count = added1.getSupporters().size();
    for (Citizen cit : citizens) {

      if (count == 10000) {
        assertFalse(added1.supportBill(cit));
        break;
      } else {
        assertTrue(added1.supportBill(cit));
        assertFalse(added1.supportBill(cit));
      }
      count++;
    }
    billRepository.save(added1);
    billRepository.save(added2);

    assertEquals(2, added2.getSupporters().size());
    assertTrue(added2.getStatus() == BillStatus.CREATED);

    assertEquals(10000, added1.getSupporters().size());
    assertTrue(added1.getStatus() == BillStatus.VOTING);
  }

  @Test
  void testSupportBillProponentVote() {
    Optional<Delegate> delegate1 = delegateRepository.findByName("Delegate 1");
    Optional<Topic> topic = topicRepository.findByName("Education");
    assertTrue(delegate1.isPresent());
    assertTrue(topic.isPresent());

    Bill added1 =
        delegate1
            .get()
            .proposeBill(
                "Bill 1", "null", new byte[] {}, LocalDateTime.now().plusMonths(3), topic.get());
    assertTrue(added1.getStatus() == BillStatus.CREATED);
    List<Citizen> citizens = citizenRepository.findAll();

    int count = added1.getSupporters().size();
    for (Citizen cit : citizens) {

      if (count == 10000) {
        assertFalse(added1.supportBill(cit));
        break;
      } else {
        assertTrue(added1.supportBill(cit));
        assertFalse(added1.supportBill(cit));
      }
      count++;
    }
    billRepository.save(added1);

    assertEquals(10000, added1.getSupporters().size());
    assertTrue(added1.getStatus() == BillStatus.VOTING);

    assertTrue(added1.getVoteBox().getPublicCastVote(delegate1.get()).get());
  }

  @Test
  void testSupportBillClampValidity() {
    LocalDateTime now = LocalDateTime.now();

    // Given
    Optional<Delegate> delegate1 = delegateRepository.findByName("Delegate 1");
    Optional<Topic> topic = topicRepository.findByName("Education");
    assertTrue(delegate1.isPresent());
    assertTrue(topic.isPresent());

    Bill added1 =
        delegate1
            .get()
            .proposeBill("Bills 1", "null", new byte[] {}, now.plusMonths(5), topic.get());
    assertTrue(added1.getStatus() == BillStatus.CREATED);
    List<Citizen> citizens = citizenRepository.findAll();

    int count = added1.getSupporters().size();
    for (Citizen cit : citizens) {

      if (count == 10000) {
        assertFalse(added1.supportBill(cit));
        break;
      } else {
        assertTrue(added1.supportBill(cit));
      }
      count++;
    }
    billRepository.save(added1);

    assertEquals(10000, added1.getSupporters().size());
    assertTrue(added1.getStatus() == BillStatus.VOTING);

    LocalDateTime validity = added1.getValidity();
    assertTrue(Duration.between(now.plusMonths(2), validity).toDays() < 2);
  }

  @Test
  void testSupportBillExtendValidity() {
    LocalDateTime now = LocalDateTime.now();

    // Given
    Optional<Delegate> delegate1 = delegateRepository.findByName("Delegate 1");
    Optional<Topic> topic = topicRepository.findByName("Education");
    assertTrue(delegate1.isPresent());
    assertTrue(topic.isPresent());

    Bill added1 =
        delegate1.get().proposeBill("Bill 1", "null", new byte[] {}, now.plusDays(10), topic.get());
    assertTrue(added1.getStatus() == BillStatus.CREATED);
    List<Citizen> citizens = citizenRepository.findAll();

    int count = added1.getSupporters().size();
    for (Citizen cit : citizens) {

      if (count == 10000) {
        assertFalse(added1.supportBill(cit));
        break;
      } else {
        assertTrue(added1.supportBill(cit));
      }
      count++;
    }
    billRepository.save(added1);

    assertEquals(10000, added1.getSupporters().size());
    assertTrue(added1.getStatus() == BillStatus.VOTING);

    LocalDateTime validity = added1.getValidity();
    assertTrue(Duration.between(now.plusDays(15), validity).toDays() < 2);
  }

  @Test
  void testSupportExpiredBill() {
    LocalDateTime now = LocalDateTime.now();

    // Given
    Optional<Delegate> delegate1 = delegateRepository.findByName("Delegate 1");
    Optional<Topic> topic = topicRepository.findByName("Education");
    assertTrue(delegate1.isPresent());
    assertTrue(topic.isPresent());

    Bill added1 =
        delegate1
            .get()
            .proposeBill("Bill 1", "null", new byte[] {}, now.minusDays(10), topic.get());
    assertTrue(added1 == null);
  }
}
