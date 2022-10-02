package de.muspellheim.todos.application;

import de.muspellheim.todos.domain.*;
import java.awt.*;
import javax.swing.*;

public class TodosView extends JFrame {
  private final TodosViewModel viewModel;
  private final JTextField newTodo;
  private final MainView main;
  private final FooterView footer;

  public TodosView(TodosService model) {
    this.viewModel = new TodosViewModel(model);

    setTitle("Todos");
    setPreferredSize(new Dimension(320, 480));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationByPlatform(true);

    var contentPane = new JPanel(new BorderLayout());
    add(contentPane);

    newTodo = new JTextField();
    newTodo.setToolTipText("What needs to be done?");
    newTodo.requestFocusInWindow();
    newTodo.addActionListener(e -> handleNewTodo());
    contentPane.add(newTodo, BorderLayout.NORTH);

    main = new MainView(this::handleToggle, this::handleDestroy);
    contentPane.add(main, BorderLayout.CENTER);

    footer = new FooterView(this::handleFilter);
    contentPane.add(footer, BorderLayout.SOUTH);

    pack();
  }

  public void run() {
    setVisible(true);
    load();
  }

  private void handleNewTodo() {
    String title = newTodo.getText();
    viewModel.addTodo(title);
    newTodo.setText("");
    load();
  }

  private void handleToggle(int id) {
    viewModel.toggleTodo(id);
    load();
  }

  private void handleDestroy(int id) {
    viewModel.destroyTodo(id);
    load();
  }

  private void handleFilter(Filter filter) {
    viewModel.setFilter(filter);
    load();
  }

  private void load() {
    main.setVisible(viewModel.hasTodos());
    main.setTodos(viewModel.getTodos());
    footer.setVisible(viewModel.hasTodos());
    footer.setCounter(viewModel.getCounter());
  }
}
