package pt.ul.fc.css.democracia2.services;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.democracia2.DTO.BillDTO;
import pt.ul.fc.css.democracia2.domain.BillStatus;
import pt.ul.fc.css.democracia2.repositories.BillRepository;

/**
 * Class responsible for listing all bills available to vote
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
@Service
@Transactional
public class ListAvailableVotesService {
  @Autowired private BillRepository billRepository;

  /**
   * Contructs a new ListAvailableVotesService object using a bill repository
   *
   * @param billRepository the bill repository necessary for this service
   */
  @Autowired
  public ListAvailableVotesService(BillRepository billRepository) {
    this.billRepository = billRepository;
  }

  /**
   * Method that lists the bills available to vote in
   *
   * @return the list the bills available to vote in
   */
  @Autowired
  public List<BillDTO> listAvailableVotes() {
    return billRepository.getBillsByStatus(BillStatus.VOTING).stream()
        .map(bill -> new BillDTO(bill))
        .collect(Collectors.toList());
  }
}
