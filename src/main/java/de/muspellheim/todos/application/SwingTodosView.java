package de.muspellheim.todos.application;

import de.muspellheim.todos.domain.*;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class SwingTodosView extends JFrame implements TodosView {
  private final JComponent todoList;

  public SwingTodosView() {
    setTitle("Todos");
    setSize(320, 480);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationByPlatform(true);

    todoList = Box.createVerticalBox();
    todoList.setBackground(Color.WHITE);
    var todoListScroll = new JScrollPane(todoList);
    add(todoListScroll);
  }

  @Override
  public void setTodos(List<Todo> todos) {
    todoList.removeAll();
    for (var todo : todos) {
      var todoItem = Box.createHorizontalBox();
      todoItem.add(new JCheckBox("", todo.completed()));
      todoItem.add(new JLabel(todo.title()));
      todoItem.add(Box.createHorizontalGlue());
      todoList.add(todoItem);
    }
  }
}
