package pt.ul.fc.css.democracia2;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.democracia2.DTO.BillDTO;
import pt.ul.fc.css.democracia2.domain.Bill;
import pt.ul.fc.css.democracia2.domain.BillStatus;
import pt.ul.fc.css.democracia2.domain.Citizen;
import pt.ul.fc.css.democracia2.repositories.BillRepository;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;

@Service
@Transactional
public class SupportBillService {
  @Autowired private BillRepository billRepository;
  @Autowired private CitizenRepository citizenRepository;

  @Autowired
  public SupportBillService(BillRepository billRepository, CitizenRepository citizenRepository) {
    this.billRepository = billRepository;
    this.citizenRepository = citizenRepository;
  }

  @Autowired
  public List<BillDTO> getOpenToSupportBills() {
    return billRepository.getBillsByStatus(BillStatus.CREATED).stream()
        .map(bill -> new BillDTO(bill))
        .collect(Collectors.toList());
  }

  public boolean supportBill(long citizen_token, long bill_id) {
    Citizen cit = citizenRepository.findByToken(citizen_token);
    Optional<Bill> bill = billRepository.findById(bill_id);

    if (bill.isEmpty()) return false;

    boolean sucess = bill.get().supportBill(cit);
    billRepository.save(bill.get());

    return sucess;
  }
}
