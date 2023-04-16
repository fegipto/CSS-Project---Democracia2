package pt.ul.fc.css.democracia2.services;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.democracia2.domain.Bill;
import pt.ul.fc.css.democracia2.domain.BillStatus;
import pt.ul.fc.css.democracia2.repositories.BillRepository;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;

@Service
@Transactional
public class ClosingExpiredBillsService {
  private BillRepository billRepository;
  private CitizenRepository citizenRepository;

  @Autowired
  public ClosingExpiredBillsService(
      BillRepository billRepository, CitizenRepository citizenRepository) {
    this.billRepository = billRepository;
    this.citizenRepository = citizenRepository;
  }

  public void scheduledExpiredBills() {
    List<Bill> listBills = billRepository.getBillsNotInStatus(BillStatus.EXPIRED);
    for (Bill bill : listBills) {
      if (bill.isExpired()) {
        bill.expire(citizenRepository);
        billRepository.save(bill);
      }
    }
  }
}
