package de.muspellheim.todos.application;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class TodosView extends JFrame {
  private final TodosViewModel viewModel;
  private final JTextField newTodo;
  private final JScrollPane todoListScroll;
  private final JComponent todoList;
  private final JLabel counter;

  public TodosView(TodosViewModel viewModel) {
    this.viewModel = viewModel;

    setTitle("Todos");
    setPreferredSize(new Dimension(320, 480));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationByPlatform(true);

    var contentPane = new JPanel(new BorderLayout());
    add(contentPane);

    newTodo = new JTextField();
    newTodo.setToolTipText("What needs to be done?");
    newTodo.requestFocusInWindow();
    newTodo.addActionListener(e -> handleNewTodo(viewModel));
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

  private void handleNewTodo(TodosViewModel viewModel) {
    viewModel.createTodo(newTodo.getText());
    newTodo.setText("");
    load();
  }

  private void load() {
    todoListScroll.setVisible(viewModel.hasTodos());
    todoList.removeAll();
    for (var todo : viewModel.getTodos()) {
      var todoItem = Box.createHorizontalBox();
      todoItem.add(new JCheckBox("", todo.completed()));
      todoItem.add(new JLabel(todo.title()));
      todoItem.add(Box.createHorizontalGlue());
      todoList.add(todoItem);
    }

    counter.setVisible(viewModel.hasTodos());
    counter.setText(viewModel.getCounter());
  }
}
