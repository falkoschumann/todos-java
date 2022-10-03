package de.muspellheim.todos.domain;

import java.util.*;

public interface Todos {
  List<Todo> load() throws TodosException;

  void store(List<Todo> todos) throws TodosException;
}
