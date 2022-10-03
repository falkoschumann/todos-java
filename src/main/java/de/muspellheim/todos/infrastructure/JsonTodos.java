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
  public List<Todo> load() throws TodosException {
    var json = readFile();
    var dto = parseJson(json);
    return mapTodos(dto);
  }

  private String readFile() throws TodosException {
    if (!Files.exists(file)) {
      // Return empty JSON.
      return "[]";
    }

    try {
      return Files.readString(file);
    } catch (IOException e) {
      throw new TodosException("File is not readable.", e);
    }
  }

  private TodoDto[] parseJson(String json) throws TodosException {
    try {
      return new Gson().fromJson(json, TodoDto[].class);
    } catch (JsonSyntaxException e) {
      throw new TodosException("JSON file is not parsable.", e);
    }
  }

  private List<Todo> mapTodos(TodoDto[] dtos) throws TodosException {
    var todos = new ArrayList<Todo>();
    for (TodoDto dto : dtos) {
      try {
        var todo = new Todo(dto.id, dto.title, dto.completed);
        todos.add(todo);
      } catch (NullPointerException | IllegalAccessError e) {
        throw new TodosException("Todo is not valid.", e);
      }
    }
    return todos;
  }

  @Override
  public void store(List<Todo> todos) throws TodosException {
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

  private void writeFile(String json) throws TodosException {
    try {
      Files.writeString(file, json);
    } catch (IOException e) {
      throw new TodosException("File is not writable.", e);
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
