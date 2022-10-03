package de.muspellheim.todos.domain;

import java.util.*;

public class FakeTodos implements Todos {
  private List<Todo> todos = List.of();
  private String message;

  void setException(String message) {
    this.message = message;
  }

  @Override
  public List<Todo> load() throws TodosException {
    if (message != null) {
      throw new TodosException(message);
    }

    return todos;
  }

  @Override
  public void store(List<Todo> todos) throws TodosException {
    if (message != null) {
      throw new TodosException(message);
    }

    this.todos = todos;
  }
}
