package pt.ul.fc.css.democracia2.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pt.ul.fc.css.democracia2.DTO.BillDTO;
import pt.ul.fc.css.democracia2.DTO.CitizenDTO;
import pt.ul.fc.css.democracia2.domain.Delegate;
import pt.ul.fc.css.democracia2.domain.Topic;
import pt.ul.fc.css.democracia2.repositories.DelegateRepository;
import pt.ul.fc.css.democracia2.repositories.TopicRepository;

/**
 * Class that tests the ProposeBillService
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
@SpringBootTest
@Transactional
class ProposeBillServiceTests {
  @Autowired private ProposeBillService proposeBillService;

  @Autowired private TopicRepository topicRepository;
  @Autowired private DelegateRepository delegateRepository;

  @Test
  void testPresentBill() throws IOException {
    byte[] fileContent = "test".getBytes();
    LocalDateTime validity = LocalDateTime.now().plusMonths(6);
    Topic topic = topicRepository.findAll().get(0);
    Delegate delegate = delegateRepository.findAll().get(0);
    BillDTO bill = new BillDTO();
    bill.setProponent(new CitizenDTO(delegate));
    bill.setTitle("Test Title");
    bill.setDescription("Test Description");
    bill.setFile(fileContent);
    bill.setValidity(validity);
    bill.setTopic(topic);
    proposeBillService.presentBill(bill);

    Delegate updatedDelegate = delegateRepository.findById(delegate.getCC()).orElse(null);
    assertEquals(1, updatedDelegate.getBills().size());
    assertEquals("Test Title", updatedDelegate.getBills().get(0).getTitle());
  }

  @Test
  void testPresentBillInvalidId() throws IOException {
    byte[] fileContent = "This is a test file".getBytes();
    LocalDateTime validity = LocalDateTime.now().plusMonths(6);
    Topic topic = topicRepository.findAll().get(0);

    BillDTO bill = new BillDTO();
    bill.setProponent(new CitizenDTO(new Delegate(null, -100)));
    bill.setTitle("Test Title");
    bill.setDescription("Test Description");
    bill.setFile(fileContent);
    bill.setValidity(validity);
    bill.setTopic(topic);
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          proposeBillService.presentBill(bill);
        });
  }

  @Test
  void testPresentBillInvalidTopic() throws IOException {
    byte[] fileContent = "This is a test file".getBytes();
    LocalDateTime validity = LocalDateTime.now().plusMonths(6);
    BillDTO bill = new BillDTO();
    Delegate delegate = delegateRepository.findAll().get(0);

    bill.setProponent(new CitizenDTO(delegate));
    bill.setTitle("Test Title");
    bill.setDescription("Test Description");
    bill.setFile(fileContent);
    bill.setValidity(validity);
    Topic invalid = new Topic("inv", null);
    invalid.setId((long) -1);
    bill.setTopic(invalid);
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          proposeBillService.presentBill(bill);
        });
  }

  @Test
  void testPresentBillInvalidValidityOver2Y() throws IOException {
    byte[] fileContent = "This is a test file".getBytes();
    LocalDateTime validity = LocalDateTime.now().plusYears(2);
    Topic topic = topicRepository.findAll().get(0);
    Delegate delegate = delegateRepository.findAll().get(0);

    BillDTO bill = new BillDTO();
    bill.setProponent(new CitizenDTO(delegate));
    bill.setTitle("Test Title");
    bill.setDescription("Test Description");
    bill.setFile(fileContent);
    bill.setValidity(validity);
    bill.setTopic(topic);
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          proposeBillService.presentBill(bill);
        });
  }

  @Test
  void testPresentBillInvalidValidityPast() throws IOException {
    byte[] fileContent = "This is a test file".getBytes();
    LocalDateTime validity = LocalDateTime.now().plusYears(2);
    Topic topic = topicRepository.findAll().get(0);
    BillDTO bill = new BillDTO();
    Delegate delegate = delegateRepository.findAll().get(0);

    bill.setProponent(new CitizenDTO(delegate));
    bill.setTitle("Test Title");
    bill.setDescription("Test Description");
    bill.setFile(fileContent);
    bill.setValidity(validity);
    bill.setTopic(topic);
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          proposeBillService.presentBill(bill);
        });
  }

  @Test
  void testGetTopics() {
    List<Topic> topics = proposeBillService.getTopics();
    // 5 roots e 2 para cada root
    assertEquals(5 + 5 * 2, topics.size());
  }
}
