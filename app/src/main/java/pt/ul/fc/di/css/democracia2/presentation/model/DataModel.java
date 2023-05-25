package pt.ul.fc.di.css.democracia2.presentation.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pt.ul.fc.di.css.democracia2.DTO.BillDTO;

public class DataModel {

  // private final ListAvailableVotesService listAvailableVotesService;
  // private final ConsultNonExpiredBillService consultNonExpiredBillService;

  // public DataModel(ListAvailableVotesService listAvailableVotesService,
  //                 ConsultNonExpiredBillService consultNonExpiredBillService) {
  //    this.listAvailableVotesService = listAvailableVotesService;
  //    this.consultNonExpiredBillService = consultNonExpiredBillService;
  // }

  // DATA FOR AVAILABLE VOTINGS
  private final ObservableList<Bill> availableVotesList =
      FXCollections.observableArrayList(bill -> new Observable[] {bill.titleProperty()});

  public ObservableList<Bill> getAvailableVotesList() {
    return availableVotesList;
  }

  private final ObjectProperty<Bill> currentBill = new SimpleObjectProperty<>(null);

  public ObjectProperty<Bill> currentBillProperty() {
    return currentBill;
  }

  public final Bill getCurrentBill() {
    return currentBillProperty().get();
  }

  public final void setCurrentBill(Bill bill) {
    currentBillProperty().set(bill);
  }

  // TODO
  public final void setVoteBill() {}

  // DATA FOR NON-EXPIRED-BILLS
  private final ObservableList<Bill> nonExpiredBillList =
      FXCollections.observableArrayList(
          bill -> new Observable[] {bill.titleProperty(), bill.topicProperty()});

  public ObservableList<Bill> getNonExpiredBillList() {
    return availableVotesList;
  }

  private final ObjectProperty<Bill> currentNonExpiredBill = new SimpleObjectProperty<>(null);

  public ObjectProperty<Bill> currentNonExpiredBillProperty() {
    return currentNonExpiredBill;
  }

  public final Bill getCurrentNonExpiredBill() {
    return currentNonExpiredBillProperty().get();
  }

  public final void setCurrentNonExpiredBill(Bill bill) {
    currentNonExpiredBillProperty().set(bill);
  }

  // TODO
  public final void setSupportBill() {}

  // LOAD AND SAVE DATA
  // TODO
  public void generateVotables() {
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<Object> responseEntity =
        restTemplate.getForEntity("http://localhost:8080/api/test/votable/bills", null);
  }

  // TODO
  public void saveData(File file) {}

  public void loadOpen() {
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<BillDTO[]> responseEntity =
        restTemplate.getForEntity("http://localhost:8080/api/bills/open", BillDTO[].class);
    BillDTO[] bills = responseEntity.getBody();
    List<Bill> billPs = new ArrayList<>();
    for (BillDTO bill : bills) {
      billPs.add(new Bill(bill));
    }
    availableVotesList.setAll(billPs);
  }
}
