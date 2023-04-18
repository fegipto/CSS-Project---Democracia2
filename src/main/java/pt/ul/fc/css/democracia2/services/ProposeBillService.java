package pt.ul.fc.css.democracia2.services;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pt.ul.fc.css.democracia2.DTO.TopicDTO;
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
   * @param delegate_token the authentication token of the delegate that wants to present a new bill
   * @param title the title of the bill
   * @param desc the description of the bill
   * @param file the file of the bill
   * @param validity the validity of the bill
   * @param topic_id the id of the topic of the bill
   * @throws IOException
   */
  public void presentBill(
      long delegate_token,
      String title,
      String desc,
      MultipartFile file,
      LocalDateTime validity,
      long topic_id)
      throws IOException {
    Optional<Delegate> delegate = delegateRepository.findByToken(delegate_token);
    Optional<Topic> topic = topicRepository.findById(topic_id);

    if (!delegate.isPresent()) {
      throw new IllegalArgumentException(
          "Delegate with token " + delegate_token + " is not found.");
    }

    if (!topic.isPresent()) {
      throw new IllegalArgumentException("Topic with id " + topic_id + " is not found.");
    }

    Bill added = delegate.get().proposeBill(title, desc, file.getBytes(), validity, topic.get());

    if (added != null)
      // save in bill table since it owns the fk
      billRepository.save(added);
  }

  /**
   * Method that gets all the topics available
   *
   * @return a list topics available
   */
  @Autowired
  public List<TopicDTO> getTopics() {
    return topicRepository.getTopics().stream().map(TopicDTO::new).collect(Collectors.toList());
  }
}
