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
import pt.ul.fc.css.democracia2.domain.Delegate;
import pt.ul.fc.css.democracia2.domain.Topic;
import pt.ul.fc.css.democracia2.repositories.BillRepository;
import pt.ul.fc.css.democracia2.repositories.DelegateRepository;
import pt.ul.fc.css.democracia2.repositories.TopicRepository;

@Service
@Transactional
public class ProposeBillService {
  private TopicRepository topicRepository;
  private DelegateRepository delegateRepository;
  private BillRepository billRepository;

  @Autowired
  public ProposeBillService(
      TopicRepository topicRepository,
      DelegateRepository delegateRepository,
      BillRepository billRepository) {
    this.topicRepository = topicRepository;
    this.delegateRepository = delegateRepository;
    this.billRepository = billRepository;
  }

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

    LocalDateTime oneYearLater = LocalDateTime.now().plusYears(1);
    if (validity.isAfter(oneYearLater)) {
      throw new IllegalArgumentException("Validity cannot be more than one year in the future.");
    }

    // save in bill table since it owns the fk
    billRepository.save(
        delegate.get().proposeBill(title, desc, file.getBytes(), validity, topic.get()));
  }

  @Autowired
  public List<TopicDTO> getTopics() {
    return topicRepository.getTopics().stream().map(TopicDTO::new).collect(Collectors.toList());
  }
}
