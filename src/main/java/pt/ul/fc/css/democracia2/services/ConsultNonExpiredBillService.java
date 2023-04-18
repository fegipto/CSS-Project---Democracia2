package pt.ul.fc.css.democracia2.services;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.democracia2.DTO.BillDTO;
import pt.ul.fc.css.democracia2.domain.Bill;
import pt.ul.fc.css.democracia2.domain.BillStatus;
import pt.ul.fc.css.democracia2.repositories.BillRepository;

/**
 * Class responsible for consulting non expired bills
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
@Service
@Transactional
public class ConsultNonExpiredBillService {
  @Autowired private BillRepository billRepository;

  /**
   * Contructs a new ConsultNonExpiredBillService object using a bill repository
   *
   * @param billRepository the bill repository necessary for this service
   */
  @Autowired
  public ConsultNonExpiredBillService(BillRepository billRepository) {
    this.billRepository = billRepository;
  }

  /**
   * Method that lists all the bills available with non-expired validity
   *
   * @return a list of all the bills available with non-expired validity
   */
  public List<BillDTO> listNonExpired() {
    return billRepository.getBillsNotInStatus(BillStatus.EXPIRED).stream()
        .map(bill -> new BillDTO(bill))
        .collect(Collectors.toList());
  }

  public Optional<BillDTO> getBill(Long id) {
    Optional<Bill> bill = billRepository.findById(id);
    return (bill.isPresent()) ? Optional.of(new BillDTO(bill.get())) : Optional.empty();
  }
}
