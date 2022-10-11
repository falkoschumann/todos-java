package de.muspellheim.todos.infrastructure;

import de.muspellheim.todos.domain.*;
import java.util.*;

public class MemoryTodosRepository implements Todos {
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
