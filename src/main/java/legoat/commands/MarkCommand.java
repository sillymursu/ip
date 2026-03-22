package legoat.commands;

import java.io.IOException;
import java.util.ArrayList;

import legoat.exceptions.DoubleCompletionException;
import legoat.exceptions.WrongFormatMarkException;
import legoat.tasktypes.Task;
import legoat.tasktypes.TaskStatus;

/**
 * MarkCommand handles task completion.
 */
public class MarkCommand implements Command {
    /**
     * Marks a task as complete and persists the updated task list.
     *
     * @param input User command tokens
     * @param taskHandler Parser that owns task state and storage
     * @return User-facing completion confirmation message
     * @throws IOException If persisting task data fails
     * @throws DoubleCompletionException If task is already complete
     * @throws WrongFormatMarkException If index is missing or invalid
     */
    @Override
    public String execute(String[] input, Parser taskHandler) throws IOException,
            DoubleCompletionException, WrongFormatMarkException {
        try {
            int lineNum = parseLineNumber(input);
            ArrayList<Task> tasks = taskHandler.getTasks();
            Task task = getTaskAtLine(tasks, lineNum);

            ensureNotAlreadyCompleted(task);
            return markTaskAndBuildMessage(taskHandler, tasks, task);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new WrongFormatMarkException();
        }
    }

    /**
     * Parses a 1-based user index into a 0-based list index.
     *
     * @param input User command tokens
     * @return 0-based task index
     */
    private int parseLineNumber(String[] input) {
        int lineNum = Integer.parseInt(input[1]) - 1;
        assert lineNum >= 0 : "Line number must be positive";
        return lineNum;
    }

    /**
     * Retrieves the task at the provided index.
     *
     * @param tasks Current task list
     * @param lineNum 0-based task index
     * @return Task at the specified index
     */
    private Task getTaskAtLine(ArrayList<Task> tasks, int lineNum) {
        assert lineNum < tasks.size() : "Line number must not be out of bounds";
        return tasks.get(lineNum);
    }

    /**
     * Ensures the selected task is not already marked complete.
     *
     * @param task Selected task
     * @throws DoubleCompletionException If task is already complete
     */
    private void ensureNotAlreadyCompleted(Task task) throws DoubleCompletionException {
        if (task.getTaskStatus() == TaskStatus.COMPLETE) {
            throw new DoubleCompletionException();
        }
    }

    /**
     * Marks the selected task complete, saves state, and builds the response.
     *
     * @param taskHandler Parser that owns task state and storage
     * @param tasks Mutable task list
     * @param task Selected task
     * @return User-facing completion confirmation message
     * @throws IOException If persisting task data fails
     */
    private String markTaskAndBuildMessage(Parser taskHandler, ArrayList<Task> tasks, Task task)
            throws IOException {
        task.mark();
        taskHandler.getStorage().saveData(tasks);
        return "Easy work. Task completed!\n" + task;
    }
}
