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
import org.springframework.web.util.UriComponentsBuilder;
import pt.ul.fc.di.css.democracia2.DTO.BillDTO;
import pt.ul.fc.di.css.democracia2.DTO.CitizenDTO;

/**
 * Class that represents a DataModel
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
public class DataModel {

  // DATA FOR BILLS
  //Observable list of Bills
  private final ObservableList<Bill> bills =
      FXCollections.observableArrayList(bill -> new Observable[] {bill.titleProperty()});

  /**
   * Method that gets the observable list of Bills (votable or supportable)
   *
   * @return the observable list of Bills
   */
  public ObservableList<Bill> getBills() {
    return bills;
  }

  //Currently selected Bill
  private final ObjectProperty<Bill> currentBill = new SimpleObjectProperty<>(null);

  /**
   * Method that gets the current Bill as an {@code ObjectProperty<>}
   *
   * @return the current Bill as an {@code ObjectProperty<>}
   */
  public ObjectProperty<Bill> currentBillProperty() {
    return currentBill;
  }

  /**
   * Method that gets the current Bill
   *
   * @return the current Bill
   */
  public final Bill getCurrentBill() {
    return currentBillProperty().get();
  }

  /**
   * Method that sets the given Bill as the currently selected
   *
   * @param bill the Bill to set as currently selected
   */
  public final void setCurrentBill(Bill bill) {
    currentBillProperty().set(bill);
  }

  // DATA FOR LOGGED CITIZEN
  //Currently logged Citizen's id
  private final LongProperty loggedCitizen = new SimpleLongProperty(-1);

  /**
   * Method that gets the id of the currently logged Citizen as an {@code LongProperty}
   *
   * @return the id of the currently logged Citizen as an {@code LongProperty}
   */
  public final LongProperty loggedCitizenProperty() {
    return this.loggedCitizen;
  }

  /**
   * Method that gets the id of the currently logged Citizen
   *
   * @return the id of the currently logged Citizen
   */
  public final Long getLoggedCitizen() {
    return this.loggedCitizenProperty().get();
  }

  /**
   * Method that sets the given id as the currently logged Citizen's id
   *
   * @param loggedCitizen the id of the currently logged Citizen
   */
  public final void setloggedCitizen(final Long loggedCitizen) {
    this.loggedCitizenProperty().set(loggedCitizen);
  }

  // SUPPORT AND VOTE
  /**
   * Method that sets the given vote as the vote of the current Citizen to the current Bill
   *
   * @param vote the vote of the current Citizen to the current Bill
   */
  public final void setVoteBill(boolean vote) {
    Long citizen = this.getLoggedCitizen();
    Long bill = this.getCurrentBill().getId();
    RestTemplate restTemplate = new RestTemplate();

    UriComponentsBuilder builder =
        UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/bill/vote")
            .queryParam("citizen", citizen)
            .queryParam("bill", bill)
            .queryParam("vote", vote);

    ResponseEntity<Boolean> resp =
        restTemplate.postForEntity(builder.toUriString(), null, Boolean.class);
    Boolean success = resp.getBody();
    if (success != null && success) {
      Bill b = this.getCurrentBill();
      b.setVoteInformation("voted");
      this.setCurrentBill(b);
    }
  }

  /**
   * Method that sets the current Citizen as a supporter of the current Bill
   */
  public final void setSupportBill() {
    Long citizen = this.getLoggedCitizen();
    Long bill = this.getCurrentBill().getId();
    RestTemplate restTemplate = new RestTemplate();

    UriComponentsBuilder builder =
        UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/bill/support")
            .queryParam("citizen", citizen)
            .queryParam("bill", bill);

    ResponseEntity<Boolean> resp =
        restTemplate.postForEntity(builder.toUriString(), null, Boolean.class);
    Boolean success = resp.getBody();
    if (success != null && success)
      this.currentBillProperty()
          .get()
          .setSupporterCount(
              (Integer.valueOf(this.currentBillProperty().get().getSupporterCount()) + 1) + "");
  }

  // LOAD AND SAVE DATA
  /**
   * Method that generates votable Bills
   */
  public void generateVotables() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getForEntity("http://localhost:8080/api/test/votable/bills", null);
  }

  /**
   * Method that generates supportable Bills
   */
  public void generateSupportables() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getForEntity("http://localhost:8080/api/test/supportable/bills", null);
  }

  /**
   * Method that sets the observable list of Bills as the available votable Bills
   */
  public void loadVotable() {
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<BillDTO[]> responseEntity =
        restTemplate.getForEntity("http://localhost:8080/api/bills/votable", BillDTO[].class);
    BillDTO[] bills = responseEntity.getBody();
    if (bills != null) {
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
  }

    /**
   * Method that sets the observable list of Bills as the available supportable Bills
   */
  public void loadOpen() {
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<BillDTO[]> responseEntity =
        restTemplate.getForEntity("http://localhost:8080/api/bills/open", BillDTO[].class);
    BillDTO[] bills = responseEntity.getBody();
    if (bills != null) {
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
  }

  /**
   * Method that tries to login the Citizen with the given name
   *
   * @param input the name of the Citizen to try to login
   */
  public void login(String input) {
    RestTemplate votedRestTemplate = new RestTemplate();
    String url = "http://localhost:8080/api/citizen/name/{name}";
    ResponseEntity<CitizenDTO> votedRestTemplateResponseEntity =
        votedRestTemplate.getForEntity(url, CitizenDTO.class, input);
    CitizenDTO user = votedRestTemplateResponseEntity.getBody();
    if (user != null) this.setloggedCitizen(user.getCc());
  }

  /**
   * Method that gets the voting information of the current Citizen to the given Bill
   *
   * @param bill the Bill to get the information of the vote of the current Citizen
   * @return the information of the vote of the current Citizen
   */
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
