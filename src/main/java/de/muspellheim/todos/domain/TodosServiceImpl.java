package de.muspellheim.todos.domain;

import java.util.*;

public class TodosServiceImpl implements TodosService {
  private final Todos todos;

  public TodosServiceImpl(Todos todos) {
    this.todos = todos;
  }

  @Override
  public List<Todo> selectTodos() {
    return todos.load();
  }
}
