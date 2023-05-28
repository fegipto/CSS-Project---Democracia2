package pt.ul.fc.di.css.democracia2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pt.ul.fc.di.css.democracia2.presentation.control.ListController;
import pt.ul.fc.di.css.democracia2.presentation.model.DataModel;

/**
 * Class that launches the frontend app
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
public class JavaFXApp extends Application {

  //fxml files location
  private static final String prefix = "/pt/ul/fc/di/css/democracia2/presentation/view/";
  private BorderPane root;

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader listLoader = new FXMLLoader(getClass().getResource(prefix + "dashboard.fxml"));
    Parent root = listLoader.load();

    ListController listController = listLoader.getController();

    DataModel model = new DataModel();
    listController.initModel(model);
    Scene scene = new Scene(root, 1000, 600);

    primaryStage.setTitle("Democracia 2");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
