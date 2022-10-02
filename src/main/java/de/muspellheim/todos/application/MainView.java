package de.muspellheim.todos.application;

import de.muspellheim.todos.domain.*;
import java.awt.*;
import java.util.List;
import java.util.function.*;
import javax.swing.*;

class MainView extends JScrollPane {
  private final Consumer<Integer> onToggle;
  private final Consumer<Integer> onDestroy;
  private final Box todoList;

  MainView(Consumer<Integer> onToggle, Consumer<Integer> onDestroy) {
    this.onToggle = onToggle;
    this.onDestroy = onDestroy;
    todoList = Box.createVerticalBox();
    todoList.setBackground(Color.WHITE);
    setViewportView(todoList);
  }

  void setTodos(List<Todo> todos) {
    todoList.removeAll();
    for (var todo : todos) {
      var item = new TodoItemView(todo, onToggle, onDestroy);
      todoList.add(item);
    }
    todoList.revalidate();
    todoList.repaint();
  }
}
