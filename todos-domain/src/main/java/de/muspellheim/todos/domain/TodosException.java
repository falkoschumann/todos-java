package de.muspellheim.todos.domain;

public class TodosException extends Exception {
  public TodosException(String message) {
    super(message);
  }

  public TodosException(String message, Throwable cause) {
    super(message, cause);
  }
}
