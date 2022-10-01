package de.muspellheim.todos.application;

import de.muspellheim.todos.domain.*;
import de.muspellheim.todos.infrastructure.*;
import java.util.*;
import javax.swing.event.*;

public class SystemUnderTest implements TodosView {
  private final EventListenerList eventListenerList = new EventListenerList();
  private int callCountShow;
  private List<Todo> lastTodos;
  private String lastCounter;

  SystemUnderTest() {
    var repository = new MemoryTodos();
    var model = new TodosService(repository);
    TodosController controller = new TodosController(model, this);
    controller.run();
  }

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

  public List<Todo> getLastTodos() {
    return lastTodos;
  }

  @Override
  public void setCounter(String counter) {
    lastCounter = counter;
  }

  public String getLastCounter() {
    return lastCounter;
  }

  @Override
  public void addNewTodoListener(NewTodoListener listener) {
    eventListenerList.add(NewTodoListener.class, listener);
  }

  public void triggerNewTodo(NewTodoEvent event) {
    for (var l : eventListenerList.getListeners(NewTodoListener.class)) {
      l.accept(event);
    }
  }
}
