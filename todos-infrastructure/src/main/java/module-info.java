module de.muspellheim.todos.infrastructure {
  exports de.muspellheim.todos.infrastructure;

  requires com.google.gson;
  requires transitive de.muspellheim.todos.domain;

  opens de.muspellheim.todos.infrastructure to
      com.google.gson;
}
