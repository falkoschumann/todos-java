package de.muspellheim.todos.application;

import java.util.function.*;
import javax.swing.*;
import javax.swing.border.*;

class FooterView extends Box {
  private final JLabel counter;

  FooterView(Consumer<Filter> onFilter) {
    super(BoxLayout.X_AXIS);

    counter = new JLabel();
    counter.setBorder(new EmptyBorder(4, 8, 4, 8));
    add(counter);

    add(Box.createHorizontalGlue());

    var filter = new JComboBox<>(Filter.values());
    filter.addActionListener(e -> onFilter.accept((Filter) filter.getSelectedItem()));
    add(filter);

    add(Box.createHorizontalGlue());
  }

  void setCounter(String text) {
    counter.setText(text);
  }
}
