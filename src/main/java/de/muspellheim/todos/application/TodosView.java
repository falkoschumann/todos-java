package de.muspellheim.todos.application;

import de.muspellheim.todos.domain.*;
import java.awt.*;
import javax.swing.*;

public class TodosView extends JFrame {
  private final TodosViewModel viewModel;
  private final HeaderView header;
  private final MainView main;
  private final FooterView footer;

  public TodosView(TodosService model) {
    this.viewModel = new TodosViewModel(model);

    setTitle("Todos");
    setPreferredSize(new Dimension(400, 600));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationByPlatform(true);

    var contentPane = new JPanel(new BorderLayout());
    add(contentPane);

    header = new HeaderView(this::handleToggleAll, this::handleNewTodo);
    contentPane.add(header, BorderLayout.NORTH);

    main = new MainView(this::handleToggle, this::handleDestroy);
    contentPane.add(main, BorderLayout.CENTER);

    footer = new FooterView(this::handleFilter, this::handleClearCompleted);
    contentPane.add(footer, BorderLayout.SOUTH);

    pack();
  }

  public void run() {
    setVisible(true);
    load();
  }

  private void handleNewTodo(String title) {
    viewModel.addTodo(title);
    header.clearNewTodo();
    load();
  }

  private void handleToggleAll() {
    viewModel.toggleAll();
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

  private void handleClearCompleted() {
    viewModel.clearCompleted();
    load();
  }

  private void load() {
    header.setToggleAllVisible(viewModel.hasTodos());
    header.setToggleAllSelected(viewModel.isAllCompleted());
    main.setVisible(viewModel.hasTodos());
    main.setTodos(viewModel.getTodos());
    footer.setVisible(viewModel.hasTodos());
    footer.setCounter(viewModel.getCounter());
    footer.setClearCompletedVisible(viewModel.hasCompletedTodos());
  }
}
