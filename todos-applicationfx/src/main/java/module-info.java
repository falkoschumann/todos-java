module de.muspellheim.todos.applicationfx {
  requires de.muspellheim.todos.domain;
  requires de.muspellheim.todos.infrastructure;
  requires jdk.localedata;
  requires javafx.controls;
  requires javafx.fxml;

  exports de.muspellheim.todos.applicationfx to
      javafx.graphics,
      javafx.fxml;

  opens de.muspellheim.todos.applicationfx to
      javafx.fxml;
}
