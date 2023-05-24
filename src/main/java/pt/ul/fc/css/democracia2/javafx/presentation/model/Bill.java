package pt.ul.fc.css.democracia2.javafx.presentation.model;

import javafx.beans.property.*;

import java.time.LocalDateTime;

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

    private final ObjectProperty<BillStatus> status = new SimpleObjectProperty<>();

    public final ObjectProperty<BillStatus> statusProperty() {
        return this.status;
    }

    public final BillStatus getStatus() {
        return this.statusProperty().get();
    }

    public final void setStatus(final BillStatus status) {
        this.statusProperty().set(status);
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

    private final ObjectProperty<LocalDateTime> validity = new SimpleObjectProperty<>();

    public final ObjectProperty<LocalDateTime> validityProperty() {
        return this.validity;
    }

    public final LocalDateTime getValidity() { return this.validityProperty().get(); }

    public final void setValidity(final LocalDateTime validity) {
        this.validityProperty().set(validity);
    }

    private final ObjectProperty<Topic> topic = new SimpleObjectProperty<>();

    public final ObjectProperty<Topic> topicProperty() {
        return this.topic;
    }

    public final Topic getTopic() {
        return this.topicProperty().get();
    }

    public final void setTopic(final Topic topic) { this.topicProperty().set(topic); }

    private final ObjectProperty<byte[]> file = new SimpleObjectProperty<>();

    public final ObjectProperty<byte[]> fileProperty() { return this.file; }

    public final byte[] getFile() {
        return this.fileProperty().get();
    }

    public final void setFile(final byte[] file) {
        this.fileProperty().set(file);
    }

    public Bill(long id, String title, BillStatus status, String description, LocalDateTime validity, Topic topic,
                byte[] file) {
        setId(id);
        setTitle(title);
        setStatus(status);
        setDescription(description);
        setValidity(validity);
        setTopic(topic);
        setFile(file);
    }

    @Override
    public String toString() {
        return "Title: " + title.getValue()+"\nTopic: " + topic.getValue()
                + "\nDescription: " + description.getValue();
    }
}
