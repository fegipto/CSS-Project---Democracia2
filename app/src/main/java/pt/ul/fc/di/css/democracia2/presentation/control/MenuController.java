package pt.ul.fc.di.css.democracia2.presentation.control;

import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import pt.ul.fc.di.css.democracia2.presentation.model.DataModel;

public class MenuController {

  @FXML private MenuItem menuItem;
  private DataModel model;

  public void initModel(DataModel model) {
    if (this.model != null) {
      throw new IllegalStateException("Model can only be initialized once");
    }
    this.model = model;
  }

  @FXML
  public void generateSupportables() {
    model.generateSupportables();
  }

  @FXML
  public void generateVotables() {
    model.generateVotables();
  }

  @FXML
  public void loadOpen() {
    model.loadOpen();
  }

  @FXML
  public void loadVotable() {
    model.loadVotable();
  }

  @FXML
  public void login() {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Login");
    dialog.setHeaderText("Enter your username:");
    Optional<String> result = dialog.showAndWait();
    if (result.isPresent()) {
      String input = result.get();
      model.login(input);
      model.loadVotable();
    }
  }

  @FXML
  public void exit() {
    menuItem.getParentPopup().getOwnerWindow().getScene().getWindow().hide();
  }
}
