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

@Service
@Transactional
public class ConsultNonExpiredBillService {
  @Autowired private BillRepository billRepository;

  @Autowired
  public ConsultNonExpiredBillService(BillRepository billRepository) {
    this.billRepository = billRepository;
  }

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
