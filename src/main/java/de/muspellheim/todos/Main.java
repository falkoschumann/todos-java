package de.muspellheim.todos;

import de.muspellheim.todos.application.*;
import de.muspellheim.todos.domain.*;
import de.muspellheim.todos.infrastructure.*;
import java.util.*;
import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    var todos = new MemoryTodos();
    todos.store(List.of(new Todo(1, "Taste JavaScript", true), new Todo(2, "Buy Unicorn", false)));
    var model = new TodosServiceImpl(todos);
    SwingUtilities.invokeLater(
        () -> {
          var view = new SwingTodosView();
          var controller = new TodosController(model, view);
          controller.run();
        });
  }
}
