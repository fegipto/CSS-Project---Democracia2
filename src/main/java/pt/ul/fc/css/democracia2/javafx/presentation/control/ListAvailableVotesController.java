package pt.ul.fc.css.democracia2.javafx.presentation.control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import pt.ul.fc.css.democracia2.javafx.presentation.model.Bill;
import pt.ul.fc.css.democracia2.javafx.presentation.model.DataModel;

public class ListAvailableVotesController {
    @FXML
    private ListView<Bill> listView;
    @FXML
    private Button button;
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

        button.setOnAction(e -> {
            model.viewCurrentBill();
        });
    }
}
