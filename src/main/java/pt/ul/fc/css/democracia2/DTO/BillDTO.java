package pt.ul.fc.css.democracia2.DTO;

import java.time.LocalDateTime;
import pt.ul.fc.css.democracia2.domain.Bill;
import pt.ul.fc.css.democracia2.domain.BillStatus;
import pt.ul.fc.css.democracia2.domain.Topic;

/**
 * Class that represents a BillDTO
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
public class BillDTO {

  private long id;
  private String title;
  private BillStatus status;
  private String description;
  private LocalDateTime validity;
  private Topic topic;
  private byte[] file;

  /**
   * Constructs a new BillDTO object
   */
  public BillDTO() {
    // No-argument constructor
  }

  /**
   * Constructs a new BillDTO object using a Bill
   *
   * @param bill the bill to use to create the DTO
   */
  public BillDTO(Bill bill) {
    this.id = bill.getId();
    this.title = bill.getTitle();
    this.status = bill.getStatus();
    this.description = bill.getDescription();
    this.validity = bill.getValidity();
    this.topic = bill.getTopic();
    this.file = bill.getFile();
  }

  /**
   * Method that gets the id of the BillDTO
   *
   * @return the id of the corresponding BillDTO
   */
  public long getId() {
    return id;
  }

  /**
   * Method that gets the title of the BillDTO
   *
   * @return the title of the corresponding BillDTO
   */
  public String getTitle() {
    return title;
  }

  /**
   * Method that gets the status of the BillDTO
   *
   * @return the status of the corresponding BillDTO
   */
  public BillStatus getStatus() {
    return status;
  }

  /**
   * Method that gets the description of the BillDTO
   *
   * @return the description of the corresponding BillDTO
   */
  public String getDescription() {
    return description;
  }

  /**
   * Method that gets the validity of the BillDTO
   *
   * @return the validity of the corresponding BillDTO
   */
  public LocalDateTime getValidity() {
    return validity;
  }

  /**
   * Method that gets the topic of the BillDTO
   *
   * @return the topic of the corresponding BillDTO
   */
  public Topic getTopic() {
    return topic;
  }

  /**
   * Method that gets the file of the BillDTO
   *
   * @return the file of the corresponding BillDTO
   */
  public byte[] getFile() {
    return file;
  }
}
