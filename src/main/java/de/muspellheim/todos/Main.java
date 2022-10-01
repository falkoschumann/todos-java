package de.muspellheim.todos;

import de.muspellheim.todos.application.*;
import de.muspellheim.todos.domain.*;
import de.muspellheim.todos.infrastructure.*;
import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    var todos = new MemoryTodos();
    var model = new TodosService(todos);
    var viewModel = new TodosViewModel(model);
    SwingUtilities.invokeLater(
        () -> {
          var view = new TodosView(viewModel);
          view.run();
        });
  }
}
