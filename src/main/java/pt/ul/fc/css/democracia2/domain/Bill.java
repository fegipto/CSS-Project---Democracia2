package pt.ul.fc.css.democracia2.domain;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Bill {

  private String title;
  private BillStatus status;
  private String description;
  private byte[] file;
  private LocalDateTime validaty;
  private Topic topic;

  private VoteBox voteBox;
  private List<Citizen> supporters;

  public Bill(String title, String description, byte[] file, LocalDateTime validaty, Topic topic) {
    super();
    this.title = title;
    this.description = description;
    this.file = file;
    this.validaty = validaty;
    this.topic = topic;

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
      voteBox = new VoteBox();
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
