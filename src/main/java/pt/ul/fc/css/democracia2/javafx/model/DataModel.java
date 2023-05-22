package pt.ul.fc.css.democracia2.javafx.model;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.ul.fc.css.democracia2.DTO.BillDTO;
import pt.ul.fc.css.democracia2.services.ConsultNonExpiredBillService;
import pt.ul.fc.css.democracia2.services.ListAvailableVotesService;

public class DataModel {

    private final ListAvailableVotesService listAvailableVotesService;
    private ConsultNonExpiredBillService consultNonExpiredBillService;

    public DataModel(ListAvailableVotesService listAvailableVotesService,
                     ConsultNonExpiredBillService consultNonExpiredBillService) {
        this.listAvailableVotesService = listAvailableVotesService;
        this.consultNonExpiredBillService = consultNonExpiredBillService;
    }

    // DATA FOR AVAILABLE VOTINGS
    private final ObservableList<Bill> availableVotesList =
            FXCollections.observableArrayList(bill ->
                    new Observable[] {bill.titleProperty(), bill.descriptionProperty()});

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

    public ObservableList<Bill> getNonExpiredBillList() {
        return availableVotesList;
    }

    public final void setVoteBill(Bill bill) {
    }

    // DATA FOR NON-EXPIRED-BILLS
    private final ObservableList<Bill> nonExpiredBillList =
            FXCollections.observableArrayList(bill ->
                    new Observable[] {bill.titleProperty(), bill.descriptionProperty()});

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

    public void setSupportBill(Bill selectedItem) {
    }

    // LOAD AND SAVE DATA
    public void loadData() {
        // mock...
        //personList.setAll(
        //        new Person("Jose", "Silva", 934445678 ),
        //        new Person("Isabel", "Ramos",912765432),
        //        new Person("Eloi", "Matos", 965436576),
        //        new Person("Ema", "Antunes", 217122121),
        //        new Person("Paulo", "Guerra", 217500504)
        //);
        for (BillDTO b: listAvailableVotesService.listAvailableVotes()) {
            availableVotesList.add(new Bill(b));
        }
        for (BillDTO b: consultNonExpiredBillService.listNonExpired()) {
            nonExpiredBillList.add(new Bill(b));
        }
    }

    public void saveData() { }
}
