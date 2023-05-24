package pt.ul.fc.css.democracia2.javafx.presentation.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * Class that represents a Delegate
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
public class Delegate extends Citizen {

    private final ListProperty<Bill> bills = new SimpleListProperty<>();

    public final ListProperty<Bill> billsProperty() {
      return this.bills;
    }

    public final List<Bill> getBills() { return this.billsProperty().get(); }

    public final void setBills(final List<Bill> bills) {
      this.billsProperty().set((ObservableList<Bill>) bills);
    }

    public final String getName() {
      return super.getName();
    }

    public final Long getCC() {
      return super.getCC();
    }

    public final Long getToken() {
      return super.getToken();
    }

    public final void setChosenDelegate(Delegate delegate, Topic topic) { super.setChosenDelegate(delegate, topic); }

    public final Delegate getChosenDelegate(Topic topic) {
      return super.getChosenDelegate(topic);
    }

    public final boolean delegateExists(Topic topic) {
      return super.delegateExists(topic);
    }

    public Bill proposeBill(Long id, String title, String description, byte[] file, BillStatus status,
                            LocalDateTime validity, Topic topic) {
      LocalDateTime now = LocalDateTime.now();
      if (!validity.isAfter(now) || validity.isAfter(now.plusYears(1))) return null;
      Bill adding = new Bill(id, title, status, description, validity, topic, file);

      bills.add(adding);
      return adding;
    }

    public Delegate(String name, long cc, List<Bill> bills) {
        super(name, cc, cc, new HashMap<>());
        setBills(bills);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
