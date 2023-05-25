package pt.ul.fc.css.democracia2.javafx.presentation.control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import pt.ul.fc.css.democracia2.javafx.presentation.model.DataModel;

public class VotingController {

    @FXML
    private TextArea title;
    @FXML
    private TextArea topic;
    @FXML
    private TextArea description;
    @FXML
    private Button button;
    private DataModel model;

    public void initModel(DataModel model) {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }

        this.model = model;
        model.currentBillProperty().addListener((obs, oldBill, newBill) -> {
            if (oldBill != null) {
                title.textProperty().unbindBidirectional(oldBill.titleProperty());
                topic.textProperty().unbindBidirectional(oldBill.topicStringProperty());
                description.textProperty().unbindBidirectional(oldBill.descriptionProperty());
            }
            if (newBill == null) {
                title.setText("");
                topic.setText("");
                description.setText("");
            } else {
                title.textProperty().bindBidirectional(newBill.titleProperty());
                topic.textProperty().bindBidirectional(newBill.topicStringProperty());
                description.textProperty().bindBidirectional(newBill.descriptionProperty());
            }
        });

        button.setOnAction(e -> {
            model.setVoteBill();
        });
    }
}
