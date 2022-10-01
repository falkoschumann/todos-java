package de.muspellheim.todos.application;

import de.muspellheim.todos.domain.*;
import java.util.*;

public class TodosServiceStub implements TodosService {
  @Override
  public List<Todo> selectTodos() {
    return List.of(new Todo(1, "Taste JavaScript", true), new Todo(2, "Buy Unicorn", false));
  }
}
