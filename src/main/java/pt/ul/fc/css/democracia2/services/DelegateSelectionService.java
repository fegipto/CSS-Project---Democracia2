package pt.ul.fc.css.democracia2.services;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.democracia2.DTO.DelegateDTO;
import pt.ul.fc.css.democracia2.domain.Citizen;
import pt.ul.fc.css.democracia2.domain.Delegate;
import pt.ul.fc.css.democracia2.domain.Topic;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;
import pt.ul.fc.css.democracia2.repositories.DelegateRepository;
import pt.ul.fc.css.democracia2.repositories.TopicRepository;

/**
 * Class responsible for selecting a delegate
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
@Service
@Transactional
public class DelegateSelectionService {
  private DelegateRepository delegateRepository;
  private TopicRepository topicRepository;
  private CitizenRepository citizenRepository;

  /**
   * Contructs a new DelegateSelectionService object using a delegate repository, topic repository, citizen repository
   *
   * @param delegateRepository the delegate repository necessary for this service
   * @param topicRepository the topic repository necessary for this service
   * @param citizenRepository the citizen repository necessary for this service
   */
  @Autowired
  public DelegateSelectionService(
      DelegateRepository delegateRepository,
      TopicRepository topicRepository,
      CitizenRepository citizenRepository) {
    this.delegateRepository = delegateRepository;
    this.topicRepository = topicRepository;
    this.citizenRepository = citizenRepository;
  }

  /**
   * Method that gets all delegates
   *
   * @return a list of all delegates
   */
  @Autowired
  public List<DelegateDTO> getDelegates() {
    return delegateRepository.findAll().stream()
        .map(delegate -> new DelegateDTO(delegate))
        .collect(Collectors.toList());
  }

  /**
   * Method that allows a citizen to choose a delegate for a specific topic
   *
   * @param citizen_token the authentication toke of the citizen that wants to choose a delegate
   * @param delegateCC the cc of the delegate
   * @param topic_id the id of the topic
   */
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
