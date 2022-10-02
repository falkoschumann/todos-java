package de.muspellheim.todos.application;

import de.muspellheim.todos.domain.*;
import java.util.*;

class TodosViewModel {
  private final TodosService model;
  private List<Todo> todos;
  private String counter;

  TodosViewModel(TodosService model) {
    this.model = model;
    update();
  }

  boolean hasTodos() {
    return !todos.isEmpty();
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

  private void update() {
    todos = model.selectTodos();
    long count = todos.stream().filter(t -> !t.completed()).count();
    counter = String.format("%1$d %2$s left", count, count == 1 ? "item" : "item" + 's');
  }
}
