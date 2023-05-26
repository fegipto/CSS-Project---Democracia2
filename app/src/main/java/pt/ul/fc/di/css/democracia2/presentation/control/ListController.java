package pt.ul.fc.di.css.democracia2.presentation.control;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import pt.ul.fc.di.css.democracia2.presentation.model.Bill;
import pt.ul.fc.di.css.democracia2.presentation.model.DataModel;

public class ListController {
  @FXML private ListView<Bill> listBills;

  private DataModel model;

  @FXML MenuController menuController;

  @FXML VotingController votingController;

  @FXML DetailsController detailsController;

  public void initModel(DataModel model) {

    menuController.initModel(model);
    votingController.initModel(model);
    detailsController.initModel(model);

    if (this.model != null) {
      throw new IllegalStateException("Model can only be initialized once");
    }

    this.model = model;
    listBills.setItems(model.getBills());

    listBills
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              model.setCurrentBill(newSelection);
            });
  }
}
