package pt.ul.fc.css.democracia2;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.democracia2.domain.Citizen;
import pt.ul.fc.css.democracia2.domain.Delegate;
import pt.ul.fc.css.democracia2.domain.Topic;
import pt.ul.fc.css.democracia2.repositories.BillRepository;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;
import pt.ul.fc.css.democracia2.repositories.DelegateRepository;
import pt.ul.fc.css.democracia2.repositories.TopicRepository;
import pt.ul.fc.css.democracia2.services.ProposeBillService;

/**
 * Class that initializes all the demo data
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
@Component
public class DemoDataInitializer {

  private static final Logger log = LoggerFactory.getLogger(DemoDataInitializer.class);

  private final CitizenRepository citizenRepository;
  private final DelegateRepository delegateRepository;
  private final TopicRepository topicRepository;

  @Autowired
  public DemoDataInitializer(
      CitizenRepository citizenRepository,
      DelegateRepository delegateRepository,
      BillRepository billRepository,
      TopicRepository topicRepository,
      ProposeBillService proposeBillService) {
    this.citizenRepository = citizenRepository;
    this.delegateRepository = delegateRepository;
    this.topicRepository = topicRepository;
  }

  /**
   * Method that initiates 100 Delegates
   */
  private void initDelegates() {
    // Create 100 delegates with unique names, CCs, and tokens
    for (int i = 1; i <= 100; i++) {
      String name = "Delegate " + i;
      long cc = 2000000000L + i;

      Delegate delegate = new Delegate(name, cc);
      delegateRepository.save(delegate);
    }
  }

  /**
   * Method that initiates 15000 Citizens
   */
  private void initCitizens() {
    // Create 15000 citizens with unique names, CCs, and tokens
    for (int i = 1; i <= 15000; i++) {
      String name = "Citizen " + i;
      long cc = 1000000000L + i;

      Citizen citizen = new Citizen(name, cc);
      citizenRepository.save(citizen);
    }
  }

  /**
   * Method that initiates 5 Topics with 2 subtopics each
   */
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

  /**
   * Method that initializes all the data calling this class methods
   */
  public void initialize() throws IOException {
    // Initialization logic goes here
    // Example: populate database with initial data
    log.info("Initializing demo data...");
    initTopics();
    initCitizens();
    initDelegates();
  }
}
