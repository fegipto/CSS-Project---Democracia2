package pt.ul.fc.css.democracia2.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.OneToMany;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;

/** This class represents a voting box for a given Bill */
@Embeddable
public class VoteBox {

  @ElementCollection
  @MapKeyJoinColumn(name = "delegate_cc")
  private Map<Delegate, Boolean> publicVotes;

  @OneToMany private Set<Citizen> voted;

  private long totalInFavor; // number of citizens in favor
  private long totalAgainst;

  /** Constructs a new VoteBox object for the given bill. */
  public VoteBox() {
    super();
    this.publicVotes = new HashMap<>();

    this.voted = new HashSet<Citizen>();
    this.totalInFavor = 0;
    this.totalAgainst = 0;
  }

  /**
   * Adds a public vote for the given delegate and choice.
   *
   * @param delegate the delegate who cast the vote
   * @param choice the choice for which the delegate voted
   */
  public void addPublicVote(Delegate delegate, boolean choice) {
    publicVotes.put(delegate, choice);
    voted.add(delegate);
    if (choice) totalInFavor++;
    else totalAgainst++;
  }

  /**
   * Adds a private vote for the given citizen and choice.
   *
   * @param citizen the citizen who cast the vote
   * @param choice the choice for which the citizen voted
   */
  public void addPrivateVote(Citizen citizen, boolean choice) {
    voted.add(citizen);
    if (choice) totalInFavor++;
    else totalAgainst++;
  }

  /**
   * Returns an optional verdict based on the total number of citizens who voted in favor or against
   * the bill. If the verdict cannot be determined, returns an empty optional.
   *
   * @return an optional verdict
   */
  public Optional<Boolean> getVerdict(CitizenRepository rep, Bill correspondingBill) {
    long yesVotes = totalInFavor;
    long noVotes = totalAgainst;

    // iterate over the citizens who didn't vote
    for (Citizen citizen : rep.findAll()) {
      if (!hasVoted(citizen)) {
        // find the delegate associated with the most specific topic for the bill
        Delegate del = citizen.getChosenDelegate(correspondingBill.getTopic());

        // update the total number of citizens who voted in favor or against
        if (del != null) {
          if (publicVotes.get(del)) yesVotes++;
          else noVotes++;
        }
      }
    }

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
    return (publicVotes.containsKey(delegate))
        ? Optional.of(publicVotes.get(delegate))
        : Optional.empty();
  }

  /**
   * @param cit the citizen to check
   * @return if the citizen has voted
   */
  public Boolean hasVoted(Citizen cit) {
    return voted.contains(cit);
  }
}
