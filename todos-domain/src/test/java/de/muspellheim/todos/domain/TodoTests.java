package de.muspellheim.todos.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class TodoTests {
  @Test
  void titleIsNull_Error() {
    var exception = assertThrows(NullPointerException.class, () -> new Todo(1, null, false));
    assertEquals("Title must not be null.", exception.getMessage());
  }

  @Test
  void titleIsEmpty_Error() {
    var exception = assertThrows(IllegalArgumentException.class, () -> new Todo(1, "", false));
    assertEquals("Title must not be empty.", exception.getMessage());
  }

  @Test
  void titleContainsOnlyWhitespaces_Error() {
    var exception = assertThrows(IllegalArgumentException.class, () -> new Todo(1, "  ", false));
    assertEquals("Title must not be empty.", exception.getMessage());
  }
}
