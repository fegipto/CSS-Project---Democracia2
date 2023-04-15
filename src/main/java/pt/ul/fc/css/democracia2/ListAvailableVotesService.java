package pt.ul.fc.css.democracia2;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.democracia2.DTO.BillDTO;
import pt.ul.fc.css.democracia2.domain.BillStatus;
import pt.ul.fc.css.democracia2.repositories.BillRepository;

@Service
@Transactional
public class ListAvailableVotesService {
  @Autowired private BillRepository billRepository;

  @Autowired
  public ListAvailableVotesService(BillRepository billRepository) {
    this.billRepository = billRepository;
  }

  @Autowired
  public List<BillDTO> listAvailableVotes() {
    return billRepository.getBillsByStatus(BillStatus.VOTING).stream()
        .map(bill -> new BillDTO(bill))
        .collect(Collectors.toList());
  }
}
