package pt.ul.fc.di.css.democracia2.DTO;

import java.time.LocalDateTime;

/**
 * Class that represents a BillDTO
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
public class BillDTO {

  private long id;
  private CitizenDTO proponent;
  private String title;
  private BillStatus status;
  private String description;
  private int supportersCount;
  private LocalDateTime validity;

  private Topic topic;
  private byte[] file;

  /** Constructs a new BillDTO object */
  public BillDTO() {
    // No-argument constructor
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

  public void setId(long id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setStatus(BillStatus status) {
    this.status = status;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setValidity(LocalDateTime validity) {
    this.validity = validity;
  }

  public void setTopic(Topic topic) {
    this.topic = topic;
  }

  public void setFile(byte[] file) {
    this.file = file;
  }

  public CitizenDTO getProponent() {
    return proponent;
  }

  public void setProponent(CitizenDTO proponent) {
    this.proponent = proponent;
  }

  public int getSupportersCount() {
    return supportersCount;
  }

  public void setSupportersCount(int supportersCount) {
    this.supportersCount = supportersCount;
  }
}
