package pt.ul.fc.di.css.democracia2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pt.ul.fc.di.css.democracia2.presentation.control.ListAvailableVotesController;
import pt.ul.fc.di.css.democracia2.presentation.control.MenuController;
import pt.ul.fc.di.css.democracia2.presentation.control.VotingController;
import pt.ul.fc.di.css.democracia2.presentation.model.DataModel;

public class JavaFXApp extends Application {

  private static final String prefix = "/pt/ul/fc/di/css/democracia2/presentation/view/";
  private static Stage primaryStage;
  private static BorderPane root;

  @Override
  public void start(Stage primaryStage) throws Exception {
    this.primaryStage = primaryStage;

    this.root = new BorderPane();
    this.listAvailableVotes();
  }

  public static void listAvailableVotes() throws Exception {
    FXMLLoader listAvailableVotesLoader =
        new FXMLLoader(JavaFXApp.class.getResource(prefix + "listAvailableVotes.fxml"));
    root.setCenter(listAvailableVotesLoader.load());
    ListAvailableVotesController listAvailableVotesController =
        listAvailableVotesLoader.getController();

    FXMLLoader votingLoader = new FXMLLoader(JavaFXApp.class.getResource(prefix + "voting.fxml"));
    root.setRight(votingLoader.load());
    VotingController votingController = votingLoader.getController();

    FXMLLoader menuLoader = new FXMLLoader(JavaFXApp.class.getResource(prefix + "menu.fxml"));
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

  public static void consultNonExpiredBills() throws Exception {
    FXMLLoader consultNonExpiredBillLoader =
        new FXMLLoader(JavaFXApp.class.getResource(prefix + "consultNonExpiredBill.fxml"));
    root.setCenter(consultNonExpiredBillLoader.load());
    ListAvailableVotesController listAvailableVotesController =
        consultNonExpiredBillLoader.getController();

    FXMLLoader supportBillLoader =
        new FXMLLoader(JavaFXApp.class.getResource(prefix + "supportBill.fxml"));
    root.setRight(supportBillLoader.load());
    VotingController votingController = supportBillLoader.getController();

    FXMLLoader menuLoader = new FXMLLoader(JavaFXApp.class.getResource(prefix + "menu.fxml"));
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

  public static void main(String[] args) {
    launch(args);
  }
}
