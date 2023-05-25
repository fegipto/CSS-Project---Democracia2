package pt.ul.fc.di.css.democracia2.presentation.model;

import java.util.*;
import javafx.beans.property.*;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

/**
 * Class that represents a VoteBox for a given Bill
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
public class VoteBox {

  private final MapProperty<Delegate, Boolean> publicVotes = new SimpleMapProperty<>();

  public final MapProperty<Delegate, Boolean> publicVotesProperty() {
    return this.publicVotes;
  }

  public final Map<Delegate, Boolean> getPublicVotes() {
    return this.publicVotesProperty().get();
  }

  public final void setPublicVotes(final Map<Delegate, Boolean> publicVotes) {
    this.publicVotesProperty().set((ObservableMap<Delegate, Boolean>) publicVotes);
  }

  public final Optional<Boolean> getPublicCastVote(Delegate delegate) {
    return (publicVotes.containsKey(delegate))
        ? Optional.of(publicVotes.get(delegate))
        : Optional.empty();
  }

  private final SetProperty<Citizen> voted = new SimpleSetProperty<>();

  public final SetProperty<Citizen> votedProperty() {
    return this.voted;
  }

  public final Set<Citizen> getVoted() {
    return this.votedProperty().get();
  }

  public final void setVoted(final Set<Citizen> voted) {
    this.votedProperty().set((ObservableSet<Citizen>) voted);
  }

  public Boolean hasVoted(Citizen cit) {
    return voted.contains(cit);
  }

  private final LongProperty totalInFavor = new SimpleLongProperty();

  public final LongProperty totalInFavorProperty() {
    return this.totalInFavor;
  }

  public final Long getTotalInFavor() {
    return this.totalInFavorProperty().get();
  }

  public final void setTotalInFavor(final Long totalInFavor) {
    this.totalInFavorProperty().set(totalInFavor);
  }

  private final LongProperty totalAgainst = new SimpleLongProperty();

  public final LongProperty totalAgainstProperty() {
    return this.totalAgainst;
  }

  public final Long getTotalAgainst() {
    return this.totalAgainstProperty().get();
  }

  public final void setTotalAgainst(final Long totalAgainst) {
    this.totalAgainstProperty().set(totalAgainst);
  }

  public final boolean addPublicVote(Delegate delegate, boolean choice) {
    if (publicVotes.containsKey(delegate) || voted.contains(delegate)) return false;
    publicVotes.put(delegate, choice);
    voted.add(delegate);
    if (choice) totalInFavor.add(1);
    else totalAgainst.add(1);
    return true;
  }

  public final boolean addPrivateVote(Citizen citizen, boolean choice) {
    if (voted.contains(citizen)) return false;
    voted.add(citizen);
    if (choice) totalInFavor.add(1);
    else totalAgainst.add(1);
    return true;
  }

  public final Optional<Boolean> getVerdict(List<Citizen> rep, Bill correspondingBill) {
    long yesVotes = totalInFavor.get();
    long noVotes = totalAgainst.get();

    // iterate over the citizens who didn't vote
    for (Citizen citizen : rep) {
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

  @Override
  public String toString() {
    return "The current result is: " + totalInFavor + " to " + totalAgainst + ".";
  }
}
