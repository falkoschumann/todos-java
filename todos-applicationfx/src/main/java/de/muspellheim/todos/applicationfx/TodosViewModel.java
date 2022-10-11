package de.muspellheim.todos.applicationfx;

import de.muspellheim.todos.domain.*;
import javafx.beans.property.*;
import javafx.collections.*;

class TodosViewModel {
  // TODO handle failure

  private final ReadOnlyBooleanWrapper hasTodos = new ReadOnlyBooleanWrapper(false);
  private final ReadOnlyBooleanWrapper allCompleted = new ReadOnlyBooleanWrapper(false);
  private final ReadOnlyStringWrapper todoCount1 = new ReadOnlyStringWrapper("0");
  private final ReadOnlyStringWrapper todoCount2 = new ReadOnlyStringWrapper(" items left");
  private final ObservableList<Todo> todos = FXCollections.observableArrayList();
  private final ReadOnlyBooleanWrapper hasCompletedTodos = new ReadOnlyBooleanWrapper(false);
  private Filter filter = Filter.ALL;
  private TodosService model;

  TodosViewModel() {
    this(null);
  }

  public TodosViewModel(TodosService model) {
    initModel(model);
  }

  void initModel(TodosService model) {
    this.model = model;
    if (model != null) {
      update();
    }
  }

  public ReadOnlyBooleanProperty hasTodos() {
    return hasTodos.getReadOnlyProperty();
  }

  public ReadOnlyBooleanProperty isAllCompleted() {
    return allCompleted.getReadOnlyProperty();
  }

  public ObservableList<Todo> getTodos() {
    return todos;
  }

  public ReadOnlyStringProperty getTodoCount1() {
    return todoCount1.getReadOnlyProperty();
  }

  public ReadOnlyStringProperty getTodoCount2() {
    return todoCount2.getReadOnlyProperty();
  }

  public ReadOnlyBooleanProperty hasCompletedTodos() {
    return hasCompletedTodos.getReadOnlyProperty();
  }

  public void addTodo(String title) {
    if (title.isBlank()) {
      return;
    }

    model.addTodo(title);
    update();
  }

  void toggleAll() {
    model.toggleAll(!isAllCompleted().get());
    update();
  }

  void toggleTodo(int id) {
    model.toggleTodo(id);
    update();
  }

  void destroyTodo(int id) {
    model.destroyTodo(id);
    update();
  }

  void setFilter(Filter filter) {
    this.filter = filter;
    update();
  }

  void clearCompleted() {
    model.clearCompleted();
    update();
  }

  void saveTodo(int id, String title) {
    model.saveTodo(id, title);
    update();
  }

  private void update() {
    var allTodos = model.selectTodos();
    todos.setAll(allTodos.stream().filter(this::filter).toList());
    hasTodos.set(!allTodos.isEmpty());
    var activeCount = allTodos.stream().filter(t -> !t.completed()).count();
    todoCount1.set(String.format("%1$d", activeCount));
    todoCount2.set(String.format(" %1$s left", activeCount == 1 ? "item" : "item" + 's'));
    hasCompletedTodos.set(allTodos.size() - activeCount > 0);
    allCompleted.set(!allTodos.isEmpty() && activeCount == 0);
  }

  private boolean filter(Todo todo) {
    return switch (filter) {
      case ALL -> true;
      case ACTIVE -> !todo.completed();
      case COMPLETED -> todo.completed();
    };
  }
}
