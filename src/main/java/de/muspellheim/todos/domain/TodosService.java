package de.muspellheim.todos.domain;

import java.util.*;

public interface TodosService {
  void addTodo(String title);

  void toggleTodo(int id);

  void destroyTodo(int id);

  void clearCompleted();

  List<Todo> selectTodos();
}
