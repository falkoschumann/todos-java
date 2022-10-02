package de.muspellheim.todos.application;

import java.util.function.*;
import javax.swing.*;

class HeaderView extends Box {
  private final JCheckBox toggleAll;
  private final JTextField newTodo;

  HeaderView(Runnable onToggleAll, Consumer<String> onNewTodo) {
    super(BoxLayout.X_AXIS);

    // WORKAROUND Toggle all should be part of main view, but we can not place a component outside
    //  clipping rect without custom clipping, so we move toggle all from main to header.
    toggleAll = new JCheckBox();
    toggleAll.addActionListener(e -> onToggleAll.run());
    add(toggleAll);

    newTodo = new JTextField();
    // WORKAROUND Swing has no placeholders, so we use a tooltip.
    newTodo.setToolTipText("What needs to be done?");
    newTodo.requestFocusInWindow();
    newTodo.addActionListener(e -> onNewTodo.accept(newTodo.getText()));
    add(newTodo);
  }

  void setToggleAllVisible(boolean visible) {
    toggleAll.setVisible(visible);
  }

  void setToggleAllSelected(boolean selected) {
    toggleAll.setSelected(selected);
  }

  void clearNewTodo() {
    newTodo.setText("");
  }
}
