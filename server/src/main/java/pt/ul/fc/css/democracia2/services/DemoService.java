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

/**
 * Class responsible for initiating votable and supportable Bills
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
@Service
@Transactional
public class DemoService {
  @Autowired private CitizenRepository citizenRepository;
  @Autowired private DelegateRepository delegateRepository;
  @Autowired private TopicRepository topicRepository;
  @Autowired private BillRepository billRepository;

  /** Method that initiates votable Bills */
  public void initVotableBills() {
    List<Delegate> delegates = delegateRepository.findAll();
    List<Topic> topics = topicRepository.findAll();
    // Demo Data generated with ChatGPT
    List<String> titles =
        Arrays.asList(
            "Healthy Food Access for All Act",
            "STEM Education Enhancement Act",
            "Climate Resilience and Adaptation Act");
    List<String> descriptions =
        Arrays.asList(
            "This bill aims to improve access to healthy and affordable food in underserved communities by providing grants and incentives to grocery stores, farmers' markets, and community organizations that offer fresh and nutritious food options.",
            "This bill aims to enhance science, technology, engineering, and mathematics (STEM) education by providing resources and training for teachers, promoting hands-on learning experiences, and increasing opportunities for underrepresented groups in STEM fields.",
            "This bill addresses the challenges posed by climate change by enhancing the resilience of communities and infrastructure. It includes measures to improve disaster preparedness, protect natural resources, and promote sustainable land and water management practices.");

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

  /** Method that initiates supportable Bills */
  public void initSupportableBills() {
    List<Delegate> delegates = delegateRepository.findAll();
    List<Topic> topics = topicRepository.findAll();
    // Demo Data generated with ChatGPT
    List<String> titles =
        Arrays.asList(
            "Mental Health Parity and Addiction Equity Act",
            "Every Student Succeeds Act (ESSA) Reauthorization");
    List<String> descriptions =
        Arrays.asList(
            "This bill seeks to strengthen mental health and addiction treatment by ensuring that health insurance plans provide equal coverage for mental health and substance abuse disorders as they do for physical health conditions.",
            " This bill proposes to reauthorize and update the Every Student Succeeds Act, which governs K-12 education policy. It includes provisions to promote educational equity, support effective teaching practices, and expand access to high-quality early childhood education.");

    int index = 0;
    for (Delegate delegate : delegates) {
      Topic topic = topics.get(index);
      String title = titles.get(index);
      String description = descriptions.get(index);

      Bill bill =
          delegate.proposeBill(
              title, description, new byte[] {}, LocalDateTime.now().plusMonths(5), topic);

      billRepository.save(bill);

      index++;
      if (index > 1) {
        break;
      }
    }
  }
}
