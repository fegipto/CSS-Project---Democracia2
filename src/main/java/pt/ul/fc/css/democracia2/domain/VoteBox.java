package pt.ul.fc.css.democracia2.domain;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/** This class represents a voting box for a given Bill */
public class VoteBox {
  private Set<Delegate> inFavor;
  private Set<Delegate> against;
  private Set<Citizen> voted;
  private Bill correspondingBill;

  private long prInFavor; // number of citizens in favor
  private long prAgainst;

  /**
   * Constructs a new VoteBox object for the given bill.
   *
   * @param correspondingBill the corresponding bill for which votes are being cast
   */
  public VoteBox(Bill correspondingBill) {
    super();
    this.correspondingBill = correspondingBill;
    this.inFavor = new HashSet<Delegate>(); // need fast contains
    this.against = new HashSet<Delegate>();

    this.voted = new HashSet<Citizen>();
    this.prInFavor = 0;
    this.prAgainst = 0;
  }

  /**
   * Returns the corresponding bill for which votes are being cast.
   *
   * @return the corresponding bill
   */
  public Bill getCorrespondingBill() {
    return correspondingBill;
  }

  /**
   * Adds a public vote for the given delegate and choice.
   *
   * @param delegate the delegate who cast the vote
   * @param choice the choice for which the delegate voted
   */
  public void addPublicVote(Delegate delegate, boolean choice) {
    ((choice) ? inFavor : against).add(delegate);
    voted.add(delegate);
  }

  /**
   * Adds a private vote for the given citizen and choice.
   *
   * @param citizen the citizen who cast the vote
   * @param choice the choice for which the citizen voted
   */
  public void addPrivateVote(Citizen citizen, boolean choice) {
    voted.add(citizen);
    if (choice) prInFavor++;
    else prAgainst++;
  }

  /**
   * Returns an optional verdict based on the total number of citizens who voted in favor or against
   * the bill. If the verdict cannot be determined, returns an empty optional.
   *
   * @return an optional verdict
   */
  public Optional<Boolean> getVerdict() {
    long yesVotes = inFavor.size() + prInFavor;
    long noVotes = against.size() + prAgainst;

    if (yesVotes > noVotes) {
      return Optional.of(true);
    } else if (noVotes > yesVotes) {
      return Optional.of(false);
    } else {
      return Optional.empty(); // Tie, verdict cannot be determined
    }
  }

  /**
   * Returns an optional boolean based on the cast vote of a given delegate on the bill. If he
   * hasn't voted return an empty
   *
   * @param delegate the delegate who cast the vote
   * @return an optional vote
   */
  public Optional<Boolean> getPublicCastVote(Delegate delegate) {
    if (inFavor.contains(delegate)) {
      return Optional.of(true);
    } else if (against.contains(delegate)) {
      return Optional.of(false);
    } else {
      return Optional.empty(); // didn't vote
    }
  }

  /**
   * @param cit the citizen to check
   * @return if the citizen has voted
   */
  public Boolean hasVoted(Citizen cit) {
    return voted.contains(cit);
  }
}
