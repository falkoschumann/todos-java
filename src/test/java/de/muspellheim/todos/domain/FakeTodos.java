package de.muspellheim.todos.domain;

import java.util.*;

public class FakeTodos implements Todos {
  private List<Todo> todos = List.of();

  @Override
  public List<Todo> load() {
    return todos;
  }

  @Override
  public void store(List<Todo> todos) {
    this.todos = todos;
  }
}
