package pt.ul.fc.css.democracia2.domain;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.AUTO;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "BILL_VALIDITY")
  private Date validity;

  @ManyToOne
  @JoinColumn(name = "TOPIC_ID")
  private Topic topic;

  @Embedded private VoteBox voteBox;

  @ManyToOne private Delegate proponent;

  @OneToMany private List<Citizen> supporters;

  @Column(name = "BILL_FILE")
  @Lob
  private byte[] file;

  public Bill(
      String title,
      String description,
      byte[] file,
      Date validity,
      Topic topic,
      Delegate proponent) {
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
    return validaty;
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
      voteBox = new VoteBox(this);
      status = BillStatus.VOTING;
    }
  }

  public boolean isExpired() {
    if (status == BillStatus.EXPIRED) {
      return true;
    } else if (LocalDateTime.now().isAfter(validaty)) {
      expire();
      return true;
    } else {
      return false;
    }
  }

  public boolean isOpenToSupport() {
    return status == BillStatus.CREATED;
  }

  public void expire() {
    status = BillStatus.EXPIRED;
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
