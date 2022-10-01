package de.muspellheim.todos.application;

import static org.junit.jupiter.api.Assertions.*;

import de.muspellheim.todos.domain.*;
import java.util.*;
import org.junit.jupiter.api.*;

public class TodosControllerTests {
  @Test
  void run_ShowTodos() {
    var model = new TodosServiceStub();
    var view = new TodosViewSpy();
    var controller = new TodosController(model, view);

    controller.run();

    assertEquals(1, view.getCallCountShow());
    assertEquals(
        List.of(new Todo(1, "Taste JavaScript", true), new Todo(2, "Buy Unicorn", false)),
        view.getLastTodos());
  }
}
