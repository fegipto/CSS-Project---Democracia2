package pt.ul.fc.css.democracia2.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Delegate extends Citizen {

  public Delegate() {
    // No-argument constructor
  }

  @OneToMany(mappedBy = "proponent")
  private List<Bill> bills;

  public List<Bill> getBills() {
    return bills;
  }

  /**
   * Constructs a new Delegate object using a name,cc,token.
   *
   * @param name the name of the delegate
   * @param cc the cc of the delegate
   * @param token authentication token
   */
  public Delegate(String name, long cc) {
    super(name, cc);
    bills = new LinkedList<>();
  }

  /**
   * Method that get the name of the Delegate
   *
   * @return the name of the corresponding Delegate
   */
  public String getName() {
    return super.getName();
  }

  /**
   * Method that get the cc of the Delegate
   *
   * @return the cc of the corresponding Delegate
   */
  public long getCC() {
    return super.getCC();
  }

  /**
   * Method that get the token of the Delegate
   *
   * @return the token of the corresponding Delegate
   */
  public long getToken() {
    return super.getToken();
  }

  /**
   * Method that given a Delegate and a Topic, creates a new ChosenDelegate and add it to the
   * delegates of the delegate
   *
   * @param delegate the Delegate to add
   * @param topic the Topic to add
   * @requires delegate != null && topic != null
   */
  public void chooseDelegate(Delegate delegate, Topic topic) {
    super.chooseDelegate(delegate, topic);
  }

  /**
   * Method that given a Topic return the delegate chosen for that topic
   *
   * @param topic the Topic to search
   * @return the delegate related to that topic || null if doent exist
   * @requires topic != null
   */
  public Delegate getChosenDelegate(Topic topic) {
    return super.getChosenDelegate(topic);
  }

  /**
   * Method that given a Topic checks if there are any ChosenDelegates in delegates with that topic
   *
   * @param topic the Topic to check
   * @return true, if exists a chosenDelegate with that topic. false, otherwise.
   */
  public boolean delegateExists(Topic topic) {
    return super.delegateExists(topic);
  }

  /**
   * Method that proposes a Bill
   *
   * @param title the Bill's title
   * @param description the Bill's description
   * @param file the Bill's file
   * @param validity the Bill's validaty
   * @param topic the Bill's topic
   */
  public Bill proposeBill(
      String title, String description, byte[] file, LocalDateTime validity, Topic topic) {
    LocalDateTime now = LocalDateTime.now();
    if (!validity.isAfter(now)) return null;
    Bill adding = new Bill(title, description, file, validity, topic, this);
    bills.add(adding);
    return adding;
  }
}
