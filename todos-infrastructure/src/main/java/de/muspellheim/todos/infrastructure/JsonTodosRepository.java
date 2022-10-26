package de.muspellheim.todos.infrastructure;

import com.google.gson.*;
import de.muspellheim.todos.domain.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class JsonTodosRepository implements Todos {
  private final Path file;

  public JsonTodosRepository(Path file) {
    this.file = file;
  }

  @Override
  public List<Todo> load() throws TodosException {
    var json = readFile();
    return parseJson(json);
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

  private List<Todo> parseJson(String json) throws TodosException {
    try {
      Todo[] todos = new Gson().fromJson(json, Todo[].class);
      return List.of(todos);
    } catch (JsonSyntaxException e) {
      throw new TodosException("JSON file is not parsable.", e);
    }
  }

  @Override
  public void store(List<Todo> todos) throws TodosException {
    var json = createJson(todos);
    writeFile(json);
  }

  private static String createJson(List<Todo> todos) {
    var gson = new Gson();
    return gson.toJson(todos);
  }

  private void writeFile(String json) throws TodosException {
    try {
      Files.writeString(file, json);
    } catch (IOException e) {
      throw new TodosException("File is not writable.", e);
    }
  }
}
