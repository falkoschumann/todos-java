package de.muspellheim.todos.application;

import static org.junit.jupiter.api.Assertions.*;

import de.muspellheim.todos.domain.*;
import java.util.*;
import org.junit.jupiter.api.*;

public class TodosControllerTests {
  @Test
  void run_ZeroTodos() {
    var model = new TodosServiceStub();
    model.setTodos(List.of());
    var view = new TodosViewSpy();
    var controller = new TodosController(model, view);

    controller.run();

    assertEquals(1, view.getCallCountShow());
    assertEquals(List.of(), view.getLastTodos());
    assertEquals("0 items left", view.getLastCounter());
  }

  @Test
  void run_OneTodo() {
    var model = new TodosServiceStub();
    model.setTodos(List.of(new Todo(1, "Taste JavaScript", false)));
    var view = new TodosViewSpy();
    var controller = new TodosController(model, view);

    controller.run();

    assertEquals(List.of(new Todo(1, "Taste JavaScript", false)), view.getLastTodos());
    assertEquals("1 item left", view.getLastCounter());
  }

  @Test
  void run_TwoTodos() {
    var model = new TodosServiceStub();
    model.setTodos(
        List.of(new Todo(1, "Taste JavaScript", false), new Todo(2, "Buy unicorn", false)));
    var view = new TodosViewSpy();
    var controller = new TodosController(model, view);

    controller.run();

    assertEquals(
        List.of(new Todo(1, "Taste JavaScript", false), new Todo(2, "Buy unicorn", false)),
        view.getLastTodos());
    assertEquals("2 items left", view.getLastCounter());
  }

  @Test
  void run_OneTodoActiveAndOneTodoCompleted() {
    var model = new TodosServiceStub();
    model.setTodos(
        List.of(new Todo(1, "Taste JavaScript", true), new Todo(2, "Buy unicorn", false)));
    var view = new TodosViewSpy();
    var controller = new TodosController(model, view);

    controller.run();

    assertEquals(
        List.of(new Todo(1, "Taste JavaScript", true), new Todo(2, "Buy unicorn", false)),
        view.getLastTodos());
    assertEquals("1 item left", view.getLastCounter());
  }
}
