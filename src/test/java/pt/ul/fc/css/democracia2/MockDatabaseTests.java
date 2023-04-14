package pt.ul.fc.css.democracia2;

import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import pt.ul.fc.css.democracia2.domain.Citizen;
import pt.ul.fc.css.democracia2.domain.Delegate;
import pt.ul.fc.css.democracia2.domain.Topic;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;
import pt.ul.fc.css.democracia2.repositories.DelegateRepository;
import pt.ul.fc.css.democracia2.repositories.TopicRepository;

@TestInstance(Lifecycle.PER_CLASS)
public class MockDatabaseTests {
  @Autowired private CitizenRepository citizenRepository;
  @Autowired private DelegateRepository delegateRepository;
  @Autowired private TopicRepository topicRepository;

  @BeforeAll
  void initializeDatabase() {
    initTopics();
    initCitizens();
    initDelegates();
  }

  private void initDelegates() {
    // Create 15000 citizens with unique names, CCs, and tokens
    for (int i = 1; i <= 100; i++) {
      String name = "Delegate " + i;
      long cc = 2000000000L + i;

      Delegate delegate = new Delegate(name, cc);
      delegateRepository.save(delegate);
    }
  }

  private void initCitizens() {
    // Create 15000 citizens with unique names, CCs, and tokens
    for (int i = 1; i <= 15000; i++) {
      String name = "Citizen " + i;
      long cc = 1000000000L + i;

      Citizen citizen = new Citizen(name, cc);
      citizenRepository.save(citizen);
    }
  }

  private void initTopics() {
    // topic names courtesy of chatGPT
    List<Topic> topics = new LinkedList<>();

    // Create 5 root topics
    Topic topic1 = new Topic("Health");
    topicRepository.save(topic1);
    topics.add(topic1);

    Topic topic2 = new Topic("Education");
    topicRepository.save(topic2);
    topics.add(topic2);

    Topic topic3 = new Topic("Environment");
    topicRepository.save(topic3);
    topics.add(topic3);

    Topic topic4 = new Topic("Economy");
    topicRepository.save(topic4);
    topics.add(topic4);

    Topic topic5 = new Topic("Foreign Affairs");
    topicRepository.save(topic5);
    topics.add(topic5);

    // Create 2 subtopics for each root topic
    for (int i = 0; i < 5; i++) {
      Topic parent = topics.get(i);
      for (int j = 0; j < 2; j++) {
        Topic subtopic = new Topic(parent.getName() + " - Subtopic " + j, parent);
        topicRepository.save(subtopic);
      }
    }
  }

  @AfterAll
  void clearDatabase() {
    citizenRepository.deleteAll();
    topicRepository.deleteAll();
  }
}
