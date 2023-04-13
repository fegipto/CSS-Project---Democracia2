package pt.ul.fc.css.democracia2.domain;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.AUTO;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.springframework.lang.NonNull;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;

@Entity
public class Bill {

  public Bill() {
    // No-argument constructor
  }

  @Id
  @GeneratedValue(strategy = AUTO)
  @Column(name = "BILL_ID")
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

  @OneToMany private List<Citizen> supporters;

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
    return voteBox;
  }

  public List<Citizen> getSupporters() {
    return supporters;
  }

  public void beginVote() {
    if (!isExpired() && supporters.size() >= 10000 && voteBox == null) {
      voteBox = new VoteBox();
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
    if (!isOpenToSupport()) return false;

    supporters.add(supporter);
    if (supporters.size() == 10000) {
      beginVote();
    }
    return true;
  }
}
