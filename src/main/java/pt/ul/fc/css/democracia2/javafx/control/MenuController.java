package pt.ul.fc.css.democracia2.javafx.control;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import pt.ul.fc.css.democracia2.javafx.model.DataModel;

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
                // handle exception...
            }
        }
    }

    @FXML
    public void save() {

        // similar to load...

    }

    @FXML
    public void exit() {
        menuBar.getScene().getWindow().hide();
    }
}
