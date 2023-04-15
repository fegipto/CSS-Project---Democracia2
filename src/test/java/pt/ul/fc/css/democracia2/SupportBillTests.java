package pt.ul.fc.css.democracia2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
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
class SupportBillTests extends MockDatabaseTests {

  @Autowired private SupportBillService supportBillService;

  @Autowired private BillRepository billRepository;

  @Autowired private DelegateRepository delegateRepository;
  @Autowired private TopicRepository topicRepository;
  @Autowired private CitizenRepository citizenRepository;

  @Test
  void testGetOpenToSupportBills() {

    Delegate delegate1 = delegateRepository.findByName("Delegate 1");
    Topic topic = topicRepository.findByName("Education");
    Bill added1 =
        delegate1.proposeBill(
            "Bill 1", "null", new byte[] {}, LocalDateTime.of(2023, 11, 5, 0, 0, 0, 0), topic);
    Bill added2 =
        delegate1.proposeBill(
            "Bill 2", "null", new byte[] {}, LocalDateTime.of(2023, 10, 5, 0, 0, 0, 0), topic);
    assertTrue(added1.getStatus() == BillStatus.CREATED);
    assertTrue(added2.getStatus() == BillStatus.CREATED);
    List<Citizen> citizens = citizenRepository.findAll();
    int count = 0;
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
    // When
    List<BillDTO> openToSupportBills = supportBillService.getOpenToSupportBills();

    // Then
    assertEquals(1, openToSupportBills.size());
    assertEquals("Bill 2", openToSupportBills.get(0).getTitle());
  }

  @Test
  void testSupportBillStatus() {
    Delegate delegate1 = delegateRepository.findByName("Delegate 1");
    Topic topic = topicRepository.findByName("Education");
    Bill added1 =
        delegate1.proposeBill(
            "Bill 1", "null", new byte[] {}, LocalDateTime.of(2023, 11, 5, 0, 0, 0, 0), topic);
    Bill added2 =
        delegate1.proposeBill(
            "Bill 2", "null", new byte[] {}, LocalDateTime.of(2023, 10, 5, 0, 0, 0, 0), topic);
    assertTrue(added1.getStatus() == BillStatus.CREATED);
    assertTrue(added2.getStatus() == BillStatus.CREATED);
    List<Citizen> citizens = citizenRepository.findAll();
    assertTrue(added2.supportBill(citizens.get(0)));

    int count = 0;
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

    assertEquals(1, added2.getSupporters().size());
    assertTrue(added2.getStatus() == BillStatus.CREATED);

    assertEquals(10000, added1.getSupporters().size());
    assertTrue(added1.getStatus() == BillStatus.VOTING);
  }

  @Test
  void testSupportBillProponentVote() {
    Delegate delegate1 = delegateRepository.findByName("Delegate 1");
    Topic topic = topicRepository.findByName("Education");
    Bill added1 =
        delegate1.proposeBill(
            "Bill 1", "null", new byte[] {}, LocalDateTime.of(2023, 11, 5, 0, 0, 0, 0), topic);
    assertTrue(added1.getStatus() == BillStatus.CREATED);
    List<Citizen> citizens = citizenRepository.findAll();

    int count = 0;
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

    assertTrue(added1.getVoteBox().getPublicCastVote(delegate1).get());
  }

  @Test
  void testSupportBillClampValidity() {
    LocalDateTime now = LocalDateTime.now();

    // Given
    Delegate delegate1 = delegateRepository.findByName("Delegate 1");
    Topic topic = topicRepository.findByName("Education");
    Bill added1 = delegate1.proposeBill("Bills 1", "null", new byte[] {}, now.plusMonths(5), topic);
    assertTrue(added1.getStatus() == BillStatus.CREATED);
    List<Citizen> citizens = citizenRepository.findAll();

    int count = 0;
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
    Delegate delegate1 = delegateRepository.findByName("Delegate 1");
    Topic topic = topicRepository.findByName("Education");
    Bill added1 = delegate1.proposeBill("Bill 1", "null", new byte[] {}, now.plusDays(10), topic);
    assertTrue(added1.getStatus() == BillStatus.CREATED);
    List<Citizen> citizens = citizenRepository.findAll();

    int count = 0;
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
    Delegate delegate1 = delegateRepository.findByName("Delegate 1");
    Topic topic = topicRepository.findByName("Education");
    Bill added1 = delegate1.proposeBill("Bill 1", "null", new byte[] {}, now.minusDays(10), topic);
    assertTrue(added1 == null);
  }
}
