package de.muspellheim.todos.domain;

import java.util.*;

public class TodosServiceImpl implements TodosService {
  private final Todos todos;

  public TodosServiceImpl(Todos todos) {
    this.todos = todos;
  }

  @Override
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

  @Override
  public void toggleTodo(int id) {
    var todos = this.todos.load();
    todos = doToggle(todos, id);
    this.todos.store(todos);
  }

  private static List<Todo> doToggle(List<Todo> todos, int id) {
    return todos.stream()
        .map(t -> t.id() != id ? t : new Todo(t.id(), t.title(), !t.completed()))
        .toList();
  }

  @Override
  public void toggleAll(boolean checked) {
    var todos = this.todos.load();
    todos = doToggleAll(todos, checked);
    this.todos.store(todos);
  }

  private static List<Todo> doToggleAll(List<Todo> todos, boolean checked) {
    return todos.stream().map(t -> new Todo(t.id(), t.title(), checked)).toList();
  }

  @Override
  public void destroyTodo(int id) {
    var todos = this.todos.load();
    todos = doDestroy(todos, id);
    this.todos.store(todos);
  }

  private static List<Todo> doDestroy(List<Todo> todos, int id) {
    return todos.stream().filter(t -> t.id() != id).toList();
  }

  @Override
  public void clearCompleted() {
    var todos = this.todos.load();
    todos = doClearCompleted(todos);
    this.todos.store(todos);
  }

  private static List<Todo> doClearCompleted(List<Todo> todos) {
    return todos.stream().filter(t -> !t.completed()).toList();
  }

  @Override
  public void saveTodo(int id, String title) {
    var todos = this.todos.load();
    todos = doSave(todos, id, title);
    this.todos.store(todos);
  }

  private static List<Todo> doSave(List<Todo> todos, int id, String title) {
    var newTitle = title.trim();
    if (newTitle.isEmpty()) {
      return todos.stream().filter(t -> t.id() != id).toList();
    } else {
      return todos.stream()
          .map(t -> t.id() != id ? t : new Todo(t.id(), newTitle, t.completed()))
          .toList();
    }
  }

  @Override
  public List<Todo> selectTodos() {
    return todos.load();
  }
}
