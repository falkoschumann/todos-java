package de.muspellheim.todos.application;

import de.muspellheim.todos.domain.*;
import java.util.*;

public class TodosController {
  private final TodosService model;
  private final TodosView view;

  public TodosController(TodosService model, TodosView view) {
    this.model = model;
    this.view = view;
  }

  public void run() {
    view.show();
    load();
  }

  private void load() {
    var todos = model.selectTodos();
    updateView(todos);
  }

  private void updateView(List<Todo> todos) {
    view.setTodos(todos);
    long count = todos.stream().filter(t -> !t.completed()).count();
    view.setCounter(String.format("%1$d %2$s left", count, count == 1 ? "item" : "item" + 's'));
  }
}
