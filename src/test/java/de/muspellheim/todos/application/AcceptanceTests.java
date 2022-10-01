package de.muspellheim.todos.application;

import static org.junit.jupiter.api.Assertions.*;

import de.muspellheim.todos.domain.*;
import java.util.*;
import org.junit.jupiter.api.*;

public class AcceptanceTests {
  private SystemUnderTest sut;

  @BeforeEach
  void setUp() {
    sut = new SystemUnderTest();
  }

  @Test
  void run_ShowViewWithZeroTodos() {
    assertEquals(1, sut.getCallCountShow());
    assertEquals(List.of(), sut.getLastTodos());
    assertEquals("0 items left", sut.getLastCounter());
  }

  @Test
  void newTodo_AddFirstTodo() {
    sut.triggerNewTodo(new TodosView.NewTodoEvent("Taste JavaScript"));

    assertEquals(List.of(new Todo(1, "Taste JavaScript", false)), sut.getLastTodos());
    assertEquals("1 item left", sut.getLastCounter());
  }

  @Test
  void newTodo_AddSecondTodo() {
    sut.triggerNewTodo(new TodosView.NewTodoEvent("Taste JavaScript"));

    sut.triggerNewTodo(new TodosView.NewTodoEvent("Buy unicorn"));

    assertEquals(
        List.of(new Todo(1, "Taste JavaScript", false), new Todo(2, "Buy unicorn", false)),
        sut.getLastTodos());
    assertEquals("2 items left", sut.getLastCounter());
  }
}
