package pt.ul.fc.css.democracia2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pt.ul.fc.css.democracia2.DTO.CitizenDTO;
import pt.ul.fc.css.democracia2.domain.Citizen;
import pt.ul.fc.css.democracia2.domain.Delegate;
import pt.ul.fc.css.democracia2.domain.Topic;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;
import pt.ul.fc.css.democracia2.repositories.DelegateRepository;
import pt.ul.fc.css.democracia2.repositories.TopicRepository;
import pt.ul.fc.css.democracia2.services.DelegateSelectionService;

@SpringBootTest
@Transactional
public class DelegateSelectionServiceTests extends MockDatabaseTests {

  @Autowired private DelegateSelectionService delegateSelectionService;

  @Autowired private CitizenRepository citizenRepository;

  @Autowired private DelegateRepository delegateRepository;

  @Autowired private TopicRepository topicRepository;

  @Test
  public void testChooseDelegate() {
    Optional<Citizen> citizen1 = citizenRepository.findByName("Citizen 1");
    Optional<Topic> topic = topicRepository.findByName("Education");
    Optional<Delegate> delegate1 = delegateRepository.findByName("Delegate 1");
    assertTrue(citizen1.isPresent());
    assertTrue(topic.isPresent());
    assertTrue(delegate1.isPresent());

    delegateSelectionService.chooseDelegate(
        citizen1.get().getToken(), delegate1.get().getCC(), topic.get().getName());

    assertEquals(delegate1.get(), citizen1.get().getChosenDelegate(topic.get()));
  }

  @Test
  public void testChooseDelegateSpecificTopic() {
    Optional<Citizen> citizen1 = citizenRepository.findByName("Citizen 1");
    Optional<Topic> topic = topicRepository.findByName("Education - Subtopic 0");
    Optional<Delegate> delegate1 = delegateRepository.findByName("Delegate 1");
    assertTrue(citizen1.isPresent());
    assertTrue(topic.isPresent());
    assertTrue(delegate1.isPresent());

    delegateSelectionService.chooseDelegate(
        citizen1.get().getToken(), delegate1.get().getCC(), topic.get().getName());

    assertEquals(delegate1.get(), citizen1.get().getChosenDelegate(topic.get()));
  }

  @Test
  public void testGetDelegates() {
    List<CitizenDTO> delegates = delegateSelectionService.getDelegates();
    assertNotNull(delegates);
    assertFalse(delegates.isEmpty());
    assertEquals(100, delegates.size());
  }
}
