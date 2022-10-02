package de.muspellheim.todos.domain;

import java.util.*;

public class TodosService {
  private final Todos todos;

  public TodosService(Todos todos) {
    this.todos = todos;
  }

  public void addTodo(String title) {
    var todos = this.todos.load();
    todos = doAddTodo(todos, title);
    this.todos.store(todos);
  }

  private static List<Todo> doAddTodo(List<Todo> todos, String title) {
    var lastId = todos.stream().mapToInt(Todo::id).max().orElse(0);
    todos = new ArrayList<>(todos);
    todos.add(new Todo(lastId + 1, title.trim(), false));
    return todos;
  }

  public List<Todo> selectTodos() {
    return todos.load();
  }

  public void toggle(int id) {
    var todos = this.todos.load();
    todos = doToggle(todos, id);
    this.todos.store(todos);
  }

  private static List<Todo> doToggle(List<Todo> todos, int id) {
    return todos.stream()
        .map(t -> t.id() != id ? t : new Todo(t.id(), t.title(), !t.completed()))
        .toList();
  }

  public void destroy(int id) {
    var todos = this.todos.load();
    todos = doDestroy(todos, id);
    this.todos.store(todos);
  }

  private static List<Todo> doDestroy(List<Todo> todos, int id) {
    return todos.stream().filter(t -> t.id() != id).toList();
  }
}
