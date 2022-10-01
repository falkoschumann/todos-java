package de.muspellheim.todos.domain;

import java.util.*;

public interface Todos {
  List<Todo> load();

  void store(List<Todo> todos);
}
