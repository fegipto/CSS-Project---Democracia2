package pt.ul.fc.css.democracia2.services;

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

/**
 * Class responsible for voting in a bill
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
@Service
@Transactional
public class VotingService {
  private BillRepository billRepository;
  private CitizenRepository citizenRepository;

  /**
   * Contructs a new VotingService object using a bill repository, citizen repository
   *
   * @param billRepository the bill repository necessary for this service
   * @param citizenRepository the citizen repository necessary for this service
   */
  @Autowired
  public VotingService(BillRepository billRepository, CitizenRepository citizenRepository) {
    this.billRepository = billRepository;
    this.citizenRepository = citizenRepository;
  }

  /**
   * Method that gets the ommited vote of a citizen using an authentication token, bill id
   *
   * @param auth_token the authentication token of the citizen that wants to vote
   * @param bill_id the id of the bill to get the ommited vote in
   * @return the vote content
   */
  public Optional<Boolean> getOmmitedVote(long auth_token, long bill_id) {
    Optional<Bill> bill = billRepository.findById(bill_id);
    Optional<Citizen> cit = citizenRepository.findByToken(auth_token);

    if (bill.isEmpty() || cit.isEmpty()) return Optional.empty();

    return bill.get()
        .getVoteBox()
        .getPublicCastVote(cit.get().getChosenDelegate(bill.get().getTopic()));
  }

  /**
   * Method that allows a citizen to vote in a bill
   *
   * @param auth_token the authentication token of the citizen that wants to vote
   * @param bill_id the id of the bill to vote in
   * @param vote the vote content
   * @return if the voting process was successfull or not
   */
  public boolean vote(long auth_token, long bill_id, boolean vote) {
    Optional<Bill> bill = billRepository.findById(bill_id);
    Optional<Citizen> cit = citizenRepository.findByToken(auth_token);

    if (bill.isEmpty() || cit.isEmpty() || bill.get().getStatus() != BillStatus.VOTING)
      return false;

    if (cit.get() instanceof Delegate)
      return bill.get().getVoteBox().addPublicVote((Delegate) cit.get(), vote);
    else return bill.get().getVoteBox().addPrivateVote(cit.get(), vote);
  }

  public boolean hasVoted(long citizenId, long billId) {
    Optional<Bill> bill = billRepository.findById(billId);
    Optional<Citizen> cit = citizenRepository.findByToken(citizenId);
    if (bill.isEmpty() || cit.isEmpty()) return false;

    return bill.get().getVoteBox().hasVoted(cit.get());
  }
}
