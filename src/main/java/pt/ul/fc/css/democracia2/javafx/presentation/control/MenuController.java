package pt.ul.fc.css.democracia2.javafx.presentation.control;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import pt.ul.fc.css.democracia2.javafx.presentation.model.DataModel;

import java.io.File;

public class MenuController {

    @FXML
    private MenuBar menuBar;
    private DataModel model;

    public void initModel(DataModel model) {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;
    }

    @FXML
    public void load() {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(menuBar.getScene().getWindow());
        if (file != null) {
            try {
                model.loadData(file);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
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
