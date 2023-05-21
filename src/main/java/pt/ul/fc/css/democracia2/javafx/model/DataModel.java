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
    private final ConsultNonExpiredBillService consultNonExpiredBillService;

    private final ObservableList<BillDTO> availableVotesList =
            FXCollections.observableArrayList(bill ->
                    new Observable[] {(Observable) bill});

    private final ObservableList<BillDTO> nonExpiredBillList =
            FXCollections.observableArrayList(bill ->
                    new Observable[] {(Observable) bill});

    public DataModel(ListAvailableVotesService listAvailableVotesService,
                     ConsultNonExpiredBillService consultNonExpiredBillService) {
        this.listAvailableVotesService = listAvailableVotesService;
        this.consultNonExpiredBillService = consultNonExpiredBillService;
    }

    //DATA FOR AVAILABLE VOTINGS
    public ObservableList<BillDTO> getAvailableVotesList() {
        return availableVotesList;
    }

    private final ObjectProperty<BillDTO> currentBill = new SimpleObjectProperty<>(null);

    public ObjectProperty<BillDTO> currentBillProperty() {
        return currentBill;
    }

    public final BillDTO getCurrentBill() {
        return currentBillProperty().get();
    }

    public final void setCurrentBill(BillDTO bill) {
        currentBillProperty().set(bill);
    }

    public ObservableList<BillDTO> getNonExpiredBillList() {
        return availableVotesList;
    }

    //DATA FOR NON-EXPIRED-BILLS
    private final ObjectProperty<BillDTO> currentNonExpiredBill = new SimpleObjectProperty<>(null);

    public ObjectProperty<BillDTO> currentNonExpiredBillProperty() {
        return currentBill;
    }

    public final BillDTO getCurrentNonExpiredBill() {
        return currentNonExpiredBillProperty().get();
    }

    public final void setCurrentNonExpiredBill(BillDTO bill) {
        currentNonExpiredBillProperty().set(bill);
    }

    public void loadData() {
        // mock...
        //personList.setAll(
        //        new Person("Jose", "Silva", 934445678 ),
        //        new Person("Isabel", "Ramos",912765432),
        //        new Person("Eloi", "Matos", 965436576),
        //        new Person("Ema", "Antunes", 217122121),
        //        new Person("Paulo", "Guerra", 217500504)
        //);
        availableVotesList.setAll(listAvailableVotesService.listAvailableVotes());
        nonExpiredBillList.setAll(consultNonExpiredBillService.listNonExpired());
    }

    public void saveData() { }
}
