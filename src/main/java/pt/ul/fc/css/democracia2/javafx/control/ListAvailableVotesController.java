package pt.ul.fc.css.democracia2.javafx.control;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import pt.ul.fc.css.democracia2.DTO.BillDTO;
import pt.ul.fc.css.democracia2.javafx.model.DataModel;

public class ListAvailableVotesController {
    @FXML
    private ListView<BillDTO> listView ;
    private DataModel model;

    public void initModel(DataModel model) {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }

        this.model = model;
        listView.setItems(model.getAvailableVotesList());

        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            model.setCurrentBill(newSelection);
        });
    }
}
