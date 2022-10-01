package de.muspellheim.todos.application;

import de.muspellheim.todos.domain.*;
import java.util.*;

public interface TodosView {
  void show();

  void setTodos(List<Todo> todos);

  void setCounter(String counter);
}
