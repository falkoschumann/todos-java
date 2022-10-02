package de.muspellheim.todos.application;

import de.muspellheim.todos.domain.*;
import java.util.*;

class TodosViewModel {
  private final TodosService model;
  private Filter filter = Filter.ALL;
  private boolean hasTodos = false;
  private List<Todo> todos;
  private String counter;

  TodosViewModel(TodosService model) {
    this.model = model;
    update();
  }

  boolean hasTodos() {
    return hasTodos;
  }

  List<Todo> getTodos() {
    return todos;
  }

  String getCounter() {
    return counter;
  }

  void addTodo(String title) {
    if (title.isBlank()) {
      return;
    }

    model.addTodo(title);
    update();
  }

  void toggleTodo(int id) {
    model.toggle(id);
    update();
  }

  void destroyTodo(int id) {
    model.destroy(id);
    update();
  }

  void setFilter(Filter filter) {
    this.filter = filter;
    update();
  }

  private void update() {
    todos = model.selectTodos();
    hasTodos = !todos.isEmpty();
    var count = todos.stream().filter(t -> !t.completed()).count();
    counter = String.format("%1$d %2$s left", count, count == 1 ? "item" : "item" + 's');
    todos = todos.stream().filter(this::filter).toList();
  }

  private boolean filter(Todo todo) {
    return switch (filter) {
      case ALL -> true;
      case ACTIVE -> !todo.completed();
      case COMPLETED -> todo.completed();
    };
  }
}
