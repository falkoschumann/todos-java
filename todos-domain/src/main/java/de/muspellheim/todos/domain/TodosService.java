package de.muspellheim.todos.domain;

import java.util.*;

public interface TodosService {
  CommandStatus addTodo(String title);

  CommandStatus toggleTodo(int id);

  CommandStatus toggleAll(boolean checked);

  CommandStatus destroyTodo(int id);

  CommandStatus clearCompleted();

  CommandStatus saveTodo(int id, String title);

  List<Todo> selectTodos();
}
