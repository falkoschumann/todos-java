package de.muspellheim.todos.applicationfx;

import de.muspellheim.todos.domain.*;
import java.util.*;
import java.util.function.*;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

public class TodoCell extends ListCell<Todo> {
  private final Consumer<Integer> onToggle;
  private final BiConsumer<Integer, String> onSave;
  private final Consumer<Integer> onDestroy;

  public TodoCell(
      Consumer<Integer> onToggle, BiConsumer<Integer, String> onSave, Consumer<Integer> onDestroy) {
    this.onToggle = onToggle;
    this.onSave = onSave;
    this.onDestroy = onDestroy;
  }

  @Override
  protected void updateItem(Todo item, boolean empty) {
    super.updateItem(item, empty);

    if (empty || item == null) {
      setText(null);
      setGraphic(null);
    } else {
      setGraphic(new View(item));
    }
  }

  @Override
  public void startEdit() {
    super.startEdit();
    setGraphic(new Edit(getItem()));
  }

  @Override
  public void commitEdit(Todo newValue) {
    super.commitEdit(newValue);
    setGraphic(new View(newValue));
  }

  @Override
  public void cancelEdit() {
    super.cancelEdit();
    setGraphic(new View(getItem()));
  }

  private class View extends HBox {
    View(Todo todo) {
      setSpacing(8);
      setAlignment(Pos.BASELINE_LEFT);

      var completed = new CheckBox();
      completed.setSelected(todo.completed());
      completed.setOnAction(e -> onToggle.accept(todo.id()));
      getChildren().add(completed);

      var title = new Label(todo.title());
      title.setMaxWidth(Double.MAX_VALUE);
      if (todo.completed()) {
        Styles.add(title, "line-through");
      } else {
        Styles.remove(title, "line-through");
      }
      title.setOnMouseClicked(
          e -> {
            if (e.getClickCount() == 2) {
              startEdit();
            }
          });
      HBox.setHgrow(title, Priority.ALWAYS);
      getChildren().add(title);

      var destroyImage = "/images/destroy.png";
      var url = TodoCell.class.getResource(destroyImage);
      Objects.requireNonNull(url, "Resource not found: " + destroyImage);
      var destroy = new Button("", new ImageView(new Image(url.toExternalForm())));
      destroy.setVisible(false);
      destroy.setOnAction(e -> onDestroy.accept(todo.id()));
      getChildren().add(destroy);

      setOnMouseEntered(e -> destroy.setVisible(true));
      setOnMouseExited(e -> destroy.setVisible(false));
    }
  }

  private class Edit extends HBox {
    private final TextField title;

    Edit(Todo todo) {
      title = new TextField(todo.title());
      title.setOnKeyReleased(
          e -> {
            switch (e.getCode()) {
              case ENTER -> handleSubmit();
              case ESCAPE -> handleCancel();
            }
          });
      title
          .focusedProperty()
          .addListener(
              (observable, oldValue, newValue) -> {
                if (oldValue && !newValue) {
                  handleSubmit();
                }
              });
      HBox.setHgrow(title, Priority.ALWAYS);
      getChildren().add(title);

      Platform.runLater(
          () -> {
            title.selectAll();
            title.requestFocus();
          });
    }

    private void handleSubmit() {
      onSave.accept(getItem().id(), title.getText());
      commitEdit(new Todo(getItem().id(), title.getText(), getItem().completed()));
    }

    private void handleCancel() {
      title.setText(getItem().title());
      cancelEdit();
    }
  }
}
