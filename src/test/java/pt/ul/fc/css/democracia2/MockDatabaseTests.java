package pt.ul.fc.css.democracia2;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;

import pt.ul.fc.css.democracia2.repositories.BillRepository;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;
import pt.ul.fc.css.democracia2.repositories.TopicRepository;

@TestInstance(Lifecycle.PER_CLASS)
public class MockDatabaseTests {
  @Autowired private CitizenRepository citizenRepository;
  @Autowired private BillRepository billRepository;
  @Autowired private TopicRepository topicRepository;


}
