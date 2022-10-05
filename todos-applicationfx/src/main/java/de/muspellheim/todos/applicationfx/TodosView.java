package de.muspellheim.todos.applicationfx;

import de.muspellheim.todos.domain.*;
import java.io.*;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class TodosView {
  private final TodosViewModel viewModel = new TodosViewModel();

  @FXML private Stage stage;
  @FXML private TextField newTodo;
  @FXML private CheckBox toggleAll;
  @FXML private ListView<Todo> todoList;
  @FXML private Pane footer;
  @FXML private Text todoCount1;
  @FXML private Text todoCount2;
  @FXML private ChoiceBox<Filter> filters;
  @FXML private Button clearCompleted;

  public static TodosView create(Stage stage, TodosService model) {
    var viewPath = "/fxml/todos-view.fxml";
    try {
      var url = TodosView.class.getResource(viewPath);
      Objects.requireNonNull(url, "Resource not found: " + viewPath);
      var loader = new FXMLLoader(url);
      loader.setRoot(stage);
      loader.load();
      var controller = (TodosView) loader.getController();
      controller.viewModel.initModel(model);
      return controller;
    } catch (IOException e) {
      throw new RuntimeException("Could not load view from " + viewPath + ".", e);
    }
  }

  @FXML
  private void initialize() {
    newTodo.requestFocus();

    toggleAll.visibleProperty().bind(viewModel.hasTodos());
    viewModel
        .isAllCompleted()
        .addListener(
            (observable, oldValue, newValue) -> {
              toggleAll.setSelected(newValue);
            });
    todoList.setCellFactory(this::createTodoCell);
    todoList.setItems(viewModel.getTodos());
    todoList.visibleProperty().bind(viewModel.hasTodos());

    footer.visibleProperty().bind(viewModel.hasTodos());
    todoCount1.textProperty().bind(viewModel.getTodoCount1());
    todoCount2.textProperty().bind(viewModel.getTodoCount2());
    filters.getItems().setAll(Filter.values());
    filters.setValue(Filter.ALL);
    filters.valueProperty().addListener((observable, oldValue, newValue) -> handleFilter(newValue));
    clearCompleted.visibleProperty().bind(viewModel.hasCompletedTodos());
  }

  private TodoCell createTodoCell(ListView<Todo> todoListView) {
    return new TodoCell(viewModel::toggleTodo, viewModel::saveTodo, viewModel::destroyTodo);
  }

  public void run() {
    stage.show();
  }

  @FXML
  private void handleNewTodo() {
    viewModel.addTodo(newTodo.getText());
    newTodo.setText("");
  }

  @FXML
  private void handleToggleAll() {
    viewModel.toggleAll();
  }

  @FXML
  private void handleFilter(Filter filter) {
    viewModel.setFilter(filter);
  }

  @FXML
  private void handleClearCompleted() {
    viewModel.clearCompleted();
  }
}
