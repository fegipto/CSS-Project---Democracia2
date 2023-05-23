package pt.ul.fc.css.democracia2.javafx.presentation.control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import pt.ul.fc.css.democracia2.javafx.presentation.model.Bill;
import pt.ul.fc.css.democracia2.javafx.presentation.model.DataModel;

public class VotingController {

    @FXML
    private SingleSelectionModel<Bill> singleSelectionModel;
    @FXML
    private Button button;
    private DataModel model;

    public void initModel(DataModel model) {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }

        this.model = model;
        singleSelectionModel.select(model.getCurrentBill());

        button.setOnAction(e -> {
            model.setVoteBill(singleSelectionModel.getSelectedItem());
        });
    }
}
