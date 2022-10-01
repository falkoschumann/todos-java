package de.muspellheim.todos.application;

import de.muspellheim.todos.domain.*;
import java.util.*;

class TodosServiceStub implements TodosService {
  private List<Todo> todos;

  void setTodos(List<Todo> todos) {
    this.todos = todos;
  }

  @Override
  public List<Todo> selectTodos() {
    return todos;
  }
}
