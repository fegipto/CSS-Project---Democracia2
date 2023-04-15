package pt.ul.fc.css.democracia2;

import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.democracia2.domain.Bill;
import pt.ul.fc.css.democracia2.domain.BillStatus;
import pt.ul.fc.css.democracia2.domain.Citizen;
import pt.ul.fc.css.democracia2.domain.Delegate;
import pt.ul.fc.css.democracia2.repositories.BillRepository;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;

@Service
@Transactional
public class VotingService {
  @Autowired private BillRepository billRepository;
  @Autowired private CitizenRepository citizenRepository;

  @Autowired
  public VotingService(BillRepository billRepository, CitizenRepository citizenRepository) {
    this.billRepository = billRepository;
    this.citizenRepository = citizenRepository;
  }

  public Optional<Boolean> getOmmitedVote(long auth_token, long bill_id) {
    Optional<Bill> bill = billRepository.findById(bill_id);
    Optional<Citizen> cit = citizenRepository.findByToken(auth_token);

    if (bill.isEmpty() || cit.isEmpty()) return Optional.empty();

    return bill.get()
        .getVoteBox()
        .getPublicCastVote(cit.get().getChosenDelegate(bill.get().getTopic()));
  }

  public boolean vote(long auth_token, long bill_id, boolean vote) {
    Optional<Bill> bill = billRepository.findById(bill_id);
    Optional<Citizen> cit = citizenRepository.findByToken(auth_token);

    if (bill.isEmpty() || cit.isEmpty() || bill.get().getStatus() != BillStatus.VOTING)
      return false;

    if (cit.get() instanceof Delegate)
      return bill.get().getVoteBox().addPublicVote((Delegate) cit.get(), vote);
    else return bill.get().getVoteBox().addPrivateVote(cit.get(), vote);
  }
}
