package pt.ul.fc.di.css.democracia2.presentation.control;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import pt.ul.fc.di.css.democracia2.presentation.model.Bill;
import pt.ul.fc.di.css.democracia2.presentation.model.DataModel;

public class NExpListController {
  @FXML private ListView<Bill> listNExpBills;

  private DataModel model;

  @FXML MenuController menuController;

  @FXML SupportingController supportingController;

  @FXML NExpDetailsController nExpDetailsController;

  public void initModel(DataModel model) {

    menuController.initModel(model);
    supportingController.initModel(model);
    nExpDetailsController.initModel(model);

    if (this.model != null) {
      throw new IllegalStateException("Model can only be initialized once");
    }

    this.model = model;
    listNExpBills.setItems(model.getNonExpiredBillList());

    listNExpBills
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              model.setCurrentNonExpiredBill(newSelection);
            });
  }
}
