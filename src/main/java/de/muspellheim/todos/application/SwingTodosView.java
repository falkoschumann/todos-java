package de.muspellheim.todos.application;

import de.muspellheim.todos.domain.*;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class SwingTodosView extends JFrame implements TodosView {
  private final EventListenerList eventListenerList = new EventListenerList();
  private final JComponent todoList;
  private final JLabel counter;

  public SwingTodosView() {
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

  @Override
  public void setCounter(String counter) {
    this.counter.setText(counter);
  }

  @Override
  public void addNewTodoListener(NewTodoListener listener) {
    eventListenerList.add(NewTodoListener.class, listener);
  }
}
