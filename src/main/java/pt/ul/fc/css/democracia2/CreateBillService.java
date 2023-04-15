package pt.ul.fc.css.democracia2;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.democracia2.DTO.TopicDTO;
import pt.ul.fc.css.democracia2.domain.Delegate;
import pt.ul.fc.css.democracia2.domain.Topic;
import pt.ul.fc.css.democracia2.repositories.BillRepository;
import pt.ul.fc.css.democracia2.repositories.TopicRepository;

@Service
@Transactional
public class CreateBillService {
  @Autowired private BillRepository billRepository;
  @Autowired private TopicRepository topicRepository;
  private Delegate delegate;
  private String title;
  private String desc;
  private LocalDateTime validity;
  private Topic topic;
  private byte[] file;

  @Autowired
  public CreateBillService(BillRepository billRepository, TopicRepository topicRepository) {
    this.billRepository = billRepository;
    this.topicRepository = topicRepository;
  }

  public void presentBill(Delegate delegate, String title, String desc, LocalDateTime validity) {
    this.delegate = delegate;
    this.title = title;
    this.desc = desc;
    LocalDateTime yearAfter = LocalDateTime.now().plusYears(1);
    if (validity.isAfter(yearAfter)) {
      this.validity = yearAfter;
    } else {
      this.validity = validity;
    }
  }

  @Autowired
  public List<TopicDTO> getTopics() {
    return topicRepository.getTopics().stream().map(TopicDTO::new).collect(Collectors.toList());
  }

  public void chooseTopic(String topic) {
    this.topic = topicRepository.findByName(topic).get();
  }

  public void appendFile(byte[] file) {
    this.file = file;
  }

  public void submit() {
    billRepository.save(delegate.proposeBill(title, desc, file, validity, topic));
  }
}
