package pt.ul.fc.css.democracia2.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pt.ul.fc.css.democracia2.javafx.presentation.control.ListAvailableVotesController;
import pt.ul.fc.css.democracia2.javafx.presentation.control.MenuController;
import pt.ul.fc.css.democracia2.javafx.presentation.control.VotingController;
import pt.ul.fc.css.democracia2.javafx.presentation.model.DataModel;

public class JavaFXApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        String prefix = "/javafx/view/";

        BorderPane root = new BorderPane();
        FXMLLoader listAvailableVotesLoader = new FXMLLoader(getClass().getResource(prefix + "listAvailableVotes.fxml"));
        root.setCenter(listAvailableVotesLoader.load());
        ListAvailableVotesController listAvailableVotesController = listAvailableVotesLoader.getController();

        FXMLLoader votingLoader = new FXMLLoader(getClass().getResource(prefix + "voting.fxml"));
        root.setRight(votingLoader.load());
        VotingController votingController = votingLoader.getController();

        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource(prefix + "menu.fxml"));
        root.setTop(menuLoader.load());
        MenuController menuController = menuLoader.getController();

        DataModel model = new DataModel();
        listAvailableVotesController.initModel(model);
        votingController.initModel(model);
        menuController.initModel(model);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }

}
