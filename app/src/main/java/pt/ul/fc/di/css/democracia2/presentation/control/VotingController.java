package pt.ul.fc.di.css.democracia2.presentation.control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import pt.ul.fc.di.css.democracia2.presentation.model.DataModel;

public class VotingController {

  @FXML private Button yes;
  @FXML private Button no;
  private DataModel model;

  public void initModel(DataModel model) {
    if (this.model != null) {
      throw new IllegalStateException("Model can only be initialized once");
    }

    this.model = model;
    yes.setOnAction(e -> {
      model.setVoteBill();
    });
    no.setOnAction(e -> {
      model.setVoteNoBill();
    });
  }
}
