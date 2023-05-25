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
import pt.ul.fc.css.democracia2.domain.Citizen;
import pt.ul.fc.css.democracia2.repositories.BillRepository;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;

/**
 * Class responsible for supporting bills
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
@Service
@Transactional
public class SupportBillService {
  private BillRepository billRepository;
  private CitizenRepository citizenRepository;

  /**
   * Contructs a new SupportBillService object using a bill repository, citizen repository
   *
   * @param billRepository the bill repository necessary for this service
   * @param citizenRepository the citizen repository necessary for this service
   */
  @Autowired
  public SupportBillService(BillRepository billRepository, CitizenRepository citizenRepository) {
    this.billRepository = billRepository;
    this.citizenRepository = citizenRepository;
  }

  /**
   * Method that gets the bills available to support
   *
   * @return the bills available to support
   */
  @Autowired
  public List<BillDTO> getOpenToSupportBills() {
    return billRepository.getBillsByStatus(BillStatus.CREATED).stream()
        .map(bill -> new BillDTO(bill))
        .collect(Collectors.toList());
  }

  /**
   * Method that allows a citizen to support a bill
   *
   * @param citizen_token the authentication token of the citizen that wants to support
   * @param bill_id the id of the bill to support
   * @return if the supporting process was successfull or not
   */
  public boolean supportBill(long citizen_token, long bill_id) {
    Optional<Citizen> cit = citizenRepository.findByToken(citizen_token);
    Optional<Bill> bill = billRepository.findById(bill_id);

    if (bill.isEmpty() || cit.isEmpty()) return false;
    if (bill.get().isExpired()) {
      bill.get().expire(citizenRepository);
      billRepository.save(bill.get());

      return false;
    }

    boolean sucess = bill.get().supportBill(cit.get());
    billRepository.save(bill.get());

    return sucess;
  }
}
