package de.muspellheim.todos.applicationfx;

import de.muspellheim.todos.domain.*;
import de.muspellheim.todos.infrastructure.*;
import java.nio.file.*;
import javafx.application.*;
import javafx.stage.*;

public class App extends Application {
  private TodosService model;

  @Override
  public void init() {
    Path file = Paths.get(System.getProperty("user.home"), "todos.json");
    var todos = new JsonTodos(file);
    model = new TodosServiceImpl(todos);
  }

  @Override
  public void start(Stage primaryStage) {
    var view = TodosView.create(primaryStage, model);
    view.run();
  }
}
