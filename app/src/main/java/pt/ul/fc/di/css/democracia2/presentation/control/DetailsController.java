package pt.ul.fc.di.css.democracia2.presentation.control;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import pt.ul.fc.di.css.democracia2.presentation.model.DataModel;

public class DetailsController {

  @FXML private Text title;
  @FXML private Text topic;
  @FXML private Text description;
  @FXML private Text status;
  @FXML private Text validity;
  @FXML private Text proponent;
  @FXML private Text supporterInfo;
  @FXML private Text supporterCount;

  private DataModel model;

  public void initModel(DataModel model) {
    if (this.model != null) {
      throw new IllegalStateException("Model can only be initialized once");
    }
    supporterInfo.setVisible(false);

    this.model = model;
    model
        .currentBillProperty()
        .addListener(
            (obs, oldBill, newBill) -> {
              supporterInfo.setVisible(false);
              supporterCount.setVisible(false);

              if (oldBill != null) {
                title.textProperty().unbindBidirectional(oldBill.titleProperty());
                topic.textProperty().unbindBidirectional(oldBill.topicProperty());
                status.textProperty().unbindBidirectional(oldBill.statusProperty());
                validity.textProperty().unbindBidirectional(oldBill.validityProperty());
                proponent.textProperty().unbindBidirectional(oldBill.proponentNameProperty());
                supporterCount.textProperty().unbindBidirectional(oldBill.supporterCountProperty());
              }
              if (newBill == null) {
                title.setText("");
                topic.setText("");
                status.setText("");
                validity.setText("");
                proponent.setText("");
                description.setText("");
                supporterCount.setText("");

              } else {
                title.textProperty().bindBidirectional(newBill.titleProperty());
                topic.textProperty().bindBidirectional(newBill.topicProperty());
                status.textProperty().bindBidirectional(newBill.statusProperty());
                validity.textProperty().bindBidirectional(newBill.validityProperty());
                proponent.textProperty().bindBidirectional(newBill.proponentNameProperty());
                description.textProperty().bindBidirectional(newBill.descriptionProperty());

                if (newBill.getStatus().equals("CREATED")) {
                  supporterCount.textProperty().bindBidirectional(newBill.supporterCountProperty());
                  supporterInfo.setVisible(true);
                  supporterCount.setVisible(true);
                }
              }
            });
  }
}
