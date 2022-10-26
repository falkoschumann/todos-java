package de.muspellheim.todos.domain;

public class Exceptions {
  private Exceptions() {}

  public static String summarize(String errorMessage, Throwable cause) {
    if (cause == null) {
      return errorMessage;
    }

    return summarize(errorMessage + " " + cause.getMessage(), cause.getCause());
  }
}
