package de.muspellheim.todos.infrastructure;

import com.google.gson.*;
import de.muspellheim.todos.domain.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class JsonTodos implements Todos {
  private final Path file;

  public JsonTodos(Path file) {
    this.file = file;
  }

  @Override
  public List<Todo> load() {
    var json = readFile();
    var dto = parseJson(json);
    return mapTodos(dto);
  }

  private String readFile() {
    if (!Files.exists(file)) {
      // Return empty JSON.
      return "[]";
    }

    try {
      return Files.readString(file);
    } catch (IOException e) {
      System.err.println("Can not read from file: " + e);
      return "[]";
    }
  }

  private TodoDto[] parseJson(String json) {
    try {
      return new Gson().fromJson(json, TodoDto[].class);
    } catch (JsonSyntaxException e) {
      System.err.println("Can not parse JSON: " + e);
      return new TodoDto[0];
    }
  }

  private List<Todo> mapTodos(TodoDto[] dtos) {
    var todos = new ArrayList<Todo>();
    for (TodoDto dto : dtos) {
      try {
        var todo = new Todo(dto.id, dto.title, dto.completed);
        todos.add(todo);
      } catch (NullPointerException | IllegalAccessError e) {
        System.err.println("Can not create todo: " + e);
      }
    }
    return todos;
  }

  @Override
  public void store(List<Todo> todos) {
    var dto = createDto(todos);
    var json = createJson(dto);
    writeFile(json);
  }

  private static Object[] createDto(List<Todo> todos) {
    return todos.stream().map(e -> new TodoDto(e.id(), e.title(), e.completed())).toArray();
  }

  private static String createJson(Object[] dto) {
    var gson = new Gson();
    return gson.toJson(dto);
  }

  private void writeFile(String json) {
    try {
      Files.writeString(file, json);
    } catch (IOException e) {
      System.err.println("Can not write to file: " + e);
    }
  }

  public static class TodoDto {
    int id;
    String title;
    boolean completed;

    public TodoDto(int id, String title, boolean completed) {
      this.id = id;
      this.title = title;
      this.completed = completed;
    }
  }
}
