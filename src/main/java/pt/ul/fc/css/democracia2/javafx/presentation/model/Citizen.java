package pt.ul.fc.css.democracia2.javafx.presentation.model;


import javafx.beans.property.*;
import javafx.collections.ObservableMap;

import java.util.Map;

public class Citizen {

    private final StringProperty name = new SimpleStringProperty();

    public final StringProperty nameProperty() {
      return this.name;
    }

    public String getName() {
      return this.nameProperty().get();
    }

    public final void setName(final String name) {
      this.nameProperty().set(name);
    }

    private final LongProperty cc = new SimpleLongProperty();

    public final LongProperty ccProperty() {
      return this.cc;
    }

    public Long getCC() {
      return this.ccProperty().get();
    }

    public final void setCC(final Long cc) {
      this.ccProperty().set(cc);
    }

    private final LongProperty token = new SimpleLongProperty();

    public final LongProperty tokenProperty() {
      return this.token;
    }

    public Long getToken() {
      return this.tokenProperty().get();
    }

    public final void setToken(final Long token) {
      this.tokenProperty().set(token);
    }

    private final MapProperty<Topic, Delegate> chosenDelegates = new SimpleMapProperty<>();

    public final MapProperty<Topic, Delegate> chosenDelegatesProperty() {
      return this.chosenDelegates;
    }

    public final Map<Topic, Delegate> getChosenDelegates() {
      return this.chosenDelegatesProperty().get();
    }

    public final void setChosenDelegates(final Map<Topic, Delegate> chosenDelegates) {
      this.chosenDelegatesProperty().set((ObservableMap<Topic, Delegate>) chosenDelegates);
    }

    public void setChosenDelegate(Delegate delegate, Topic topic) { chosenDelegates.put(topic, delegate); }

    public Delegate getChosenDelegate(Topic topic) { return this.getChosenDelegates().get(topic); }

    public boolean delegateExists(Topic topic) { return this.getChosenDelegates().containsKey(topic); }

    public Citizen(String name, long cc, long token, Map<Topic, Delegate> chosenDelegates) {
      setName(name);
      setCC(cc);
      setToken(token);
      setChosenDelegates(chosenDelegates);
    }

    @Override
    public String toString() {
      return "Name: " + name.getValue()+"\tCC: " + cc.getValue() + "\nChosen delegates: " + chosenDelegates.getValue();
    }
}