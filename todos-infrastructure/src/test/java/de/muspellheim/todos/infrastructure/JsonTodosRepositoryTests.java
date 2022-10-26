package de.muspellheim.todos.infrastructure;

import static org.junit.jupiter.api.Assertions.*;

import de.muspellheim.todos.domain.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.junit.jupiter.api.*;

class JsonTodosRepositoryTests {
  private final Path file = Paths.get("build/todos.json");

  @BeforeEach
  void init() throws IOException {
    Files.deleteIfExists(file);
  }

  @Test
  void loadAndStore() throws TodosException {
    var repository = new JsonTodosRepository(file);

    repository.store(List.of(new Todo(1, "foo", true), new Todo(2, "bar", false)));
    var todos = repository.load();

    assertEquals(List.of(new Todo(1, "foo", true), new Todo(2, "bar", false)), todos);
  }
}
