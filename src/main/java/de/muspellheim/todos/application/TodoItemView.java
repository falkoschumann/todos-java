package de.muspellheim.todos.application;

import de.muspellheim.todos.domain.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.*;
import javax.swing.*;

class TodoItemView extends JPanel {
  private static final String CARD_VIEW = "VIEW";
  private static final String CARD_EDIT = "EDIT";

  private final CardLayout layout;
  private final Edit edit;

  TodoItemView(Todo todo, Runnable onToggle, Consumer<String> onSave, Runnable onDestroy) {
    setMaximumSize(new Dimension(Short.MAX_VALUE, 32));
    setOpaque(false);
    layout = new CardLayout();
    setLayout(layout);

    View view = new View(todo, onToggle, onDestroy);
    add(view, CARD_VIEW);

    edit = new Edit(todo, onSave);
    add(edit, CARD_EDIT);
  }

  private class View extends Box {
    private final JButton destroy;

    View(Todo todo, Runnable onToggle, Runnable onDestroy) {
      super(BoxLayout.X_AXIS);

      var completed = new JCheckBox();
      completed.setSelected(todo.completed());
      completed.addActionListener(e -> onToggle.run());
      add(completed);

      var title = new JLabel(todo.title());
      title.setMaximumSize(new Dimension(Short.MAX_VALUE, 32));
      var titleText = todo.title();
      if (todo.completed()) {
        titleText = "<html><strike>" + titleText + "</strike><html>";
      }
      title.setText(titleText);
      title.addMouseListener(new MouseClickListener());
      title.addMouseListener(new MouseHoverListener());
      add(title);

      destroy = new JButton("❌");
      destroy.setVisible(false);
      destroy.addActionListener(e -> onDestroy.run());
      destroy.addMouseListener(new MouseHoverListener());
      add(destroy);
    }

    private class MouseClickListener extends MouseAdapter {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          layout.show(TodoItemView.this, CARD_EDIT);
          edit.requestFocus();
        }
      }
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

  private class Edit extends Box {
    private final Todo todo;
    private final Consumer<String> onSave;
    private final JTextField title;

    Edit(Todo todo, Consumer<String> onSave) {
      super(BoxLayout.X_AXIS);
      this.todo = todo;
      this.onSave = onSave;

      title = new JTextField();
      title.setText(todo.title());
      title.addKeyListener(new KeyPressedListener());
      title.addFocusListener(new FocusLostListener());
      add(title);
    }

    private void handleSubmit() {
      onSave.accept(title.getText());
      layout.show(TodoItemView.this, CARD_VIEW);
    }

    private void handleCancel() {
      title.setText(todo.title());
      layout.show(TodoItemView.this, CARD_VIEW);
    }

    @Override
    public void requestFocus() {
      title.requestFocus();
    }

    private class KeyPressedListener extends KeyAdapter {
      @Override
      public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
          case KeyEvent.VK_ENTER -> handleSubmit();
          case KeyEvent.VK_ESCAPE -> handleCancel();
        }
      }
    }

    private class FocusLostListener extends FocusAdapter {
      @Override
      public void focusLost(FocusEvent e) {
        handleSubmit();
      }
    }
  }
}
