package de.muspellheim.todos.domain;

public record Failure(String errorMessage) implements CommandStatus {
  public Failure(String errorMessage, Throwable cause) {
    this(join(errorMessage, cause));
  }

  private static String join(String errorMessage, Throwable cause) {
    if (cause == null) {
      return errorMessage;
    }

    return join(errorMessage + " " + cause.getMessage(), cause.getCause());
  }
}
