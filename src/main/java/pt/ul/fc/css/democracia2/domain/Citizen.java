package pt.ul.fc.css.democracia2.domain;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import org.springframework.lang.NonNull;

/** Class that represents a Citizen */
@Entity
@Inheritance(strategy = SINGLE_TABLE)
public class Citizen {

  public Citizen() {
    // No-argument constructor
  }

  @Id
  @GeneratedValue(strategy = AUTO)
  @Column(name = "CIT_ID")
  private Long id;

  @Column(name = "CIT_NAME")
  private String name;

  @Column(name = "CIT_CC")
  private long cc;

  @Column(name = "CIT_TOKEN")
  private long token;

  @OneToMany
  @JoinTable(
      name = "citizen_delegate_topic",
      joinColumns = {@JoinColumn(name = "citizen_id", referencedColumnName = "CIT_ID")},
      inverseJoinColumns = {@JoinColumn(name = "delegate_id", referencedColumnName = "CIT_ID")})
  @MapKeyJoinColumn(name = "topic_id")
  private Map<Topic, Delegate> chosenDelegates;

  /**
   * Constructs a new Citizen object using a name,cc,token.
   *
   * @param name the name of the citizen
   * @param cc the cc of the citizen
   * @param token authentication token
   */
  public Citizen(@NonNull String name, @NonNull long cc, long token) {
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
    if (topic == null) return null;
    return (chosenDelegates.containsKey(topic))
        ? chosenDelegates.get(topic)
        : getChosenDelegate(topic.getParent());
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

  @Override
  public int hashCode() {
    return Objects.hash(cc, name);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Citizen other = (Citizen) obj;
    return cc == other.cc && Objects.equals(name, other.name);
  }
}