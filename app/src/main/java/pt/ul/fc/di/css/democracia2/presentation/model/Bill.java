package pt.ul.fc.di.css.democracia2.presentation.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import pt.ul.fc.di.css.democracia2.DTO.BillDTO;

public class Bill {
  private final LongProperty id = new SimpleLongProperty();

  public final LongProperty idProperty() {
    return this.id;
  }

  public final Long getId() {
    return this.idProperty().get();
  }

  public final void setId(final Long id) {
    this.idProperty().set(id);
  }

  private final StringProperty title = new SimpleStringProperty();

  public final StringProperty titleProperty() {
    return this.title;
  }

  public final String getTitle() {
    return this.titleProperty().get();
  }

  public final void setTitle(final String title) {
    this.titleProperty().set(title);
  }

  private final StringProperty proponentName = new SimpleStringProperty();

  public final StringProperty proponentNameProperty() {
    return this.proponentName;
  }

  public final String getProponentName() {
    return this.proponentNameProperty().get();
  }

  public final void setProponentName(final String proponentName) {
    this.proponentNameProperty().set(proponentName);
  }

  private final StringProperty description = new SimpleStringProperty();

  public final StringProperty descriptionProperty() {
    return this.description;
  }

  public final String getDescription() {
    return this.descriptionProperty().get();
  }

  public final void setDescription(final String description) {
    this.descriptionProperty().set(description);
  }

  private final StringProperty status = new SimpleStringProperty();

  public final StringProperty statusProperty() {
    return this.status;
  }

  public final String getStatus() {
    return this.statusProperty().get();
  }

  public final void setStatus(final String status) {
    this.statusProperty().set(status);
  }

  private final StringProperty topic = new SimpleStringProperty();

  public final StringProperty topicProperty() {
    return this.topic;
  }

  public final String getTopic() {
    return this.topicProperty().get();
  }

  public final void setTopic(final String topic) {
    this.topicProperty().set(topic);
  }

  private final StringProperty validity = new SimpleStringProperty();

  public final StringProperty validityProperty() {
    return this.validity;
  }

  public final String getValidity() {
    return this.validityProperty().get();
  }

  public final void setValidity(final LocalDateTime validity) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    this.validityProperty().set(validity.format(formatter));
  }

  private final StringProperty voteInformation = new SimpleStringProperty("Please login");

  public final StringProperty voteInformationProperty() {
    return this.voteInformation;
  }

  public final String getVoteInformation() {
    return this.voteInformationProperty().get();
  }

  public final void setVoteInformation(final String voteInformation) {
    this.voteInformationProperty().set(voteInformation);
  }

  private final StringProperty supporterCount = new SimpleStringProperty("Please login");

  public final StringProperty supporterCountProperty() {
    return this.supporterCount;
  }

  public final String getSupporterCount() {
    return this.supporterCountProperty().get();
  }

  public final void setSupporterCount(final String supporterCount) {
    this.supporterCountProperty().set(supporterCount);
  }

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
