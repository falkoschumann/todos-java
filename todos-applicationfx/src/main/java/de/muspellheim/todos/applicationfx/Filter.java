package de.muspellheim.todos.applicationfx;

enum Filter {
  ALL("All"),
  ACTIVE("Active"),
  COMPLETED("Completed");

  private final String text;

  Filter(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
