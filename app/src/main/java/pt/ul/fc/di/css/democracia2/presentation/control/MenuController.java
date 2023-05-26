package pt.ul.fc.di.css.democracia2.presentation.control;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import pt.ul.fc.di.css.democracia2.presentation.model.DataModel;

public class MenuController {

  @FXML private MenuBar menuBar;
  private DataModel model;

  public void initModel(DataModel model) {
    if (this.model != null) {
      throw new IllegalStateException("Model can only be initialized once");
    }
    this.model = model;
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
  public void generateSupportables() {
    model.generateSupportables();
  }

  @FXML
  public void loadSupportable() {
    model.loadSupportable();
  }

  @FXML
  public void save() {
    FileChooser chooser = new FileChooser();
    File file = chooser.showOpenDialog(menuBar.getScene().getWindow());
    if (file != null) {
      try {
        model.saveData(file);
      } catch (Exception exc) {
        exc.printStackTrace();
      }
    }
  }

  @FXML
  public void exit() {
    menuBar.getScene().getWindow().hide();
  }
}
