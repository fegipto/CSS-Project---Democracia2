package pt.ul.fc.css.democracia2.services;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.democracia2.domain.Bill;
import pt.ul.fc.css.democracia2.domain.Citizen;
import pt.ul.fc.css.democracia2.domain.Delegate;
import pt.ul.fc.css.democracia2.domain.Topic;
import pt.ul.fc.css.democracia2.repositories.BillRepository;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;
import pt.ul.fc.css.democracia2.repositories.DelegateRepository;
import pt.ul.fc.css.democracia2.repositories.TopicRepository;

@Service
@Transactional
public class DemoService {
  @Autowired private CitizenRepository citizenRepository;
  @Autowired private DelegateRepository delegateRepository;
  @Autowired private TopicRepository topicRepository;
  @Autowired private BillRepository billRepository;

  public void initVotableBills() {
    List<Delegate> delegates = delegateRepository.findAll();
    List<Topic> topics = topicRepository.findAll();
    List<String> titles = Arrays.asList("Bill Title 1", "Bill Title 2", "Bill Title 3");
    List<String> descriptions =
        Arrays.asList("Bill Description 1", "Bill Description 2", "Bill Description 3");

    int index = 0;
    for (Delegate delegate : delegates) {
      Topic topic = topics.get(index);
      String title = titles.get(index);
      String description = descriptions.get(index);

      Bill bill =
          delegate.proposeBill(
              title, description, new byte[] {}, LocalDateTime.now().plusMonths(4), topic);

      List<Citizen> citizens = citizenRepository.findAll();

      int count = 0;
      for (Citizen citizen : citizens) {
        bill.supportBill(citizen);
        count++;
        if (count == 10000) {
          break;
        }
      }

      billRepository.save(bill);

      index++;
      if (index > 2) {
        break;
      }
    }
  }
}
