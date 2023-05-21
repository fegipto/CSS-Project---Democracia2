package pt.ul.fc.css.democracia2.javafx.model;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.ul.fc.css.democracia2.DTO.BillDTO;
import pt.ul.fc.css.democracia2.services.ListAvailableVotesService;

public class DataModel {

    private final ListAvailableVotesService listAvailableVotesService;

    /* in this way personList also reports
     * mutations of the elements in it by using the given extractor.
     * Observable objects returned by extractor (applied to each list element) are listened
     * for changes and transformed into "update" change of ListChangeListener.
     * since the phone is not visible, changes in the phone do not need to be propagated
     */
    private final ObservableList<BillDTO> availableVotesList =
            FXCollections.observableArrayList(bill ->
                    new Observable[] {(Observable) bill});

    public DataModel(ListAvailableVotesService listAvailableVotesService) {
        this.listAvailableVotesService = listAvailableVotesService;
    }

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
    }

    public void saveData() { }
}
