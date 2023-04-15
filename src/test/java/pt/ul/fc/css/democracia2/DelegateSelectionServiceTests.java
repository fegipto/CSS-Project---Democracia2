package pt.ul.fc.css.democracia2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pt.ul.fc.css.democracia2.DTO.DelegateDTO;
import pt.ul.fc.css.democracia2.DTO.TopicDTO;
import pt.ul.fc.css.democracia2.domain.Citizen;
import pt.ul.fc.css.democracia2.domain.Delegate;
import pt.ul.fc.css.democracia2.domain.Topic;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;
import pt.ul.fc.css.democracia2.repositories.DelegateRepository;
import pt.ul.fc.css.democracia2.repositories.TopicRepository;

@SpringBootTest
@Transactional
public class DelegateSelectionServiceTests extends MockDatabaseTests {

  @Autowired private DelegateSelectionService delegateSelectionService;

  @Autowired private CitizenRepository citizenRepository;

  @Autowired private DelegateRepository delegateRepository;

  @Autowired private TopicRepository topicRepository;

  @Test
  public void testChooseDelegate() {
    Citizen citizen1 = citizenRepository.findByName("Citizen 1");
    Topic topic = topicRepository.findByName("Education");
    Delegate delegate1 = delegateRepository.findByName("Delegate 1");

    delegateSelectionService.chooseDelegate(
        citizen1.getToken(), delegate1.getCC(), topic.getName());

    Citizen updatedCitizen = citizenRepository.findByToken(citizen1.getToken());
    assertNotNull(updatedCitizen.getChosenDelegate(topic));
    assertEquals(delegate1, updatedCitizen.getChosenDelegate(topic));
  }

  @Test
  public void testGetDelegate() {
    List<DelegateDTO> delegates = delegateSelectionService.getDelegate();
    assertNotNull(delegates);
    assertFalse(delegates.isEmpty());
    assertEquals(100, delegates.size());
  }

  @Test
  public void testGetTopic() {
    List<TopicDTO> topics = delegateSelectionService.getTopic();
    assertNotNull(topics);
    assertFalse(topics.isEmpty());
    assertEquals(15, topics.size());
  }
}
