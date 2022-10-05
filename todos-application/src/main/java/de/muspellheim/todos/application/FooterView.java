package de.muspellheim.todos.application;

import java.util.function.*;
import javax.swing.*;
import javax.swing.border.*;

class FooterView extends Box {
  private final JLabel todoCount;
  private final JButton clearCompleted;

  FooterView(Consumer<Filter> onFilter, Runnable onClearCompleted) {
    super(BoxLayout.X_AXIS);

    todoCount = new JLabel();
    todoCount.setBorder(new EmptyBorder(4, 8, 4, 8));
    add(todoCount);

    add(Box.createHorizontalGlue());

    var filters = new JComboBox<>(Filter.values());
    filters.addActionListener(e -> onFilter.accept((Filter) filters.getSelectedItem()));
    add(filters);

    add(Box.createHorizontalGlue());

    clearCompleted = new JButton("Clear completed");
    clearCompleted.setVisible(false);
    clearCompleted.addActionListener(e -> onClearCompleted.run());
    add(clearCompleted);
  }

  void setTodoCount(String text) {
    todoCount.setText(text);
  }

  void setClearCompletedVisible(boolean visible) {
    clearCompleted.setVisible(visible);
  }
}
