package pt.ul.fc.css.democracia2.domain;

import static jakarta.persistence.EnumType.STRING;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;

/**
 * Class that represents a Bill
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
@Entity
public class Bill {

  /** Constructs a new Bill object */
  public Bill() {
    // No-argument constructor
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "BILL_TITLE")
  private String title;

  @Enumerated(STRING)
  @Column(name = "BILL_STAT")
  private BillStatus status;

  @Column(name = "BILL_DESC")
  private String description;

  @Column(name = "BILL_VALIDITY")
  @Temporal(TemporalType.TIMESTAMP)
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime validity;

  @ManyToOne
  @JoinColumn(name = "TOPIC_ID", nullable = false)
  private Topic topic;

  @Embedded private VoteBox voteBox;

  @ManyToOne
  @JoinColumn(name = "delegate_cc", nullable = false)
  private Delegate proponent;

  public Delegate getProponent() {
    return proponent;
  }

  @ManyToMany(cascade = CascadeType.ALL)
  private List<Citizen> supporters;

  @Column(name = "BILL_FILE")
  @Lob
  private byte[] file;

  /**
   * Constructs a new Bill object using a title, description, file, validity, topic and proponent
   *
   * @param title the title of the bill
   * @param description the description of the bill
   * @param file the file with the main information about the project
   * @param validity the validity of the project
   * @param topic the topic to which the bill is associated
   * @param proponent the delegate that proposes the bill
   */
  public Bill(
      @NonNull String title,
      @NonNull String description,
      byte[] file,
      @NonNull @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime validity,
      @NonNull Topic topic,
      @NonNull Delegate proponent) {
    super();
    this.title = title;
    this.description = description;
    this.file = file;
    this.validity = validity;
    this.topic = topic;
    this.proponent = proponent;
    this.status = BillStatus.CREATED;
    this.voteBox = new VoteBox();
    this.supporters = new LinkedList<Citizen>();
    supporters.add(proponent);
  }

  /**
   * Method that gets the title of the Bill
   *
   * @return the title of the corresponding Bill
   */
  public String getTitle() {
    return title;
  }

  /**
   * Method that gets the status of the Bill
   *
   * @return the status of the corresponding Bill
   */
  public BillStatus getStatus() {
    return status;
  }

  /**
   * Method that gets the description of the Bill
   *
   * @return the description of the corresponding Bill
   */
  public String getDescription() {
    return description;
  }

  /**
   * Method that gets the file of the Bill
   *
   * @return the file of the corresponding Bill
   */
  public byte[] getFile() {
    return file;
  }

  /**
   * Method that gets the validity of the Bill
   *
   * @return the validity of the corresponding Bill
   */
  public LocalDateTime getValidity() {
    return validity;
  }

  /**
   * Method that gets the topic of the Bill
   *
   * @return the topic of the corresponding Bill
   */
  public Topic getTopic() {
    return topic;
  }

  /**
   * Method that gets the votebox of the Bill
   *
   * @return the votebox of the corresponding Bill
   */
  public VoteBox getVoteBox() {
    return (status == BillStatus.VOTING) ? voteBox : null;
  }

  /**
   * Method that gets the supporters of the bill
   *
   * @return the supporters of the corresponding Bill
   */
  public List<Citizen> getSupporters() {
    return supporters;
  }

  /** Method that begins the voting process of a Bill */
  public void beginVote() {
    if (supporters.size() >= 10000) {
      if (isExpired()) status = BillStatus.EXPIRED;
      LocalDateTime now = LocalDateTime.now();
      boolean isValidityMoreThan15Days =
          validity.isAfter(now) && validity.isAfter(now.plusDays(15));
      status = BillStatus.VOTING;

      // 15 dias minimo
      if (isValidityMoreThan15Days) {

        // clamp the validity to 2 months
        boolean isValidityMoreThan2Months = validity.isAfter(now.plusMonths(2));

        if (isValidityMoreThan2Months) validity = now.plusMonths(2);
      } else {
        validity = now.plusDays(15); // extender
      }
      voteBox.addPublicVote(proponent, true);
    }
  }

  /**
   * Method that checks if a Bill is expired or not
   *
   * @return if the corresponding Bill is expired
   */
  public boolean isExpired() {
    if (status == BillStatus.EXPIRED) {
      return true;
    } else if (LocalDateTime.now(ZoneId.of("Europe/Lisbon")).isAfter(validity)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Method that checks if a Bill is open to support or not
   *
   * @return if the corresponding Bill is open to support
   */
  public boolean isOpenToSupport() {
    return status == BillStatus.CREATED;
  }

  /**
   * Method that expires a Bill
   *
   * @param citRepo the CitizenRepository used to check the verdict of the citizens
   */
  public void expire(CitizenRepository citRepo) {
    if (status == BillStatus.CREATED) {
      status = BillStatus.EXPIRED;
    } else if (status == BillStatus.VOTING) {
      Optional<Boolean> verdict = voteBox.getVerdict(citRepo, this);
      status = (verdict.isPresent() && verdict.get()) ? BillStatus.ACCEPTED : BillStatus.FAILED;
    }
  }

  /**
   * Method that adds the given supporter to the supporters list
   *
   * @param supporter the supporter to add
   * @return if the supporter was successfully addded
   */
  public boolean supportBill(Citizen supporter) {
    if (!isOpenToSupport() || supporters.contains(supporter)) return false;

    supporters.add(supporter);
    if (supporters.size() >= 10000) {
      beginVote();
    }
    return true;
  }

  /**
   * Method that gets the id of the Bill
   *
   * @return the id of the corresponding Bill
   */
  public long getId() {
    return this.id;
  }
}
