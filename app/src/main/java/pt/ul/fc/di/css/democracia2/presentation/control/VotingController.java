package pt.ul.fc.di.css.democracia2.presentation.control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import pt.ul.fc.di.css.democracia2.presentation.model.DataModel;

public class VotingController {

  @FXML private Button yes;
  @FXML private Button no;
  @FXML private Button support;
  @FXML private AnchorPane actionPane;
  @FXML private Text information;

  StringConverter<String> converter =
      new StringConverter<String>() {
        @Override
        public String toString(String object) {
          if (object == null) {
            return "Vote:";
          } else if ("voted".equals(object)) {
            yes.setVisible(false);
            no.setVisible(false);
            return "You have voted";
          } else if ("yes".equals(object)) {
            return "Vote: (Omitted vote yes)";
          } else if ("no".equals(object)) {
            return "Vote: (Omitted vote no)";
          } else {
            return object;
          }
        }

        @Override
        public String fromString(String string) {

          if ("Vote:".equals(string)) {
            return null;
          } else if ("You have voted".equals(string)) {

            return "voted";
          } else if ("Vote: (Omitted vote yes)".equals(string)) {
            return "yes";
          } else if ("Vote: (Omitted vote no)".equals(string)) {
            return "no";
          } else {
            return string;
          }
        }
      };

  private DataModel model;

  public void initModel(DataModel model) {
    if (this.model != null) {
      throw new IllegalStateException("Model can only be initialized once");
    }

    this.model = model;
    yes.setVisible(false);
    no.setVisible(false);
    support.setVisible(false);
    model
        .currentBillProperty()
        .addListener(
            (obs, oldBill, newBill) -> {
              if (oldBill != null) {
                information.textProperty().unbindBidirectional(oldBill.voteInformationProperty());
                yes.setVisible(false);
                no.setVisible(false);
                support.setVisible(false);
              }
              if (newBill == null) {
                yes.setVisible(false);
                no.setVisible(false);
                support.setVisible(false);
              } else {

                if (newBill.getStatus().equals("CREATED")) support.setVisible(true);
                else if (newBill.getStatus().equals("VOTING")) {
                  yes.setVisible(true);
                  no.setVisible(true);
                } else {
                  yes.setVisible(false);
                  no.setVisible(false);
                  support.setVisible(false);
                }
                information
                    .textProperty()
                    .bindBidirectional(newBill.voteInformationProperty(), converter);
                if (model.getLoggedCitizen() == -1) {
                  yes.setVisible(false);
                  no.setVisible(false);
                  support.setVisible(false);
                }
              }
            });

    yes.setOnAction(
        e -> {
          model.setVoteBill(true);
        });
    no.setOnAction(
        e -> {
          model.setVoteBill(false);
        });
    support.setOnAction(
        e -> {
          model.setSupportBill();
        });
  }
}
