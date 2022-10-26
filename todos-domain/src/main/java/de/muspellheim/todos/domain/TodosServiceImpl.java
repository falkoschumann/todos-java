package de.muspellheim.todos.domain;

import java.util.*;
import java.util.function.*;
import java.util.logging.*;

public class TodosServiceImpl implements TodosService {
  private final Logger logger = Logger.getLogger(getClass().getName());
  private final Todos todos;

  public TodosServiceImpl(Todos todos) {
    this.todos = todos;
  }

  @Override
  public Result addTodo(String title) {
    return validateTitle(title, this::doAddTodo, this::doNothing);
  }

  private Result doAddTodo(String title) {
    try {
      var todos = this.todos.load();
      todos = doAddTodo(todos, title);
      this.todos.store(todos);
      return Result.ok();
    } catch (TodosException e) {
      return handleError("Add todo \"" + title + "\" failed.", e);
    }
  }

  private Result doNothing() {
    return Result.ok();
  }

  private Result handleError(String errorMessage, TodosException e) {
    logger.log(Level.WARNING, errorMessage, e);
    String summarize = Exceptions.summarize(errorMessage, e);
    return Result.fail(summarize);
  }

  private static List<Todo> doAddTodo(List<Todo> todos, String title) {
    var lastId = todos.stream().mapToInt(Todo::id).max().orElse(0);
    todos = new ArrayList<>(todos);
    todos.add(new Todo(lastId + 1, title, false));
    return todos;
  }

  @Override
  public Result toggleTodo(int id) {
    try {
      var todos = this.todos.load();
      todos = doToggle(todos, id);
      this.todos.store(todos);
      return Result.ok();
    } catch (TodosException e) {
      return handleError("Toggle todo " + id + " failed.", e);
    }
  }

  private static List<Todo> doToggle(List<Todo> todos, int id) {
    return todos.stream()
        .map(t -> t.id() != id ? t : new Todo(t.id(), t.title(), !t.completed()))
        .toList();
  }

  @Override
  public Result toggleAll(boolean checked) {
    try {
      var todos = this.todos.load();
      todos = doToggleAll(todos, checked);
      this.todos.store(todos);
      return Result.ok();
    } catch (TodosException e) {
      return handleError("Toggle all to " + checked + " failed.", e);
    }
  }

  private static List<Todo> doToggleAll(List<Todo> todos, boolean checked) {
    return todos.stream().map(t -> new Todo(t.id(), t.title(), checked)).toList();
  }

  @Override
  public Result destroyTodo(int id) {
    try {
      var todos = this.todos.load();
      todos = doDestroy(todos, id);
      this.todos.store(todos);
      return Result.ok();
    } catch (TodosException e) {
      return handleError("Destroy todo " + id + " failed.", e);
    }
  }

  private static List<Todo> doDestroy(List<Todo> todos, int id) {
    return todos.stream().filter(t -> t.id() != id).toList();
  }

  @Override
  public Result clearCompleted() {
    try {
      var todos = this.todos.load();
      todos = doClearCompleted(todos);
      this.todos.store(todos);
      return Result.ok();
    } catch (TodosException e) {
      return handleError("Clear completed failed.", e);
    }
  }

  private static List<Todo> doClearCompleted(List<Todo> todos) {
    return todos.stream().filter(t -> !t.completed()).toList();
  }

  @Override
  public Result saveTodo(int id, String title) {
    return validateTitle(title, t -> doSaveTodo(id, t), () -> destroyTodo(id));
  }

  private Result doSaveTodo(int id, String title) {
    try {
      var todos = this.todos.load();
      todos = doSaveTodo(todos, id, title);
      this.todos.store(todos);
      return Result.ok();
    } catch (TodosException e) {
      return handleError("Save todo " + id + " with title \"" + title + "\" failed.", e);
    }
  }

  private static List<Todo> doSaveTodo(List<Todo> todos, int id, String title) {
    return todos.stream()
        .map(t -> t.id() != id ? t : new Todo(t.id(), title, t.completed()))
        .toList();
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

  <T> T validateTitle(String title, Function<String, T> onValid, Supplier<T> onInvalid) {
    if (title.isBlank()) {
      return onInvalid.get();
    } else {
      return onValid.apply(title.trim());
    }
  }
}
