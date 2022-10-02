package de.muspellheim.todos.application;

import de.muspellheim.todos.domain.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class TodosView extends JFrame {
  private final TodosViewModel viewModel;
  private final JTextField newTodo;
  private final JScrollPane todoListScroll;
  private final JComponent todoList;
  private final JLabel counter;

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

    todoList = Box.createVerticalBox();
    todoList.setBackground(Color.WHITE);
    todoListScroll = new JScrollPane(todoList);
    contentPane.add(todoListScroll, BorderLayout.CENTER);

    counter = new JLabel();
    counter.setBorder(new EmptyBorder(4, 8, 4, 8));
    contentPane.add(counter, BorderLayout.SOUTH);

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

  private void load() {
    todoListScroll.setVisible(viewModel.hasTodos());
    todoList.removeAll();
    for (var todo : viewModel.getTodos()) {
      var item = new TodoItemView(todo, this::handleToggle, this::handleDestroy);
      todoList.add(item);
    }
    todoList.revalidate();
    todoList.repaint();

    counter.setVisible(viewModel.hasTodos());
    counter.setText(viewModel.getCounter());
  }
}
