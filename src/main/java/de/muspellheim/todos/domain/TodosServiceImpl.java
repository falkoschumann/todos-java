package de.muspellheim.todos.domain;

import java.util.*;
import java.util.logging.*;

public class TodosServiceImpl implements TodosService {
  private final Logger logger = Logger.getLogger(getClass().getName());
  private final Todos todos;

  public TodosServiceImpl(Todos todos) {
    this.todos = todos;
  }

  @Override
  public CommandStatus addTodo(String title) {
    try {
      var todos = this.todos.load();
      todos = doAddTodo(todos, title);
      this.todos.store(todos);
      return new Success();
    } catch (TodosException e) {
      String errorMessage = "Add todo \"" + title + "\" failed.";
      logger.log(Level.WARNING, errorMessage, e);
      return new Failure(errorMessage, e);
    }
  }

  private static List<Todo> doAddTodo(List<Todo> todos, String title) {
    var lastId = todos.stream().mapToInt(Todo::id).max().orElse(0);
    todos = new ArrayList<>(todos);
    todos.add(new Todo(lastId + 1, title.trim(), false));
    return todos;
  }

  @Override
  public CommandStatus toggleTodo(int id) {
    try {
      var todos = this.todos.load();
      todos = doToggle(todos, id);
      this.todos.store(todos);
      return new Success();
    } catch (TodosException e) {
      String errorMessage = "Toggle todo " + id + " failed.";
      logger.log(Level.WARNING, errorMessage, e);
      return new Failure(errorMessage, e);
    }
  }

  private static List<Todo> doToggle(List<Todo> todos, int id) {
    return todos.stream()
        .map(t -> t.id() != id ? t : new Todo(t.id(), t.title(), !t.completed()))
        .toList();
  }

  @Override
  public CommandStatus toggleAll(boolean checked) {
    try {
      var todos = this.todos.load();
      todos = doToggleAll(todos, checked);
      this.todos.store(todos);
      return new Success();
    } catch (TodosException e) {
      String errorMessage = "Toggle all to " + checked + " failed.";
      logger.log(Level.WARNING, errorMessage, e);
      return new Failure(errorMessage, e);
    }
  }

  private static List<Todo> doToggleAll(List<Todo> todos, boolean checked) {
    return todos.stream().map(t -> new Todo(t.id(), t.title(), checked)).toList();
  }

  @Override
  public CommandStatus destroyTodo(int id) {
    try {
      var todos = this.todos.load();
      todos = doDestroy(todos, id);
      this.todos.store(todos);
      return new Success();
    } catch (TodosException e) {
      String errorMessage = "Destroy todo " + id + " failed.";
      logger.log(Level.WARNING, errorMessage, e);
      return new Failure(errorMessage, e);
    }
  }

  private static List<Todo> doDestroy(List<Todo> todos, int id) {
    return todos.stream().filter(t -> t.id() != id).toList();
  }

  @Override
  public CommandStatus clearCompleted() {
    try {
      var todos = this.todos.load();
      todos = doClearCompleted(todos);
      this.todos.store(todos);
      return new Success();
    } catch (TodosException e) {
      String errorMessage = "Clear completed failed.";
      logger.log(Level.WARNING, errorMessage, e);
      return new Failure(errorMessage, e);
    }
  }

  private static List<Todo> doClearCompleted(List<Todo> todos) {
    return todos.stream().filter(t -> !t.completed()).toList();
  }

  @Override
  public CommandStatus saveTodo(int id, String title) {
    try {
      var todos = this.todos.load();
      todos = doSave(todos, id, title);
      this.todos.store(todos);
      return new Success();
    } catch (TodosException e) {
      String errorMessage = "Save todo " + id + " with title \"" + title + "\" failed.";
      logger.log(Level.WARNING, errorMessage, e);
      return new Failure(errorMessage, e);
    }
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
    try {
      return todos.load();
    } catch (TodosException e) {
      logger.log(Level.WARNING, "Select todos failed.", e);
      return List.of();
    }
  }
}
