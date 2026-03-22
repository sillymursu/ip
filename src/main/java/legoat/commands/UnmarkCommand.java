package legoat.commands;

import java.io.IOException;
import java.util.ArrayList;

import legoat.exceptions.DoubleIncompleteException;
import legoat.exceptions.WrongFormatUnmarkException;
import legoat.tasktypes.Task;
import legoat.tasktypes.TaskStatus;

/**
 * UnmarkCommand handles task incompletion.
 */
public class UnmarkCommand implements Command {
    /**
     * Marks a task as incomplete and persists the updated task list.
     *
     * @param input User command tokens
     * @param taskHandler Parser that owns task state and storage
     * @return User-facing incompletion confirmation message
     * @throws IOException If persisting task data fails
     * @throws DoubleIncompleteException If task is already incomplete
     * @throws WrongFormatUnmarkException If index is missing or invalid
     */
    @Override
    public String execute(String[] input, Parser taskHandler) throws IOException,
            DoubleIncompleteException, WrongFormatUnmarkException {
        try {
            int lineNum = parseLineNumber(input);
            ArrayList<Task> tasks = taskHandler.getTasks();
            Task task = getTaskAtLine(tasks, lineNum);

            ensureNotAlreadyIncomplete(task);
            return unmarkTaskAndBuildMessage(taskHandler, tasks, task);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new WrongFormatUnmarkException();
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
     * Ensures the selected task is not already incomplete.
     *
     * @param task Selected task
     * @throws DoubleIncompleteException If task is already incomplete
     */
    private void ensureNotAlreadyIncomplete(Task task) throws DoubleIncompleteException {
        if (task.getTaskStatus() == TaskStatus.INCOMPLETE) {
            throw new DoubleIncompleteException();
        }
    }

    /**
     * Marks the selected task incomplete, saves state, and builds the response.
     *
     * @param taskHandler Parser that owns task state and storage
     * @param tasks Mutable task list
     * @param task Selected task
     * @return User-facing incompletion confirmation message
     * @throws IOException If persisting task data fails
     */
    private String unmarkTaskAndBuildMessage(Parser taskHandler, ArrayList<Task> tasks, Task task)
            throws IOException {
        task.unmark();
        taskHandler.getStorage().saveData(tasks);
        return "Electric Boogaloo. Task uncompleted!\n" + task;
    }
}
