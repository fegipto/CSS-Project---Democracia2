package pt.ul.fc.css.democracia2.domain;

import java.util.HashMap;
import java.util.Map;

/** Class that represents a Citizen */
public class Citizen {
  private String name;
  private long cc;
  private long token;

  private Map<Topic, Delegate> chosenDelegates;

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
    this.chosenDelegates = new HashMap<>();
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
    chosenDelegates.put(topic, delegate);
  }

  /**
   * Method that given a Topic return the delegate chosen for that topic
   *
   * @param topic the Topic to search
   * @return the delegate related to that topic || null if doent exist
   * @requires topic != null
   */
  public Delegate getChosenDelegate(Topic topic) {
    return chosenDelegates.get(topic);
  }

  /**
   * Method that given a Topic checks if there are any ChosenDelegates in delegates with that topic
   *
   * @param topic the Topic to check
   * @return true, if exists a chosenDelegate with that topic. false, otherwise.
   */
  public boolean delegateExists(Topic topic) {
    return chosenDelegates.containsKey(topic);
  }
}
