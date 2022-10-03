package de.muspellheim.todos.domain;

public sealed interface CommandStatus permits Success, Failure {}
