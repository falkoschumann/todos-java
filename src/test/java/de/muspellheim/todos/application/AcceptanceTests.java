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
    assertFalse(sut.hasTodos());
    assertEquals(List.of(), sut.getTodos());
    assertEquals("0 items left", sut.getCounter());
  }

  @Test
  void addTodo_AddFirstTodo() {
    sut.addTodo("Taste JavaScript");

    assertTrue(sut.hasTodos());
    assertEquals(List.of(new Todo(1, "Taste JavaScript", false)), sut.getTodos());
    assertEquals("1 item left", sut.getCounter());
  }

  @Test
  void addTodo_AddSecondTodo() {
    sut.addTodo("Taste JavaScript");

    sut.addTodo("Buy unicorn");

    assertTrue(sut.hasTodos());
    assertEquals(
        List.of(new Todo(1, "Taste JavaScript", false), new Todo(2, "Buy unicorn", false)),
        sut.getTodos());
    assertEquals("2 items left", sut.getCounter());
  }

  @Test
  void addTodo_TitleIsBlank_NoTodoIsAdded() {
    sut.addTodo("Taste JavaScript");

    sut.addTodo("  ");

    assertTrue(sut.hasTodos());
    assertEquals(List.of(new Todo(1, "Taste JavaScript", false)), sut.getTodos());
    assertEquals("1 item left", sut.getCounter());
  }

  @Test
  void toggleTodo_SetTodoCompleted() {
    sut.addTodo("Taste JavaScript");
    sut.addTodo("Buy unicorn");

    sut.toggleTodo(1);

    assertTrue(sut.hasTodos());
    assertEquals(
        List.of(new Todo(1, "Taste JavaScript", true), new Todo(2, "Buy unicorn", false)),
        sut.getTodos());
    assertEquals("1 item left", sut.getCounter());
  }

  @Test
  void destroyTodo_RemoveTodo() {
    sut.addTodo("Taste JavaScript");
    sut.addTodo("Buy unicorn");

    sut.destroyTodo(1);

    assertTrue(sut.hasTodos());
    assertEquals(List.of(new Todo(2, "Buy unicorn", false)), sut.getTodos());
    assertEquals("1 item left", sut.getCounter());
  }
}
