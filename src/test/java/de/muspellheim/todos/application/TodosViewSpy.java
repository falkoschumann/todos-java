package de.muspellheim.todos.application;

import de.muspellheim.todos.domain.*;
import java.util.*;

public class TodosViewSpy implements TodosView {
  private int callCountShow;
  private List<Todo> lastTodos;
  private String lastCounter;

  @Override
  public void show() {
    callCountShow++;
  }

  public int getCallCountShow() {
    return callCountShow;
  }

  @Override
  public void setTodos(List<Todo> todos) {
    lastTodos = todos;
  }

  @Override
  public void setCounter(String counter) {
    lastCounter = counter;
  }

  public List<Todo> getLastTodos() {
    return lastTodos;
  }

  public String getLastCounter() {
    return lastCounter;
  }
}
