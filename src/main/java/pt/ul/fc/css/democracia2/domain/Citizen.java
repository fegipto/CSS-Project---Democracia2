package pt.ul.fc.css.democracia2.domain;

import java.util.HashSet;
import java.util.Set;

/** Class that represents a Citizen */
public class Citizen {
  private String name;
  private long cc;
  private long token;

  private Set<ChosenDelegate> delegates;

  /**
   * Constructs a new Citizen object using a name,cc,token.
   *
   * @param name the name of the citizen
   * @param cc the cc of the citizen
   * @param token authentication token
   */
  public Citizen(String name, long cc, long token) {
    super();
    this.name = name;
    this.cc = cc;
    this.token = token;
    this.delegates = new HashSet<ChosenDelegate>();
  }

  /**
   * Method that get the name of the Citizen
   *
   * @return the name of the corresponding Citizen
   */
  public String getName() {
    return name;
  }

  /**
   * Method that get the cc of the Citizen
   *
   * @return the cc of the corresponding Citizen
   */
  public long getCC() {
    return cc;
  }

  /**
   * Method that get the token of the Citizen
   *
   * @return the token of the corresponding Citizen
   */
  public long getToken() {
    return token;
  }

  /**
   * Method that given a Delegate and a Topic, creates a new ChosenDelegate and add it to the
   * delegates of the citizen
   *
   * @param delegate the Delegate to add
   * @param topic the Topic to add
   * @requires delegate != null && topic != null
   */
  public void chooseDelegate(Delegate delegate, Topic topic) {
    if (topicExists(topic)) {
      delegates.remove(getByTopic(topic));
    }
    delegates.add(new ChosenDelegate(delegate, topic));
  }

  /**
   * Method that given a Delagate and a Topic, creates a new {@link ChosenDelegate} and checks if it
   * exist on the delegates of the Citizen.
   *
   * @param delegate the Delegate to check
   * @param topic the Topic to check
   * @return true, if the chosenDelegate exists in delegates. false, otherwise.
   * @requires delegate != null && topic != null
   */
  public boolean checksExistsChosen(Delegate delegate, Topic topic) {
    return delegates.contains(new ChosenDelegate(delegate, topic));
  }

  /**
   * Method that given a Topic return the delegate chosen for that topic
   *
   * @param topic the Topic to search
   * @return the delegate related to that topic || a error message saying that the delegate to that
   *     topic doesn't exist.
   * @requires topic != null
   */
  public Delegate getChosenDelegate(Topic topic) {
    for (ChosenDelegate chosenDelegate : delegates) {
      if (chosenDelegate.getTopic().equals(topic)) {
        return chosenDelegate.getDelegate();
      }
    }
    throw new RuntimeException("No delegate found for topic: " + topic);
  }

  /**
   * Method that given a Topic checks if there are any ChosenDelegates in delegates with that topic
   *
   * @param topic the Topic to check
   * @return true, if exists a chosenDelegate with that topic. false, otherwise.
   */
  private boolean topicExists(Topic topic) {
    for (ChosenDelegate chosenDelegate : delegates) {
      if (chosenDelegate.getTopic().equals(topic)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Method that given a Topic, return the chosenDelegate associated with that Topic.
   *
   * @param topic the Topic search.
   * @return the ChosenDelegate associated with that topic || null if ChosenDelegate with that topic
   *     does exist
   */
  private ChosenDelegate getByTopic(Topic topic) {
    for (ChosenDelegate chosenDelegate : delegates) {
      if (chosenDelegate.getTopic().equals(topic)) {
        return chosenDelegate;
      }
    }
    return null;
  }
}
