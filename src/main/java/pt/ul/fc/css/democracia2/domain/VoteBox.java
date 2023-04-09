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

  public VoteBox(Bill correspondingBill) {
    super();
    this.correspondingBill = correspondingBill;
    this.inFavor = new HashSet<Delegate>(); // need fast contains
    this.against = new HashSet<Delegate>();

    this.voted = new HashSet<Citizen>();
    this.prInFavor = 0;
    this.prAgainst = 0;
  }

  public Bill getCorrespondingBill() {
    return correspondingBill;
  }

  public void addPublicVote(Delegate delegate, boolean choice) {
    ((choice) ? inFavor : against).add(delegate);
    voted.add(delegate);
  }

  public void addPrivateVote(Citizen citizen, boolean choice) {
    voted.add(citizen);
    if (choice) prInFavor++;
    else prAgainst++;
  }

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

  public Optional<Boolean> getPublicCastVote(Delegate delegate) {
    if (inFavor.contains(delegate)) {
      return Optional.of(true);
    } else if (against.contains(delegate)) {
      return Optional.of(false);
    } else {
      return Optional.empty(); // didn't vote
    }
  }

  public Boolean hasVoted(Citizen cit) {
    return voted.contains(cit);
  }
}
