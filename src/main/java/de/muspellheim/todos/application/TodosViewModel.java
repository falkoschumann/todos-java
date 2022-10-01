package de.muspellheim.todos.application;

import de.muspellheim.todos.domain.*;
import java.util.*;

public class TodosViewModel {
  private final TodosService model;
  private List<Todo> todos;
  private String counter;

  public TodosViewModel(TodosService model) {
    this.model = model;
    update();
  }

  public boolean hasTodos() {
    return !todos.isEmpty();
  }

  public List<Todo> getTodos() {
    return todos;
  }

  public String getCounter() {
    return counter;
  }

  public void createTodo(String title) {
    if (title.isBlank()) {
      return;
    }

    model.addTodo(title);
    update();
  }

  private void update() {
    todos = model.selectTodos();
    long count = todos.stream().filter(t -> !t.completed()).count();
    counter = String.format("%1$d %2$s left", count, count == 1 ? "item" : "item" + 's');
  }
}
