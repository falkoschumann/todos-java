package de.muspellheim.todos.application;

import static org.junit.jupiter.api.Assertions.*;

import de.muspellheim.todos.domain.*;
import de.muspellheim.todos.infrastructure.*;
import java.util.*;
import org.junit.jupiter.api.*;

public class AcceptanceTests {
  private TodosViewModel sut;

  @BeforeEach
  void setUp() {
    var repository = new MemoryTodos();
    var model = new TodosService(repository);
    sut = new TodosViewModel(model);
  }

  @Test
  void run_NoTodos() {
    assertAll(
        () -> assertFalse(sut.hasTodos(), "has todos"),
        () -> assertEquals(List.of(), sut.getTodos(), "todos"),
        () -> assertEquals("0 items left", sut.getCounter(), "counter"));
  }

  @Test
  void addTodo_AddFirstTodo() {
    sut.addTodo("Taste JavaScript");

    assertAll(
        () -> assertTrue(sut.hasTodos(), "has todos"),
        () ->
            assertEquals(List.of(new Todo(1, "Taste JavaScript", false)), sut.getTodos(), "todos"),
        () -> assertEquals("1 item left", sut.getCounter(), "counter"));
  }

  @Test
  void addTodo_AddSecondTodo() {
    sut.addTodo("Taste JavaScript");

    sut.addTodo("Buy unicorn");

    assertAll(
        () -> assertTrue(sut.hasTodos(), "has todos"),
        () ->
            assertEquals(
                List.of(new Todo(1, "Taste JavaScript", false), new Todo(2, "Buy unicorn", false)),
                sut.getTodos(),
                "todos"),
        () -> assertEquals("2 items left", sut.getCounter(), "counter"));
  }

  @Test
  void addTodo_TitleIsBlank_NoTodoIsAdded() {
    sut.addTodo("Taste JavaScript");

    sut.addTodo("  ");

    assertAll(
        () -> assertTrue(sut.hasTodos(), "has todos"),
        () ->
            assertEquals(List.of(new Todo(1, "Taste JavaScript", false)), sut.getTodos(), "todos"),
        () -> assertEquals("1 item left", sut.getCounter(), "counter"));
  }

  @Test
  void toggleTodo_SetTodoCompleted() {
    sut.addTodo("Taste JavaScript");
    sut.addTodo("Buy unicorn");

    sut.toggleTodo(1);

    assertAll(
        () -> assertTrue(sut.hasTodos(), "has todos"),
        () ->
            assertEquals(
                List.of(new Todo(1, "Taste JavaScript", true), new Todo(2, "Buy unicorn", false)),
                sut.getTodos(),
                "todos"),
        () -> assertEquals("1 item left", sut.getCounter(), "counter"));
  }

  @Test
  void destroyTodo_RemoveTodo() {
    sut.addTodo("Taste JavaScript");
    sut.addTodo("Buy unicorn");

    sut.destroyTodo(1);

    assertAll(
        () -> assertTrue(sut.hasTodos(), "has todos"),
        () -> assertEquals(List.of(new Todo(2, "Buy unicorn", false)), sut.getTodos(), "todos"),
        () -> assertEquals("1 item left", sut.getCounter(), "counter"));
  }

  @Test
  void setFilter_ShowOnlyActiveTodos() {
    sut.addTodo("Taste JavaScript");
    sut.addTodo("Buy unicorn");
    sut.toggleTodo(1);

    sut.setFilter(Filter.ACTIVE);

    assertAll(
        () -> assertTrue(sut.hasTodos(), "has todos"),
        () -> assertEquals(List.of(new Todo(2, "Buy unicorn", false)), sut.getTodos(), "todos"),
        () -> assertEquals("1 item left", sut.getCounter(), "counter"));
  }

  @Test
  void setFilter_ShowOnlyCompletedTodos() {
    sut.addTodo("Taste JavaScript");
    sut.addTodo("Buy unicorn");
    sut.toggleTodo(1);

    sut.setFilter(Filter.COMPLETED);

    assertAll(
        () -> assertTrue(sut.hasTodos(), "has todos"),
        () -> assertEquals(List.of(new Todo(1, "Taste JavaScript", true)), sut.getTodos(), "todos"),
        () -> assertEquals("1 item left", sut.getCounter(), "counter"));
  }

  @Test
  void setFilter_HideAllTodos() {
    sut.addTodo("Taste JavaScript");
    sut.addTodo("Buy unicorn");

    sut.setFilter(Filter.COMPLETED);

    assertAll(
        () -> assertTrue(sut.hasTodos(), "has todos"),
        () -> assertEquals(List.of(), sut.getTodos(), "todos"),
        () -> assertEquals("2 items left", sut.getCounter(), "counter"));
  }
}
