package de.muspellheim.todos.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.*;

public class TodosServiceTest {
  private Todos repository;
  private TodosService service;

  @BeforeEach
  void setUp() {
    repository = new FakeTodos();
    service = new TodosServiceImpl(repository);
  }

  @Test
  void addTodo_FirstTodo() {
    service.addTodo("foo");

    assertEquals(List.of(new Todo(1, "foo", false)), repository.load());
  }

  @Test
  void addTodo_SecondTodo() {
    repository.store(List.of(new Todo(1, "foo", true)));

    service.addTodo("bar");

    assertEquals(List.of(new Todo(1, "foo", true), new Todo(2, "bar", false)), repository.load());
  }

  @Test
  void toggleTodo_SetCompleted() {
    repository.store(List.of(new Todo(1, "foo", false)));

    service.toggleTodo(1);

    assertEquals(List.of(new Todo(1, "foo", true)), repository.load());
  }

  @Test
  void toggleTodo_SetActive() {
    repository.store(List.of(new Todo(1, "foo", true)));

    service.toggleTodo(1);

    assertEquals(List.of(new Todo(1, "foo", false)), repository.load());
  }

  @Test
  void toggleAll_SetAllCompleted() {
    repository.store(List.of(new Todo(1, "foo", false), new Todo(2, "bar", true)));

    service.toggleAll(true);

    assertEquals(List.of(new Todo(1, "foo", true), new Todo(2, "bar", true)), repository.load());
  }

  @Test
  void toggleAll_SetAllActive() {
    repository.store(List.of(new Todo(1, "foo", false), new Todo(2, "bar", true)));

    service.toggleAll(false);

    assertEquals(List.of(new Todo(1, "foo", false), new Todo(2, "bar", false)), repository.load());
  }

  @Test
  void destroyTodo_RemoveTodo() {
    repository.store(List.of(new Todo(1, "foo", true)));

    service.destroyTodo(1);

    assertEquals(List.of(), repository.load());
  }

  @Test
  void clearCompleted_RemoveCompletedTodo() {
    repository.store(List.of(new Todo(1, "foo", false), new Todo(2, "bar", true)));

    service.clearCompleted();

    assertEquals(List.of(new Todo(1, "foo", false)), repository.load());
  }

  @Test
  void selectTodos_ReturnStoredTodos() {
    repository.store(List.of(new Todo(1, "foo", true), new Todo(2, "bar", false)));

    var result = service.selectTodos();

    assertEquals(List.of(new Todo(1, "foo", true), new Todo(2, "bar", false)), result);
  }
}
