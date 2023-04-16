package pt.ul.fc.css.democracia2;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pt.ul.fc.css.democracia2.domain.Bill;
import pt.ul.fc.css.democracia2.domain.Citizen;
import pt.ul.fc.css.democracia2.domain.Delegate;
import pt.ul.fc.css.democracia2.domain.Topic;
import pt.ul.fc.css.democracia2.repositories.BillRepository;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;
import pt.ul.fc.css.democracia2.repositories.DelegateRepository;
import pt.ul.fc.css.democracia2.repositories.TopicRepository;
import pt.ul.fc.css.democracia2.services.VotingService;

@SpringBootTest
@Transactional
public class VotingTests extends MockDatabaseTests {
  @Autowired private VotingService votingService;

  @Autowired private CitizenRepository citizenRepository;
  @Autowired private DelegateRepository delegateRepository;
  @Autowired private TopicRepository topicRepository;
  @Autowired private BillRepository billRepository;

  @BeforeEach
  void initVotableBill() {
    Optional<Delegate> delegate1 = delegateRepository.findByName("Delegate 1");
    Optional<Topic> topic = topicRepository.findByName("Education");
    assertTrue(delegate1.isPresent());
    assertTrue(topic.isPresent());

    Bill added1 =
        delegate1
            .get()
            .proposeBill(
                "Bill Votable",
                "null",
                new byte[] {},
                LocalDateTime.now().plusMonths(4),
                topic.get());
    List<Citizen> citizens = citizenRepository.findAll();
    int count = 0;
    for (Citizen cit : citizens) {
      added1.supportBill(cit);
      if (count == 10000) {

        break;
      }
      count++;
    }
    billRepository.save(added1);
  }

  @Test
  void testGetOmittedVote() {

    Citizen cit = citizenRepository.findAll().get(0);
    Optional<Delegate> delegate1 = delegateRepository.findByName("Delegate 1");
    Optional<Topic> topic = topicRepository.findByName("Education");
    assertTrue(delegate1.isPresent());
    assertTrue(topic.isPresent());

    Bill bill = delegate1.get().getBills().get(0);
    assertTrue(votingService.getOmmitedVote(cit.getToken(), bill.getId()).isEmpty());

    cit.chooseDelegate(delegate1.get(), topic.get());

    // call the service method and verify the result
    Optional<Boolean> result = votingService.getOmmitedVote(cit.getToken(), bill.getId());
    assertTrue(result.isPresent());
    assertTrue(result.get());
  }

  @Test
  void testGetOmittedVoteWithInvalidBillId() {
    // call the service method with an invalid bill id and verify the result
    Optional<Boolean> result = votingService.getOmmitedVote(1L, 999L);
    assertFalse(result.isPresent());
  }

  @Test
  void testGetOmittedVoteOnSubTopic() {

    Citizen cit = citizenRepository.findAll().get(0);
    Optional<Delegate> delegate1 = delegateRepository.findByName("Delegate 1");
    Optional<Topic> topic = topicRepository.findByName("Education - Subtopic 0");
    assertTrue(delegate1.isPresent());
    assertTrue(topic.isPresent());

    Bill bill = delegate1.get().getBills().get(0);
    assertTrue(votingService.getOmmitedVote(cit.getToken(), bill.getId()).isEmpty());

    cit.chooseDelegate(delegate1.get(), topic.get());

    // call the service method and verify the result
    Optional<Boolean> result = votingService.getOmmitedVote(cit.getToken(), bill.getId());
    assertFalse(result.isPresent());
  }

  @Test
  void testGetOmittedVoteOnBillSubTopic() {
    Optional<Delegate> delegate1 = delegateRepository.findByName("Delegate 1");
    Optional<Topic> topic = topicRepository.findByName("Education - Subtopic 0");
    assertTrue(delegate1.isPresent());
    assertTrue(topic.isPresent());
    Bill specificBill =
        delegate1
            .get()
            .proposeBill(
                "Bill Votable",
                "null",
                new byte[] {},
                LocalDateTime.now().plusMonths(4),
                topic.get());
    List<Citizen> citizens = citizenRepository.findAll();
    int count = 0;
    for (Citizen cit : citizens) {
      specificBill.supportBill(cit);
      if (count == 10000) {

        break;
      }
      count++;
    }
    billRepository.save(specificBill);

    Citizen cit = citizenRepository.findAll().get(0);

    Optional<Topic> topicGeneral = topicRepository.findByName("Education");
    assertTrue(topicGeneral.isPresent());

    assertTrue(votingService.getOmmitedVote(cit.getToken(), specificBill.getId()).isEmpty());

    cit.chooseDelegate(delegate1.get(), topicGeneral.get());

    // call the service method and verify the result
    Optional<Boolean> result = votingService.getOmmitedVote(cit.getToken(), specificBill.getId());
    assertTrue(result.isPresent());
    assertTrue(result.get());
  }

  @Test
  void testVoteCitizen() {

    Optional<Delegate> delegate1 = delegateRepository.findByName("Delegate 1");
    Optional<Topic> topic = topicRepository.findByName("Education - Subtopic 0");
    assertTrue(delegate1.isPresent());
    assertTrue(topic.isPresent());
    Optional<Bill> votable = billRepository.findByTitle("Bill Votable");
    assertTrue(votable.isPresent());
    Citizen cit = citizenRepository.findAll().get(0);
    Bill nonVotable =
        delegate1
            .get()
            .proposeBill(
                "Bill Non Votable",
                "null",
                new byte[] {},
                LocalDateTime.now().plusMonths(4),
                topic.get());

    assertFalse(votingService.vote(cit.getToken(), nonVotable.getId(), false));
    assertTrue(votingService.vote(cit.getToken(), votable.get().getId(), false));

    assertTrue(votable.get().getVoteBox().hasVoted(cit));
  }

  @Test
  void testVoteDelegate() {
    Optional<Delegate> delegate1 = delegateRepository.findByName("Delegate 1");
    Optional<Topic> topic = topicRepository.findByName("Education - Subtopic 0");
    Optional<Bill> votable = billRepository.findByTitle("Bill Votable");
    assertTrue(delegate1.isPresent());
    assertTrue(topic.isPresent());
    assertTrue(votable.isPresent());
    Bill nonVotable =
        delegate1
            .get()
            .proposeBill(
                "Bill Non Votable",
                "null",
                new byte[] {},
                LocalDateTime.now().plusMonths(4),
                topic.get());

    Optional<Delegate> delegate2 = delegateRepository.findByName("Delegate 2");

    assertTrue(delegate2.isPresent());
    assertFalse(votingService.vote(delegate2.get().getToken(), nonVotable.getId(), false));
    assertTrue(votingService.vote(delegate2.get().getToken(), votable.get().getId(), false));

    assertTrue(votable.get().getVoteBox().hasVoted(delegate2.get()));
    Optional<Boolean> vote = votable.get().getVoteBox().getPublicCastVote(delegate2.get());
    assertTrue(vote.isPresent());
    assertFalse(vote.get());
  }
}
