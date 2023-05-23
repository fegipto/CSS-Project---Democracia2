package pt.ul.fc.css.democracia2.javafx.presentation.model;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

public class DataModel {

    //private final ListAvailableVotesService listAvailableVotesService;
    //private final ConsultNonExpiredBillService consultNonExpiredBillService;

    //public DataModel(ListAvailableVotesService listAvailableVotesService,
    //                 ConsultNonExpiredBillService consultNonExpiredBillService) {
    //    this.listAvailableVotesService = listAvailableVotesService;
    //    this.consultNonExpiredBillService = consultNonExpiredBillService;
    //}

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

    //TODO
    public final void setVoteBill(Bill bill) {
    }

    //TODO
    public final void viewCurrentBill() {
    }

    // DATA FOR NON-EXPIRED-BILLS
    private final ObservableList<Bill> nonExpiredBillList =
            FXCollections.observableArrayList(bill ->
                    new Observable[] {bill.titleProperty(), bill.descriptionProperty()});

    public ObservableList<Bill> getNonExpiredBillList() {
        return availableVotesList;
    }

    private final ObjectProperty<Bill> currentNonExpiredBill = new SimpleObjectProperty<>(null);

    public ObjectProperty<Bill> currentNonExpiredBillProperty() { return currentNonExpiredBill; }

    public final Bill getCurrentNonExpiredBill() {
        return currentNonExpiredBillProperty().get();
    }

    public final void setCurrentNonExpiredBill(Bill bill) {
        currentNonExpiredBillProperty().set(bill);
    }

    //TODO
    public final void setSupportBill(Bill selectedItem) {
    }

    //TODO
    public final void viewCurrentNonExpiredBill() {
    }

    // LOAD AND SAVE DATA
    //TODO
    public void loadData(File file) {
        // mock...
        //personList.setAll(
        //        new Person("Jose", "Silva", 934445678 ),
        //        new Person("Isabel", "Ramos",912765432),
        //        new Person("Eloi", "Matos", 965436576),
        //        new Person("Ema", "Antunes", 217122121),
        //        new Person("Paulo", "Guerra", 217500504)
        //);
        //for (BillDTO b: listAvailableVotesService.listAvailableVotes()) {
        //    availableVotesList.add(new Bill(b));
        //}
        //for (BillDTO b: consultNonExpiredBillService.listNonExpired()) {
        //    nonExpiredBillList.add(new Bill(b));
        //}
    }

    //TODO
    public void saveData(File file) { }
}
