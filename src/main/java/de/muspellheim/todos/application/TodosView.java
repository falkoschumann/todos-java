package de.muspellheim.todos.application;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class TodosView extends JFrame {
  private final TodosViewModel viewModel;
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

    todoList = Box.createVerticalBox();
    todoList.setBackground(Color.WHITE);
    var todoListScroll = new JScrollPane(todoList);
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

  private void load() {
    todoList.removeAll();
    for (var todo : viewModel.getTodos()) {
      var todoItem = Box.createHorizontalBox();
      todoItem.add(new JCheckBox("", todo.completed()));
      todoItem.add(new JLabel(todo.title()));
      todoItem.add(Box.createHorizontalGlue());
      todoList.add(todoItem);
    }

    this.counter.setText(viewModel.getCounter());
  }
}
