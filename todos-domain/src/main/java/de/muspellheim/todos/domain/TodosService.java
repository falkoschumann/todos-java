package de.muspellheim.todos.domain;

import java.util.*;

public interface TodosService {
  Result addTodo(String title);

  Result toggleTodo(int id);

  Result toggleAll(boolean checked);

  Result destroyTodo(int id);

  Result clearCompleted();

  Result saveTodo(int id, String title);

  List<Todo> selectTodos();
}
