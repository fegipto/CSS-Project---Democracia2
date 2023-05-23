package pt.ul.fc.css.democracia2.javafx.presentation.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Class that represents a Delegate
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
public class Delegate extends Citizen {

  private ListProperty<Bill> bills = new SimpleListProperty<>();

  public List<Bill> getBills() {
    return bills;
  }

  public String getName() {
    return super.getName();
  }

  public long getCC() {
    return super.getCC();
  }

  public long getToken() {
    return super.getToken();
  }

  public void chooseDelegate(Delegate delegate, Topic topic) {
    super.chooseDelegate(delegate, topic);
  }

  public Delegate getChosenDelegate(Topic topic) {
    return super.getChosenDelegate(topic);
  }

  public boolean delegateExists(Topic topic) {
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

  public Delegate(String name, long cc) {
    super(name, cc, cc, new HashMap<>());
  }
}
