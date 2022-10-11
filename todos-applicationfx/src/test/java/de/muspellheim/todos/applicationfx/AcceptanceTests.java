package de.muspellheim.todos.applicationfx;

import static org.junit.jupiter.api.Assertions.*;

import de.muspellheim.todos.domain.*;
import de.muspellheim.todos.infrastructure.*;
import java.util.*;
import org.junit.jupiter.api.*;

public class AcceptanceTests {
  private TodosViewModel sut;

  @BeforeEach
  void setUp() {
    var repository = new MemoryTodosRepository();
    var model = new TodosServiceImpl(repository);
    sut = new TodosViewModel(model);
  }

  @Test
  void initialEmpty() {
    assertAll(
        () -> assertFalse(sut.hasTodos().get(), "has todos"),
        () -> assertFalse(sut.isAllCompleted().get(), "is all complete"),
        () -> assertEquals(List.of(), sut.getTodos(), "todos"),
        () -> assertEquals("0", sut.getTodoCount1().get(), "todo count 1"),
        () -> assertEquals(" items left", sut.getTodoCount2().get(), "todo count 2"),
        () -> assertFalse(sut.hasCompletedTodos().get(), "has completed todos"));
  }

  @Test
  void addFirstTodo() {
    sut.addTodo("Taste JavaScript");

    assertAll(
        () -> assertTrue(sut.hasTodos().get(), "has todos"),
        () -> assertFalse(sut.isAllCompleted().get(), "is all complete"),
        () ->
            assertEquals(List.of(new Todo(1, "Taste JavaScript", false)), sut.getTodos(), "todos"),
        () -> assertEquals("1", sut.getTodoCount1().get(), "todo count 1"),
        () -> assertEquals(" item left", sut.getTodoCount2().get(), "todo count 2"),
        () -> assertFalse(sut.hasCompletedTodos().get(), "has completed todos"));
  }

  @Test
  void addSecondTodo() {
    sut.addTodo("Taste JavaScript");

    sut.addTodo("Buy unicorn");

    assertAll(
        () -> assertTrue(sut.hasTodos().get(), "has todos"),
        () -> assertFalse(sut.isAllCompleted().get(), "is all complete"),
        () ->
            assertEquals(
                List.of(new Todo(1, "Taste JavaScript", false), new Todo(2, "Buy unicorn", false)),
                sut.getTodos(),
                "todos"),
        () -> assertEquals("2", sut.getTodoCount1().get(), "todo count 1"),
        () -> assertEquals(" items left", sut.getTodoCount2().get(), "todo count 2"),
        () -> assertFalse(sut.hasCompletedTodos().get(), "has completed todos"));
  }

  @Test
  void doNotAddTodoIfTitleIsBlank() {
    sut.addTodo("Taste JavaScript");

    sut.addTodo("  ");

    assertAll(
        () -> assertTrue(sut.hasTodos().get(), "has todos"),
        () -> assertFalse(sut.isAllCompleted().get(), "is all complete"),
        () ->
            assertEquals(List.of(new Todo(1, "Taste JavaScript", false)), sut.getTodos(), "todos"),
        () -> assertEquals("1", sut.getTodoCount1().get(), "todo count 1"),
        () -> assertEquals(" item left", sut.getTodoCount2().get(), "todo count 2"),
        () -> assertFalse(sut.hasCompletedTodos().get(), "has completed todos"));
  }

  @Test
  void setTodoCompleted() {
    sut.addTodo("Taste JavaScript");
    sut.addTodo("Buy unicorn");

    sut.toggleTodo(1);

    assertAll(
        () -> assertTrue(sut.hasTodos().get(), "has todos"),
        () -> assertFalse(sut.isAllCompleted().get(), "is all complete"),
        () ->
            assertEquals(
                List.of(new Todo(1, "Taste JavaScript", true), new Todo(2, "Buy unicorn", false)),
                sut.getTodos(),
                "todos"),
        () -> assertEquals("1", sut.getTodoCount1().get(), "todo count 1"),
        () -> assertEquals(" item left", sut.getTodoCount2().get(), "todo count 2"),
        () -> assertTrue(sut.hasCompletedTodos().get(), "has completed todos"));
  }

  @Test
  void removeTodo() {
    sut.addTodo("Taste JavaScript");
    sut.addTodo("Buy unicorn");

    sut.destroyTodo(1);

    assertAll(
        () -> assertTrue(sut.hasTodos().get(), "has todos"),
        () -> assertFalse(sut.isAllCompleted().get(), "is all complete"),
        () -> assertEquals(List.of(new Todo(2, "Buy unicorn", false)), sut.getTodos(), "todos"),
        () -> assertEquals("1", sut.getTodoCount1().get(), "todo count 1"),
        () -> assertEquals(" item left", sut.getTodoCount2().get(), "todo count 2"),
        () -> assertFalse(sut.hasCompletedTodos().get(), "has completed todos"));
  }

  @Test
  void showOnlyActiveTodos() {
    sut.addTodo("Taste JavaScript");
    sut.addTodo("Buy unicorn");
    sut.toggleTodo(1);

    sut.setFilter(Filter.ACTIVE);

    assertAll(
        () -> assertTrue(sut.hasTodos().get(), "has todos"),
        () -> assertFalse(sut.isAllCompleted().get(), "is all complete"),
        () -> assertEquals(List.of(new Todo(2, "Buy unicorn", false)), sut.getTodos(), "todos"),
        () -> assertEquals("1", sut.getTodoCount1().get(), "todo count 1"),
        () -> assertEquals(" item left", sut.getTodoCount2().get(), "todo count 2"),
        () -> assertTrue(sut.hasCompletedTodos().get(), "has completed todos"));
  }

  @Test
  void showOnlyCompletedTodos() {
    sut.addTodo("Taste JavaScript");
    sut.addTodo("Buy unicorn");
    sut.toggleTodo(1);

    sut.setFilter(Filter.COMPLETED);

    assertAll(
        () -> assertTrue(sut.hasTodos().get(), "has todos"),
        () -> assertFalse(sut.isAllCompleted().get(), "is all complete"),
        () -> assertEquals(List.of(new Todo(1, "Taste JavaScript", true)), sut.getTodos(), "todos"),
        () -> assertEquals("1", sut.getTodoCount1().get(), "todo count 1"),
        () -> assertEquals(" item left", sut.getTodoCount2().get(), "todo count 2"),
        () -> assertTrue(sut.hasCompletedTodos().get(), "has completed todos"));
  }

  @Test
  void hideAllTodos() {
    sut.addTodo("Taste JavaScript");
    sut.addTodo("Buy unicorn");

    sut.setFilter(Filter.COMPLETED);

    assertAll(
        () -> assertTrue(sut.hasTodos().get(), "has todos"),
        () -> assertEquals(List.of(), sut.getTodos(), "todos"),
        () -> assertEquals("2", sut.getTodoCount1().get(), "todo count 1"),
        () -> assertEquals(" items left", sut.getTodoCount2().get(), "todo count 2"),
        () -> assertFalse(sut.hasCompletedTodos().get(), "has completed todos"));
  }

  @Test
  void removeCompletedTodos() {
    sut.addTodo("Taste JavaScript");
    sut.addTodo("Buy unicorn");
    sut.toggleTodo(1);

    sut.clearCompleted();

    assertAll(
        () -> assertTrue(sut.hasTodos().get(), "has todos"),
        () -> assertFalse(sut.isAllCompleted().get(), "is all complete"),
        () -> assertEquals(List.of(new Todo(2, "Buy unicorn", false)), sut.getTodos(), "todos"),
        () -> assertEquals("1", sut.getTodoCount1().get(), "todo count 1"),
        () -> assertEquals(" item left", sut.getTodoCount2().get(), "todo count 2"),
        () -> assertFalse(sut.hasCompletedTodos().get(), "has completed todos"));
  }

  @Test
  void setAllTodosCompleted() {
    sut.addTodo("Taste JavaScript");
    sut.addTodo("Buy unicorn");
    sut.toggleTodo(1);

    sut.toggleAll();

    assertAll(
        () -> assertTrue(sut.hasTodos().get(), "has todos"),
        () -> assertTrue(sut.isAllCompleted().get(), "is all complete"),
        () ->
            assertEquals(
                List.of(new Todo(1, "Taste JavaScript", true), new Todo(2, "Buy unicorn", true)),
                sut.getTodos(),
                "todos"),
        () -> assertEquals("0", sut.getTodoCount1().get(), "todo count 1"),
        () -> assertEquals(" items left", sut.getTodoCount2().get(), "todo count 2"),
        () -> assertTrue(sut.hasCompletedTodos().get(), "has completed todos"));
  }

  @Test
  void setAllTodosActive() {
    sut.addTodo("Taste JavaScript");
    sut.toggleTodo(1);
    sut.addTodo("Buy unicorn");
    sut.toggleTodo(2);

    sut.toggleAll();

    assertAll(
        () -> assertTrue(sut.hasTodos().get(), "has todos"),
        () -> assertFalse(sut.isAllCompleted().get(), "is all complete"),
        () ->
            assertEquals(
                List.of(new Todo(1, "Taste JavaScript", false), new Todo(2, "Buy unicorn", false)),
                sut.getTodos(),
                "todos"),
        () -> assertEquals("2", sut.getTodoCount1().get(), "todo count 1"),
        () -> assertEquals(" items left", sut.getTodoCount2().get(), "todo count 2"),
        () -> assertFalse(sut.hasCompletedTodos().get(), "has completed todos"));
  }

  @Test
  void changeTodosTitle() {
    sut.addTodo("Taste JavaScript");
    sut.addTodo("Buy unicorn");

    sut.saveTodo(1, "Taste TypeScript");

    assertAll(
        () -> assertTrue(sut.hasTodos().get(), "has todos"),
        () -> assertFalse(sut.isAllCompleted().get(), "is all complete"),
        () ->
            assertEquals(
                List.of(new Todo(1, "Taste TypeScript", false), new Todo(2, "Buy unicorn", false)),
                sut.getTodos(),
                "todos"),
        () -> assertEquals("2", sut.getTodoCount1().get(), "todo count 1"),
        () -> assertEquals(" items left", sut.getTodoCount2().get(), "todo count 2"),
        () -> assertFalse(sut.hasCompletedTodos().get(), "has completed todos"));
  }
}
