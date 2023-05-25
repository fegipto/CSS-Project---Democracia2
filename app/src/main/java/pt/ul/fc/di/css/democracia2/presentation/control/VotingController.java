package pt.ul.fc.di.css.democracia2.presentation.control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import pt.ul.fc.di.css.democracia2.presentation.model.DataModel;

public class VotingController {

  @FXML private TextArea title;
  @FXML private TextArea topic;
  @FXML private TextArea description;
  @FXML private Button button;
  private DataModel model;

  public void initModel(DataModel model) {
    if (this.model != null) {
      throw new IllegalStateException("Model can only be initialized once");
    }

    this.model = model;
  }
}
