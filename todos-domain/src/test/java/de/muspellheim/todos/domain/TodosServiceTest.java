package de.muspellheim.todos.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.*;

public class TodosServiceTest {
  private FakeTodosRepository repository;
  private TodosService service;

  @BeforeEach
  void setUp() {
    repository = new FakeTodosRepository();
    service = new TodosServiceImpl(repository);
  }

  @Test
  void addTodo_FirstTodo() throws Exception {
    var status = service.addTodo("foo");

    assertEquals(Result.ok(), status);
    assertEquals(List.of(new Todo(1, "foo", false)), repository.load());
  }

  @Test
  void addTodo_Failure() {
    repository.setException("Foobar.");

    var status = service.addTodo("foo");

    assertEquals(Result.fail("Add todo \"foo\" failed. Foobar."), status);
  }

  @Test
  void addTodo_SecondTodo() throws Exception {
    repository.store(List.of(new Todo(1, "foo", true)));

    var status = service.addTodo("bar");

    assertEquals(Result.ok(), status);
    assertEquals(List.of(new Todo(1, "foo", true), new Todo(2, "bar", false)), repository.load());
  }

  @Test
  void addTodo_TrimTitle() throws Exception {
    var status = service.addTodo("  foo ");

    assertEquals(Result.ok(), status);
    assertEquals(List.of(new Todo(1, "foo", false)), repository.load());
  }

  @Test
  void addTodo_DoNothingIfTitleIsBlank() throws Exception {
    var status = service.addTodo("   ");

    assertEquals(Result.ok(), status);
    assertEquals(List.of(), repository.load());
  }

  @Test
  void toggleTodo_SetCompleted() throws Exception {
    repository.store(List.of(new Todo(1, "foo", false)));

    var status = service.toggleTodo(1);

    assertEquals(Result.ok(), status);
    assertEquals(List.of(new Todo(1, "foo", true)), repository.load());
  }

  @Test
  void toggleTodo_SetActive() throws Exception {
    repository.store(List.of(new Todo(1, "foo", true)));

    var status = service.toggleTodo(1);

    assertEquals(Result.ok(), status);
    assertEquals(List.of(new Todo(1, "foo", false)), repository.load());
  }

  @Test
  void toggleTodo_Failure() {
    repository.setException("Foobar.");

    var status = service.toggleTodo(42);

    assertEquals(Result.fail("Toggle todo 42 failed. Foobar."), status);
  }

  @Test
  void toggleAll_SetAllCompleted() throws Exception {
    repository.store(List.of(new Todo(1, "foo", false), new Todo(2, "bar", true)));

    var status = service.toggleAll(true);

    assertEquals(Result.ok(), status);
    assertEquals(List.of(new Todo(1, "foo", true), new Todo(2, "bar", true)), repository.load());
  }

  @Test
  void toggleAll_SetAllActive() throws Exception {
    repository.store(List.of(new Todo(1, "foo", false), new Todo(2, "bar", true)));

    var status = service.toggleAll(false);

    assertEquals(Result.ok(), status);
    assertEquals(List.of(new Todo(1, "foo", false), new Todo(2, "bar", false)), repository.load());
  }

  @Test
  void toggleAll_Failure() {

    repository.setException("Foobar.");

    var status = service.toggleAll(true);

    assertEquals(Result.fail("Toggle all to true failed. Foobar."), status);
  }

  @Test
  void destroyTodo_RemoveFirstTodo() throws Exception {
    repository.store(List.of(new Todo(1, "foo", true), new Todo(2, "bar", false)));

    var status = service.destroyTodo(1);

    assertEquals(Result.ok(), status);
    assertEquals(List.of(new Todo(2, "bar", false)), repository.load());
  }

  @Test
  void destroyTodo_RemoveSecondTodo() throws Exception {
    repository.store(List.of(new Todo(1, "foo", true), new Todo(2, "bar", false)));

    var status = service.destroyTodo(2);

    assertEquals(Result.ok(), status);
    assertEquals(List.of(new Todo(1, "foo", true)), repository.load());
  }

  @Test
  void destroyTodo_Failure() {
    repository.setException("Foobar.");

    var status = service.destroyTodo(42);

    assertEquals(Result.fail("Destroy todo 42 failed. Foobar."), status);
  }

  @Test
  void clearCompleted_RemoveCompletedTodo() throws Exception {
    repository.store(List.of(new Todo(1, "foo", false), new Todo(2, "bar", true)));

    var status = service.clearCompleted();

    assertEquals(Result.ok(), status);
    assertEquals(List.of(new Todo(1, "foo", false)), repository.load());
  }

  @Test
  void clearCompleted_Failure() {
    repository.setException("Foobar.");

    var status = service.clearCompleted();

    assertEquals(Result.fail("Clear completed failed. Foobar."), status);
  }

  @Test
  void saveTodo_ChangeTodosTitle() throws Exception {
    repository.store(List.of(new Todo(1, "foo", false)));

    var status = service.saveTodo(1, "bar");

    assertEquals(Result.ok(), status);
    assertEquals(List.of(new Todo(1, "bar", false)), repository.load());
  }

  @Test
  void saveTodo_TrimTitle() throws Exception {
    repository.store(List.of(new Todo(1, "foo", false)));

    var status = service.saveTodo(1, "  bar ");

    assertEquals(Result.ok(), status);
    assertEquals(List.of(new Todo(1, "bar", false)), repository.load());
  }

  @Test
  void saveTodo_DeleteTodoIfTitleIsBlank() throws Exception {
    repository.store(List.of(new Todo(1, "foo", false)));

    var status = service.saveTodo(1, "   ");

    assertEquals(Result.ok(), status);
    assertEquals(List.of(), repository.load());
  }

  @Test
  void saveTodo_Failure() {
    repository.setException("Foobar.");

    var status = service.saveTodo(42, "bar");

    assertEquals(Result.fail("Save todo 42 with title \"bar\" failed. Foobar."), status);
  }

  @Test
  void selectTodos_ReturnStoredTodos() throws Exception {
    repository.store(List.of(new Todo(1, "foo", true), new Todo(2, "bar", false)));

    var result = service.selectTodos();

    assertEquals(List.of(new Todo(1, "foo", true), new Todo(2, "bar", false)), result);
  }

  @Test
  void selectTodos_Failure_ReturnEmptyList() {
    repository.setException("Foobar.");

    var result = service.selectTodos();

    assertEquals(result, List.of());
  }
}
