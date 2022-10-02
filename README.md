# Todos

A simple app for managing todos, following the example
of [TodoMVC](https://todomvc.com).

## User Stories

### Create Todo

- [x] Focus new todo text field on startup.
- [x] If there are no todos, only display text field for new todo.
- [x] Remove spaces before and after text and only create todo if text is not
  empty.

### Show Todos

- [x] Show all todos.
- [x] Show only active or completed todos.
- [x] Display number of active todos.

### Edit Todo

- [ ] Edit a todo in the list by double-clicking and focus text field.
- [ ] When editing a todo, only show text box for editing.
- [ ] Save the change with `Enter` or if you lose focus and cancel the change
  with `Escape`.
- [ ] Remove spaces before and after text and delete todo if text is empty.

### Complete Todo

- [x] Mark a todo in the list as completed or active.
- [x] Strikethrough out a completed todo.
- [x] Mark all todos as completed or active.

### Remove Todo

- [x] Remove a todo from the list.
- [x] Hover over todo shows the remove button.
- [x] Remove all completed todos.
- [x] If there are no completed todos, the remove completed button is hidden.

## Messages

### Commands

- [x] Add todo (title)
- [x] Toggle todo (id)
- [x] Toggle all (checked)
- [x] Destroy todo (id)
- [x] Clear completed
- [ ] Save todo (id, title)

### Queries

- [x] Select todos -> (id, title, completed)\*

### Notifications

N/A

### Events

N/A
