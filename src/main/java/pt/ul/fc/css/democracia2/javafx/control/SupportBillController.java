package pt.ul.fc.css.democracia2.javafx.control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import pt.ul.fc.css.democracia2.javafx.model.Bill;
import pt.ul.fc.css.democracia2.javafx.model.DataModel;

public class SupportBillController {

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
        singleSelectionModel.select(model.getCurrentNonExpiredBill());

        button.setOnAction(e -> {
            model.setSupportBill(singleSelectionModel.getSelectedItem());
        });
    }
}
