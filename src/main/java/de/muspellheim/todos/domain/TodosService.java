package de.muspellheim.todos.domain;

import java.util.*;

public class TodosService {
  private final Todos todos;

  public TodosService(Todos todos) {
    this.todos = todos;
  }

  public void addTodo(String title) {
    var todos = this.todos.load();
    todos = doAddTodos(todos, title);
    this.todos.store(todos);
  }

  private static List<Todo> doAddTodos(List<Todo> todos, String title) {
    var lastId = todos.stream().mapToInt(Todo::id).max().orElse(0);
    todos = new ArrayList<>(todos);
    todos.add(new Todo(lastId + 1, title, false));
    return todos;
  }

  public List<Todo> selectTodos() {
    return todos.load();
  }
}
