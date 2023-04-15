package pt.ul.fc.css.democracia2;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.democracia2.DTO.DelegateDTO;
import pt.ul.fc.css.democracia2.DTO.TopicDTO;
import pt.ul.fc.css.democracia2.domain.Citizen;
import pt.ul.fc.css.democracia2.domain.Delegate;
import pt.ul.fc.css.democracia2.domain.Topic;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;
import pt.ul.fc.css.democracia2.repositories.DelegateRepository;
import pt.ul.fc.css.democracia2.repositories.TopicRepository;

@Service
@Transactional
public class DelegateSelectionService {
  @Autowired private DelegateRepository delegateRepository;
  @Autowired private TopicRepository topicRepository;
  @Autowired private CitizenRepository citizenRepository;

  @Autowired
  public DelegateSelectionService(
      DelegateRepository delegateRepository,
      TopicRepository topicRepository,
      CitizenRepository citizenRepository) {
    this.delegateRepository = delegateRepository;
    this.topicRepository = topicRepository;
    this.citizenRepository = citizenRepository;
  }

  @Autowired
  public List<TopicDTO> getTopic() {
    return topicRepository.findAll().stream()
        .map(topic -> new TopicDTO(topic))
        .collect(Collectors.toList());
  }

  @Autowired
  public List<DelegateDTO> getDelegate() {
    return delegateRepository.findAll().stream()
        .map(delegate -> new DelegateDTO(delegate))
        .collect(Collectors.toList());
  }

  public void chooseDelegate(Long citizen_token, Long delegateCC, String topic_id) {
    Optional<Citizen> citizen = citizenRepository.findByToken(citizen_token);
    Optional<Delegate> delegate = delegateRepository.findById(delegateCC);
    Optional<Topic> topic = topicRepository.findByName(topic_id);

    if (!citizen.isEmpty() && !delegate.isEmpty() && !topic.isEmpty()) {
      citizen.get().chooseDelegate(delegate.get(), topic.get());
      citizenRepository.save(citizen.get());
    }
  }
}
