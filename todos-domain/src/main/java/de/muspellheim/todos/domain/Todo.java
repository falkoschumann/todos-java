package de.muspellheim.todos.domain;

import java.util.*;

public record Todo(int id, String title, boolean completed) {
  public Todo {
    Objects.requireNonNull(title, "Title must not be null.");
    if (title.isBlank()) {
      throw new IllegalArgumentException("Title must not be empty.");
    }
  }
}
