package pt.ul.fc.css.democracia2.javafx.presentation.model;

import jakarta.persistence.*;
import pt.ul.fc.css.democracia2.domain.Bill;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;

import java.util.*;

/**
 * Class that represents a VoteBox for a given Bill
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
@Embeddable
public class VoteBox {

  @ElementCollection
  @MapKeyJoinColumn(name = "delegate_cc")
  private Map<Delegate, Boolean> publicVotes;

  @ManyToMany(cascade = CascadeType.ALL)
  private Set<Citizen> voted;

  private long totalInFavor; // number of citizens in favor

  /** Does not count ommited votes */
  public long getTotalInFavor() {
    return totalInFavor;
  }
  /** Does not count ommited votes */
  public long getTotalAgainst() {
    return totalAgainst;
  }

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
  public boolean addPublicVote(Delegate delegate, boolean choice) {
    if (publicVotes.containsKey(delegate) || voted.contains(delegate)) return false;
    publicVotes.put(delegate, choice);
    voted.add(delegate);
    if (choice) totalInFavor++;
    else totalAgainst++;
    return true;
  }

  /**
   * Adds a private vote for the given citizen and choice.
   *
   * @param citizen the citizen who cast the vote
   * @param choice the choice for which the citizen voted
   */
  public boolean addPrivateVote(Citizen citizen, boolean choice) {
    if (voted.contains(citizen)) return false;
    voted.add(citizen);
    if (choice) totalInFavor++;
    else totalAgainst++;
    return true;
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
   * Method that check if a citizen has voted
   *
   * @param cit the citizen to check
   * @return if the citizen has voted
   */
  public Boolean hasVoted(Citizen cit) {
    return voted.contains(cit);
  }
}
