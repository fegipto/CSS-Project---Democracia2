package pt.ul.fc.css.democracia2.DTO;

import java.time.LocalDateTime;
import pt.ul.fc.css.democracia2.domain.Bill;
import pt.ul.fc.css.democracia2.domain.BillStatus;
import pt.ul.fc.css.democracia2.domain.Topic;

public class BillDTO {

  private long id;
  private String title;
  private BillStatus status;
  private String description;
  private LocalDateTime validity;
  private Topic topic;
  private byte[] file;

  public BillDTO() {
    // No-argument constructor
  }

  public BillDTO(Bill bill) {
    this.id = bill.getId();
    this.title = bill.getTitle();
    this.status = bill.getStatus();
    this.description = bill.getDescription();
    this.validity = bill.getValidity();
    this.topic = bill.getTopic();
    this.file = bill.getFile();
  }

  public long getId() {
    return id;
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

  public LocalDateTime getValidity() {
    return validity;
  }

  public Topic getTopic() {
    return topic;
  }

  public byte[] getFile() {
    return file;
  }
}
