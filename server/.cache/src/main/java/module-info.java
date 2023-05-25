module pt.ul.fc.di.css.javafxexample {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.web;

  opens pt.ul.fc.di.css.javafxexample to
      javafx.fxml,
      javafx.web;
  opens pt.ul.fc.di.css.javafxexample.presentation.control to
      javafx.fxml;

  exports pt.ul.fc.di.css.javafxexample;
}
