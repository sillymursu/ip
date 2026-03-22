package legoat.commands;

import java.io.IOException;
import java.util.ArrayList;

import legoat.exceptions.WrongFormatTodoException;
import legoat.tasktypes.Task;
import legoat.tasktypes.TaskStatus;
import legoat.tasktypes.TaskType;

/**
 * TodoCommand handles todo creation.
 */
public class TodoCommand implements Command {
    /**
     * Creates and persists a todo task from user input.
     *
     * @param input User command tokens
     * @param taskHandler Parser that owns task state and storage
     * @return Success message for the created todo task
     * @throws IOException If persisting task data fails
     * @throws WrongFormatTodoException If task name is missing
     */
    @Override
    public String execute(String[] input, Parser taskHandler) throws IOException,
            WrongFormatTodoException {
        String taskName = extractTaskName(input);
        validateTaskName(taskName);
        return saveTodoAndBuildMessage(taskHandler, taskName);
    }

    /**
     * Extracts task name text from user input.
     *
     * @param input User command tokens
     * @return Trimmed task name
     */
    private String extractTaskName(String[] input) {
        StringBuilder todoName = new StringBuilder();
        for (int i = 1; i < input.length; i++) {
            todoName.append(" ");
            todoName.append(input[i]);
        }
        return todoName.toString().trim();
    }

    /**
     * Validates that task name is present.
     *
     * @param taskName Extracted task name
     * @throws WrongFormatTodoException If task name is empty
     */
    private void validateTaskName(String taskName) throws WrongFormatTodoException {
        if (taskName.isEmpty()) {
            throw new WrongFormatTodoException();
        }
        assert !taskName.isEmpty() : "taskName must not be empty";
    }

    /**
     * Persists the todo task and builds the user-facing success message.
     *
     * @param taskHandler Parser that owns task state and storage
     * @param taskName Final todo task name
     * @return Success message for UI output
     * @throws IOException If persisting task data fails
     */
    private String saveTodoAndBuildMessage(Parser taskHandler, String taskName) throws IOException {
        ArrayList<Task> tasks = taskHandler.getTasks();
        Task task = new Task(taskName, TaskType.TODO, TaskStatus.INCOMPLETE);
        tasks.add(task);
        taskHandler.getStorage().saveData(tasks);
        return "Added Task @ index " + tasks.size() + ":\n" + task;
    }
}
