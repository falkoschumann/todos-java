package de.muspellheim.todos.application;

import de.muspellheim.todos.domain.*;
import java.awt.*;
import java.util.List;
import java.util.function.*;
import javax.swing.*;

class MainView extends JScrollPane {
  private final Consumer<Integer> onToggle;
  private final BiConsumer<Integer, String> onSave;
  private final Consumer<Integer> onDestroy;
  private final Box todoList;

  MainView(
      Consumer<Integer> onToggle, BiConsumer<Integer, String> onSave, Consumer<Integer> onDestroy) {
    this.onToggle = onToggle;
    this.onSave = onSave;
    this.onDestroy = onDestroy;
    todoList = Box.createVerticalBox();
    todoList.setBackground(Color.WHITE);
    setViewportView(todoList);
  }

  void setTodos(List<Todo> todos) {
    todoList.removeAll();
    for (var todo : todos) {
      var item =
          new TodoItemView(
              todo,
              () -> onToggle.accept(todo.id()),
              title -> onSave.accept(todo.id(), title),
              () -> onDestroy.accept(todo.id()));
      todoList.add(item);
    }
    todoList.revalidate();
    todoList.repaint();
  }
}
