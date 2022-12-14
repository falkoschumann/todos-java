package de.muspellheim.todos.application;

import de.muspellheim.todos.domain.*;
import java.util.*;

class TodosViewModel {
  // TODO handle failure

  private final TodosService model;
  private Filter filter = Filter.ALL;
  private boolean hasTodos = false;
  private boolean allCompleted = false;
  private List<Todo> todos;
  private String todoCount;
  private boolean hasCompletedTodos;

  TodosViewModel(TodosService model) {
    this.model = model;
    update();
  }

  boolean hasTodos() {
    return hasTodos;
  }

  boolean isAllCompleted() {
    return allCompleted;
  }

  List<Todo> getTodos() {
    return todos;
  }

  String getTodoCount() {
    return todoCount;
  }

  boolean hasCompletedTodos() {
    return hasCompletedTodos;
  }

  void addTodo(String title) {
    if (title.isBlank()) {
      return;
    }

    model.addTodo(title);
    update();
  }

  void toggleAll() {
    model.toggleAll(!isAllCompleted());
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
    todos = allTodos.stream().filter(this::filter).toList();
    hasTodos = !allTodos.isEmpty();
    var activeCount = allTodos.stream().filter(t -> !t.completed()).count();
    todoCount =
        String.format(
            "<html><strong>%1$d</strong> %2$s left</html>",
            activeCount, activeCount == 1 ? "item" : "item" + 's');
    hasCompletedTodos = allTodos.size() - activeCount > 0;
    allCompleted = !allTodos.isEmpty() && activeCount == 0;
  }

  private boolean filter(Todo todo) {
    return switch (filter) {
      case ALL -> true;
      case ACTIVE -> !todo.completed();
      case COMPLETED -> todo.completed();
    };
  }
}
