package de.muspellheim.todos.application;

import de.muspellheim.todos.domain.*;
import java.util.*;

public interface TodosView {
  void show();

  void setTodos(List<Todo> todos);

  void setCounter(String counter);

  void addNewTodoListener(NewTodoListener listener);

  interface NewTodoListener extends EventListener {
    void accept(NewTodoEvent event);
  }

  record NewTodoEvent(String title) {}
}
