package de.muspellheim.todos.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.*;

public class TodosServiceTests {
  @Test
  void selectTodos_ReturnTodos() {
    var repository = new FakeTodos();
    repository.store(
        List.of(new Todo(1, "Taste JavaScript", true), new Todo(2, "Buy Unicorn", false)));
    var service = new TodosServiceImpl(repository);

    var todos = service.selectTodos();

    assertEquals(
        List.of(new Todo(1, "Taste JavaScript", true), new Todo(2, "Buy Unicorn", false)), todos);
  }
}
