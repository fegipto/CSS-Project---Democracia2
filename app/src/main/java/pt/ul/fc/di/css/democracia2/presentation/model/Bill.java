package pt.ul.fc.di.css.democracia2.presentation.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import pt.ul.fc.di.css.democracia2.DTO.BillDTO;

/**
 * Class that represents a frontend Bill
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
public class Bill {
  //Bill's id as a {@code LongProperty}
  private final LongProperty id = new SimpleLongProperty();

  /**
   * Method that gets the Bill's id as a {@code LongProperty}
   *
   * @return the Bill's id as a {@code LongProperty}
   */
  public final LongProperty idProperty() {
    return this.id;
  }

  /**
   * Method that gets the Bill's id
   *
   * @return the Bill's id
   */
  public final Long getId() {
    return this.idProperty().get();
  }

  /**
   * Method that sets the Bill's id
   *
   * @param id the Bill's id
   */
  public final void setId(final Long id) {
    this.idProperty().set(id);
  }

  //Bill's title as a {@code StringProperty}
  private final StringProperty title = new SimpleStringProperty();

  /**
   * Method that gets the Bill's title as a {@code StringProperty}
   *
   * @return the Bill's title as a {@code StringProperty}
   */
  public final StringProperty titleProperty() {
    return this.title;
  }

  /**
   * Method that gets the Bill's title
   *
   * @return the Bill's title
   */
  public final String getTitle() {
    return this.titleProperty().get();
  }

  /**
   * Method that sets the Bill's title
   *
   * @param title the Bill's title
   */
  public final void setTitle(final String title) {
    this.titleProperty().set(title);
  }

  //Bill's proponent name as a {@code StringProperty}
  private final StringProperty proponentName = new SimpleStringProperty();

  /**
   * Method that gets the Bill's proponent name as a {@code StringProperty}
   *
   * @return the Bill's proponent name as a {@code StringProperty}
   */
  public final StringProperty proponentNameProperty() {
    return this.proponentName;
  }

  /**
   * Method that gets the Bill's proponent name
   *
   * @return the Bill's proponent name
   */
  public final String getProponentName() {
    return this.proponentNameProperty().get();
  }

  /**
   * Method that sets the Bill's proponent name
   *
   * @param proponentName the Bill's proponent name
   */
  public final void setProponentName(final String proponentName) {
    this.proponentNameProperty().set(proponentName);
  }

  //Bill's description as a {@code StringProperty}
  private final StringProperty description = new SimpleStringProperty();

  /**
   * Method that gets the Bill's description as a {@code StringProperty}
   *
   * @return the Bill's description as a {@code StringProperty}
   */
  public final StringProperty descriptionProperty() {
    return this.description;
  }

  /**
   * Method that gets the Bill's description
   *
   * @return the Bill's description
   */
  public final String getDescription() {
    return this.descriptionProperty().get();
  }

  /**
   * Method that sets the Bill's description
   *
   * @param description the Bill's description
   */
  public final void setDescription(final String description) {
    this.descriptionProperty().set(description);
  }

  //Bill's status as a {@code StringProperty}
  private final StringProperty status = new SimpleStringProperty();

  /**
   * Method that gets the Bill's status as a {@code StringProperty}
   *
   * @return the Bill's status as a {@code StringProperty}
   */
  public final StringProperty statusProperty() {
    return this.status;
  }

  /**
   * Method that gets the Bill's status
   *
   * @return the Bill's status
   */
  public final String getStatus() {
    return this.statusProperty().get();
  }

  /**
   * Method that sets the Bill's status
   *
   * @param status the Bill's status
   */
  public final void setStatus(final String status) {
    this.statusProperty().set(status);
  }

  //Bill's topic as a {@code StringProperty}
  private final StringProperty topic = new SimpleStringProperty();

  /**
   * Method that gets the Bill's topic as a {@code StringProperty}
   *
   * @return the Bill's topic as a {@code StringProperty}
   */
  public final StringProperty topicProperty() {
    return this.topic;
  }

  /**
   * Method that gets the Bill's topic
   *
   * @return the Bill's topic
   */
  public final String getTopic() {
    return this.topicProperty().get();
  }

  /**
   * Method that sets the Bill's topic
   *
   * @param topic the Bill's topic
   */
  public final void setTopic(final String topic) {
    this.topicProperty().set(topic);
  }

  //Bill's validity as a {@code StringProperty}
  private final StringProperty validity = new SimpleStringProperty();

  /**
   * Method that gets the Bill's validity as a {@code StringProperty}
   *
   * @return the Bill's validity as a {@code StringProperty}
   */
  public final StringProperty validityProperty() {
    return this.validity;
  }

  /**
   * Method that gets the Bill's validity
   *
   * @return the Bill's validity
   */
  public final String getValidity() {
    return this.validityProperty().get();
  }

  /**
   * Method that sets the Bill's validity
   *
   * @param validity the Bill's validity
   */
  public final void setValidity(final LocalDateTime validity) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    this.validityProperty().set(validity.format(formatter));
  }

  //Bill's vote information as a {@code StringProperty}
  private final StringProperty voteInformation = new SimpleStringProperty("Please login");

  /**
   * Method that gets the Bill's vote information as a {@code StringProperty}
   *
   * @return the Bill's vote information as a {@code StringProperty}
   */
  public final StringProperty voteInformationProperty() {
    return this.voteInformation;
  }

  /**
   * Method that gets the Bill's vote information
   *
   * @return the Bill's vote information
   */
  public final String getVoteInformation() {
    return this.voteInformationProperty().get();
  }

  /**
   * Method that sets the Bill's vote information
   *
   * @param voteInformation the Bill's vote information
   */
  public final void setVoteInformation(final String voteInformation) {
    this.voteInformationProperty().set(voteInformation);
  }

  //Bill's supporter count as a {@code StringProperty}
  private final StringProperty supporterCount = new SimpleStringProperty("Please login");

  /**
   * Method that gets the Bill's supporter count as a {@code StringProperty}
   *
   * @return the Bill's supporter count as a {@code StringProperty}
   */
  public final StringProperty supporterCountProperty() {
    return this.supporterCount;
  }

  /**
   * Method that gets the Bill's supporter count
   *
   * @return the Bill's supporter count
   */
  public final String getSupporterCount() {
    return this.supporterCountProperty().get();
  }

  /**
   * Method that sets the Bill's supporter count
   *
   * @param supporterCount the Bill's supporter count
   */
  public final void setSupporterCount(final String supporterCount) {
    this.supporterCountProperty().set(supporterCount);
  }

  /**
   * Constructs a new frontend Bill object with the given BillDTO
   *
   * @param billDTO the BillDTO to use to create the frontend Bill
   */
  public Bill(BillDTO billDTO) {
    setId(billDTO.getId());
    setTitle(billDTO.getTitle());
    setProponentName(billDTO.getProponent().getName());
    setTopic(billDTO.getTopic().getName());
    setDescription(billDTO.getDescription());
    setStatus(billDTO.getStatus().name());
    setValidity(billDTO.getValidity());
    setSupporterCount(billDTO.getSupportersCount() + "");
  }

  @Override
  public String toString() {
    return title.getValue();
  }
}
