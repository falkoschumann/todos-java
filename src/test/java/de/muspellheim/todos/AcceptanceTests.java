package de.muspellheim.todos;

import static org.junit.jupiter.api.Assertions.*;

import de.muspellheim.todos.application.*;
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
  void run_ShowViewWithZeroTodos() {
    assertEquals(List.of(), sut.getTodos());
    assertEquals("0 items left", sut.getCounter());
  }

  @Test
  void newTodo_AddFirstTodo() {
    sut.createTodo("Taste JavaScript");

    assertEquals(List.of(new Todo(1, "Taste JavaScript", false)), sut.getTodos());
    assertEquals("1 item left", sut.getCounter());
  }

  @Test
  void newTodo_AddSecondTodo() {
    sut.createTodo("Taste JavaScript");

    sut.createTodo("Buy unicorn");

    assertEquals(
        List.of(new Todo(1, "Taste JavaScript", false), new Todo(2, "Buy unicorn", false)),
        sut.getTodos());
    assertEquals("2 items left", sut.getCounter());
  }
}
