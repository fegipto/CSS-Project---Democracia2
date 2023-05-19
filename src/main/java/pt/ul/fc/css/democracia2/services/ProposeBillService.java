package pt.ul.fc.css.democracia2.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import pt.ul.fc.css.democracia2.DTO.BillDTO;
import pt.ul.fc.css.democracia2.domain.Bill;
import pt.ul.fc.css.democracia2.domain.Delegate;
import pt.ul.fc.css.democracia2.domain.Topic;
import pt.ul.fc.css.democracia2.repositories.BillRepository;
import pt.ul.fc.css.democracia2.repositories.DelegateRepository;
import pt.ul.fc.css.democracia2.repositories.TopicRepository;

/**
 * Class responsible for proposing new bills
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
@Service
@Transactional
public class ProposeBillService {
  private TopicRepository topicRepository;
  private DelegateRepository delegateRepository;
  private BillRepository billRepository;

  /**
   * Contructs a new ProposeBillService object using a topic repository, delegate repository, bill
   * repository
   *
   * @param topicRepository the topic repository necessary for this service
   * @param delegateRepository the delegate repository necessary for this service
   * @param billRepository the bill repository necessary for this service
   */
  @Autowired
  public ProposeBillService(
      TopicRepository topicRepository,
      DelegateRepository delegateRepository,
      BillRepository billRepository) {
    this.topicRepository = topicRepository;
    this.delegateRepository = delegateRepository;
    this.billRepository = billRepository;
  }

  /**
   * Method that allows a delegate to present a new bill
   *
   * @param billDTO bill info
   * @throws IOException
   */
  public BillDTO presentBill(BillDTO billDTO)
      throws IOException {
    Optional<Delegate> delegate = delegateRepository.findByToken(billDTO.getProponentId());

    if (!delegate.isPresent()) {
      throw new IllegalArgumentException(
          "Delegate with token " + billDTO.getProponentId() + " is not found.");
    }

    Optional<Topic> billTopic = topicRepository.findById(billDTO.getTopicId());
    if (billTopic.isEmpty()) {
      throw new IllegalArgumentException(
          "Topic with id " + billDTO.getProponentId() + " is not found.");
    }
    Bill added = delegate.get().proposeBill(billDTO.getTitle(), billDTO.getDescription(),
     billDTO.getFile(), billDTO.getValidity(), billTopic.get());

    if (added != null)
      // save in bill table since it owns the fk
      billRepository.save(added);

    return new BillDTO(added);
  }

  /**
   * Method that gets all the topics available
   *
   * @return a list topics available
   */
  @Autowired
  public List<Topic> getTopics() {
    return topicRepository.getTopics();
  }
}
