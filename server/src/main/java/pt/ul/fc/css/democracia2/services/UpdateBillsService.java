package pt.ul.fc.css.democracia2.services;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.democracia2.domain.Bill;
import pt.ul.fc.css.democracia2.domain.BillStatus;
import pt.ul.fc.css.democracia2.repositories.BillRepository;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;

/**
 * Class responsible for updating the status of bills, either in voting or created bills
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
@Service
@Transactional
public class UpdateBillsService {
  private BillRepository billRepository;
  private CitizenRepository citizenRepository;

  /**
   * Contructs a new UpdateBillsService object using a bill repository, citizen repository
   *
   * @param billRepository the bill repository necessary for this service
   * @param citizenRepository the citizen repository necessary for this service
   */
  @Autowired
  public UpdateBillsService(BillRepository billRepository, CitizenRepository citizenRepository) {
    this.billRepository = billRepository;
    this.citizenRepository = citizenRepository;
  }

  /** Method that expires all the bills with expired validity */
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
