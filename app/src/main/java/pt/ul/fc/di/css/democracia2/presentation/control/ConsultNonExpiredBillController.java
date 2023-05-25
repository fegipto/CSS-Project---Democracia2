package pt.ul.fc.di.css.democracia2.presentation.control;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import pt.ul.fc.di.css.democracia2.presentation.model.Bill;
import pt.ul.fc.di.css.democracia2.presentation.model.DataModel;

public class ConsultNonExpiredBillController {
  @FXML private ListView<Bill> listView;
  private DataModel model;

  public void initModel(DataModel model) {
    if (this.model != null) {
      throw new IllegalStateException("Model can only be initialized once");
    }

    this.model = model;
    listView.setItems(model.getNonExpiredBillList());

    listView
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              model.setCurrentNonExpiredBill(newSelection);
            });
  }
}
