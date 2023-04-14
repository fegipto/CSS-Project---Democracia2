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
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;

@Entity
public class Bill {

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
  private LocalDateTime validity;

  @ManyToOne
  @JoinColumn(name = "TOPIC_ID", nullable = false)
  private Topic topic;

  @Embedded private VoteBox voteBox;

  @ManyToOne
  @JoinColumn(name = "delegate_cc", nullable = false)
  private Delegate proponent;

  @ManyToMany(cascade = CascadeType.ALL)
  private List<Citizen> supporters;

  @Column(name = "BILL_FILE")
  @Lob
  private byte[] file;

  public Bill(
      @NonNull String title,
      @NonNull String description,
      @NonNull byte[] file,
      @NonNull LocalDateTime validity,
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
  }

  public String getTitle() {
    return title;
  }

  public BillStatus getStatus() {
    return status;
  }

  public String getDescription() {
    return description;
  }

  public byte[] getFile() {
    return file;
  }

  public LocalDateTime getValidaty() {
    return validity;
  }

  public Topic getTopic() {
    return topic;
  }

  public VoteBox getVoteBox() {
    return (status == BillStatus.VOTING) ? voteBox : null;
  }

  public List<Citizen> getSupporters() {
    return supporters;
  }

  public void beginVote() {
    if (!isExpired() && supporters.size() >= 10000) {

      status = BillStatus.VOTING;
    }
  }

  public boolean isExpired() {
    if (status == BillStatus.EXPIRED) {
      return true;
    } else if (LocalDateTime.now().isAfter(validity)) {
      return true;
    } else {
      return false;
    }
  }

  public boolean isOpenToSupport() {
    return status == BillStatus.CREATED;
  }

  public void expire(CitizenRepository citRepo) {
    if (status == BillStatus.CREATED) {
      status = BillStatus.EXPIRED;
    } else if (status == BillStatus.VOTING) {
      Optional<Boolean> verdict = voteBox.getVerdict(citRepo, this);
      if (verdict.isEmpty()) status = BillStatus.CLOSED;
      else status = (verdict.get()) ? BillStatus.ACCEPTED : BillStatus.FAILED;
    }
  }

  public boolean supportBill(Citizen supporter) {
    if (!isOpenToSupport() || supporters.contains(supporter)) return false;

    supporters.add(supporter);
    if (supporters.size() >= 10000) {
      beginVote();
    }
    return true;
  }
}
