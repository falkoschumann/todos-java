package de.muspellheim.todos.applicationfx;

import javafx.scene.*;

class Styles {
  private Styles() {}

  static void add(Node node, String style) {
    if (!node.getStyleClass().contains(style)) {
      node.getStyleClass().add(style);
    }
  }

  static void remove(Node node, String style) {
    node.getStyleClass().remove(style);
  }
}
