package pt.ul.fc.css.democracia2.services;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
  public BillDTO presentBill(BillDTO billDTO) throws IOException {
    Optional<Delegate> delegate = delegateRepository.findByToken(billDTO.getProponent().getToken());

    if (!delegate.isPresent()) {
      throw new IllegalArgumentException(
          "Delegate with token " + billDTO.getProponent().getToken() + " is not found.");
    }

    Optional<Topic> billTopic = topicRepository.findById(billDTO.getTopic().getId());
    if (billTopic.isEmpty()) {
      throw new IllegalArgumentException(
          "Topic with id " + billDTO.getTopic().getId() + " is not found.");
    }
    Bill added =
        delegate
            .get()
            .proposeBill(
                billDTO.getTitle(),
                billDTO.getDescription(),
                billDTO.getFile(),
                billDTO.getValidity(),
                billTopic.get());

    if (added != null)
      // save in bill table since it owns the fk
      billRepository.save(added);
    else throw new IllegalArgumentException("Invalid validity");
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
