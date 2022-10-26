package de.muspellheim.todos.domain;

import java.util.*;
import java.util.function.*;

public class Result {
  private final boolean success;
  private final String errorMessage;

  protected Result(boolean success, String errorMessage) {
    this.success = success;
    this.errorMessage = errorMessage != null ? errorMessage : "";
  }

  public static Result ok() {
    return new Result(true, null);
  }

  public static Result fail(String errorMessage) {
    if (errorMessage.isBlank()) {
      throw new IllegalArgumentException("Error message must not be blank.");
    }

    return new Result(false, errorMessage);
  }

  public boolean isSuccess() {
    return success;
  }

  public boolean isFailure() {
    return !isSuccess();
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public Result onSuccess(Runnable action) {
    if (isFailure()) {
      return this;
    }

    action.run();
    return this;
  }

  public Result onFailure(Runnable action) {
    if (isSuccess()) {
      return this;
    }

    action.run();
    return this;
  }

  public Result onFailure(Consumer<String> action) {
    if (isSuccess()) {
      return this;
    }

    action.accept(getErrorMessage());
    return this;
  }

  public Result onBoth(Runnable action) {
    action.run();
    return this;
  }

  public Result onBoth(Consumer<Result> action) {
    action.accept(this);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Result result = (Result) o;
    return errorMessage.equals(result.errorMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(errorMessage);
  }

  @Override
  public String toString() {
    if (isSuccess()) {
      return "Success";
    } else {
      return "Failure(" + getErrorMessage() + ')';
    }
  }
}
