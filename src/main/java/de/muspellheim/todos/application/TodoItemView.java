package de.muspellheim.todos.application;

import de.muspellheim.todos.domain.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.*;
import javax.swing.*;

class TodoItemView extends Box {
  private final JButton destroy;

  TodoItemView(Todo todo, Consumer<Integer> onToggle, Consumer<Integer> onDestroy) {
    super(BoxLayout.X_AXIS);

    var completed = new JCheckBox();
    completed.setSelected(todo.completed());
    completed.addActionListener(e -> onToggle.accept(todo.id()));
    add(completed);

    var title = new JLabel(todo.title());
    title.setMaximumSize(new Dimension(Short.MAX_VALUE, 32));
    var titleText = todo.title();
    if (todo.completed()) {
      titleText = "<html><strike>" + titleText + "</strike><html>";
    }
    title.setText(titleText);
    title.addMouseListener(new MouseHoverListener());
    add(title);

    destroy = new JButton("❌");
    destroy.setVisible(false);
    destroy.addActionListener(e -> onDestroy.accept(todo.id()));
    destroy.addMouseListener(new MouseHoverListener());
    add(destroy);
  }

  private class MouseHoverListener extends MouseAdapter {
    @Override
    public void mouseEntered(MouseEvent e) {
      destroy.setVisible(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
      destroy.setVisible(false);
    }
  }
}
