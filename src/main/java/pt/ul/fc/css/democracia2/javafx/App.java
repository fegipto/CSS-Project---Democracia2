package pt.ul.fc.css.democracia2.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pt.ul.fc.css.democracia2.javafx.control.ListAvailableVotesController;
import pt.ul.fc.css.democracia2.javafx.control.MenuController;
import pt.ul.fc.css.democracia2.javafx.model.DataModel;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        String prefix = "/pt/ul/fc/css/democracia2/javafx/view/";

        BorderPane root = new BorderPane();
        FXMLLoader listAvailableVotesLoader = new FXMLLoader(getClass().getResource(prefix + "listAvailableVotesController.fxml"));
        root.setCenter(listAvailableVotesLoader.load());
        ListAvailableVotesController listAvailableVotesController = listAvailableVotesLoader.getController();

        FXMLLoader editorLoader = new FXMLLoader(getClass().getResource(prefix + "editor.fxml"));
        root.setRight(editorLoader.load());
        //EditorController editorController = editorLoader.getController();

        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource(prefix + "menu.fxml"));
        root.setTop(menuLoader.load());
        MenuController menuController = menuLoader.getController();

        //DataModel model = new DataModel();
        //listAvailableVotesController.initModel(model);
        //editorController.initModel(model);
        //menuController.initModel(model);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }

}
