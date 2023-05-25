module pt.ul.fc.di.css.democracia2 {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.web;
  requires spring.web;

  opens pt.ul.fc.di.css.democracia2 to
      javafx.fxml,
      javafx.web;
  opens pt.ul.fc.di.css.democracia2.presentation.control to
      javafx.fxml;

  exports pt.ul.fc.di.css.democracia2.DTO;
  exports pt.ul.fc.di.css.democracia2;
}
