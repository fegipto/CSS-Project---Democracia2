package pt.ul.fc.css.democracia2;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import pt.ul.fc.css.democracia2.DTO.TopicDTO;
import pt.ul.fc.css.democracia2.domain.*;
import pt.ul.fc.css.democracia2.repositories.DelegateRepository;
import pt.ul.fc.css.democracia2.repositories.TopicRepository;
import pt.ul.fc.css.democracia2.services.ProposeBillService;

@SpringBootTest
@Transactional
class ProposeBillServiceTests extends MockDatabaseTests {
  @Autowired private ProposeBillService proposeBillService;

  @Autowired private TopicRepository topicRepository;
  @Autowired private DelegateRepository delegateRepository;

  @Test
  void testPresentBill() throws IOException {
    byte[] fileContent = "test".getBytes();
    LocalDateTime validity = LocalDateTime.now().plusMonths(6);
    Topic topic = topicRepository.findAll().get(0);
    Delegate delegate = delegateRepository.findAll().get(0);

    proposeBillService.presentBill(
        delegate.getToken(),
        "Test Title",
        "Test Description",
        new MockMultipartFile("test-file", fileContent),
        validity,
        topic.getId());

    Delegate updatedDelegate = delegateRepository.findById(delegate.getCC()).orElse(null);
    assertEquals(1, updatedDelegate.getBills().size());
    assertEquals("Test Title", updatedDelegate.getBills().get(0).getTitle());
  }

  @Test
  void testPresentBillInvalidToken() throws IOException {
    byte[] fileContent = "This is a test file".getBytes();
    LocalDateTime validity = LocalDateTime.now().plusMonths(6);
    Topic topic = topicRepository.findAll().get(0);

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          proposeBillService.presentBill(
              9999999999L,
              "Test Title",
              "Test Description",
              new MockMultipartFile("test-file", fileContent),
              validity,
              topic.getId());
        });
  }

  @Test
  void testPresentBillInvalidTopic() throws IOException {
    byte[] fileContent = "This is a test file".getBytes();
    LocalDateTime validity = LocalDateTime.now().plusMonths(6);

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          proposeBillService.presentBill(
              2,
              "Test Title",
              "Test Description",
              new MockMultipartFile("test-file", fileContent),
              validity,
              9999L);
        });
  }

  @Test
  void testPresentBillInvalidValidityOver2Y() throws IOException {
    byte[] fileContent = "This is a test file".getBytes();
    LocalDateTime validity = LocalDateTime.now().plusYears(2);
    Topic topic = topicRepository.findAll().get(0);

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          proposeBillService.presentBill(
              1,
              "Test Title",
              "Test Description",
              new MockMultipartFile("test-file", fileContent),
              validity,
              topic.getId());
        });
  }

  @Test
  void testPresentBillInvalidValidityPast() throws IOException {
    byte[] fileContent = "This is a test file".getBytes();
    LocalDateTime validity = LocalDateTime.now().plusYears(2);
    Topic topic = topicRepository.findAll().get(0);

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          proposeBillService.presentBill(
              1,
              "Test Title",
              "Test Description",
              new MockMultipartFile("test-file", fileContent),
              validity,
              topic.getId());
        });
  }

  @Test
  void testGetTopics() {
    List<TopicDTO> topics = proposeBillService.getTopics();

    // 5 roots e 2 para cada root
    assertEquals(5 + 5 * 2, topics.size());
  }
}
