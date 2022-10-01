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

  public List<Todo> getTodos() {
    return todos;
  }

  public void setTodos(List<Todo> todos) {
    this.todos = todos;
  }

  public String getCounter() {
    return counter;
  }

  public void setCounter(String counter) {
    this.counter = counter;
  }

  public void createTodo(String title) {
    if (title.isBlank()) {
      return;
    }

    model.addTodo(title);
    update();
  }

  private void update() {
    var todos = model.selectTodos();
    setTodos(todos);
    long count = todos.stream().filter(t -> !t.completed()).count();
    setCounter(String.format("%1$d %2$s left", count, count == 1 ? "item" : "item" + 's'));
  }
}
