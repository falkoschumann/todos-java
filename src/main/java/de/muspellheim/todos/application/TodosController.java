package de.muspellheim.todos.application;

import de.muspellheim.todos.domain.*;

public class TodosController {
  private final TodosService model;
  private final TodosView view;

  public TodosController(TodosService model, TodosView view) {
    this.model = model;
    this.view = view;
  }

  public void run() {
    view.show();
    var todos = model.selectTodos();
    view.setTodos(todos);
  }
}
