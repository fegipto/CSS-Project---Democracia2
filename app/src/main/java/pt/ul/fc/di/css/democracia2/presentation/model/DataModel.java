package pt.ul.fc.di.css.democracia2.presentation.model;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.Observable;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pt.ul.fc.di.css.democracia2.DTO.BillDTO;
import pt.ul.fc.di.css.democracia2.DTO.CitizenDTO;

public class DataModel {

  // DATA FOR AVAILABLE VOTINGS
  private final ObservableList<Bill> bills =
      FXCollections.observableArrayList(bill -> new Observable[] {bill.titleProperty()});

  public ObservableList<Bill> getBills() {
    return bills;
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
  // DATA FOR AVAILABLE VOTINGS
  private final LongProperty loggedCitizen = new SimpleLongProperty(-1);

  public final LongProperty loggedCitizenProperty() {
    return this.loggedCitizen;
  }

  public final Long getLoggedCitizen() {
    return this.loggedCitizenProperty().get();
  }

  public final void setloggedCitizen(final Long loggedCitizen) {
    this.loggedCitizenProperty().set(loggedCitizen);
  }

  // TODO
  public final void setVoteBill() {}

  // TODO
  public final void setVoteNoBill() {}

  // TODO
  public final void setSupportBill() {}

  // LOAD AND SAVE DATA
  // TODO
  public void generateVotables() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getForEntity("http://localhost:8080/api/test/votable/bills", null);
  }

  public void generateSupportables() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getForEntity("http://localhost:8080/api/test/supportable/bills", null);
  }

  public void loadVotable() {
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<BillDTO[]> responseEntity =
        restTemplate.getForEntity("http://localhost:8080/api/bills/votable", BillDTO[].class);
    BillDTO[] bills = responseEntity.getBody();
    List<Bill> billPs = new ArrayList<>();

    for (BillDTO bill : bills) {
      Bill b = new Bill(bill);
      if (this.getLoggedCitizen() != -1) {
        b.setVoteInformation(getVoteInformation(bill));
      }
      billPs.add(b);
    }
    this.bills.setAll(billPs);
  }

  public void loadOpen() {
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<BillDTO[]> responseEntity =
        restTemplate.getForEntity("http://localhost:8080/api/bills/open", BillDTO[].class);
    BillDTO[] bills = responseEntity.getBody();
    List<Bill> billPs = new ArrayList<>();
    for (BillDTO bill : bills) {
      Bill b = new Bill(bill);
      if (this.getLoggedCitizen() != -1) {
        b.setVoteInformation(getVoteInformation(bill));
      }
      billPs.add(b);
    }
    this.bills.setAll(billPs);
  }

  public void login(String input) {
    RestTemplate votedRestTemplate = new RestTemplate();
    String url = "http://localhost:8080/api/citizen/name/{name}";
    ResponseEntity<CitizenDTO> votedRestTemplateResponseEntity =
        votedRestTemplate.getForEntity(url, CitizenDTO.class, input);
    CitizenDTO user = votedRestTemplateResponseEntity.getBody();
    if (user != null) this.setloggedCitizen(user.getCc());
  }

  private String getVoteInformation(BillDTO bill) {
    if (!bill.getStatus().name().equals("VOTING")) return "";
    RestTemplate votedRestTemplate = new RestTemplate();
    String url = "http://localhost:8080/api/bill/{citizenId}/voted/{billId}";
    String voteInfo = null;
    Long citizenId = this.getLoggedCitizen();
    Long billId = bill.getId();
    ResponseEntity<String> votedRestTemplateResponseEntity =
        votedRestTemplate.getForEntity(url, String.class, citizenId, billId);
    voteInfo = votedRestTemplateResponseEntity.getBody();

    return voteInfo;
  }
}
