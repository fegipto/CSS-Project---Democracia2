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
                information
                    .textProperty()
                    .bindBidirectional(newBill.voteInformationProperty(), converter);
                if (newBill.getVoteInformation() != null
                    && newBill.getStatus().equals("VOTING")
                    && newBill.getVoteInformation().equals("voted")) {
                  yes.setVisible(false);
                  no.setVisible(false);
                } else if (newBill.getStatus().equals("CREATED")
                    && model.getLoggedCitizen() != -1) {
                  support.setVisible(true);
                } else if (model.getLoggedCitizen() != -1) {
                  yes.setVisible(true);
                  no.setVisible(true);
                }
              }
            });

    yes.setOnAction(
        e -> {
          model.setVoteBill();
        });
    no.setOnAction(
        e -> {
          model.setVoteNoBill();
        });
  }
}
