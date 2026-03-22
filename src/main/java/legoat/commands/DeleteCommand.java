package legoat.commands;

import java.io.IOException;
import java.util.ArrayList;

import legoat.exceptions.WrongFormatDeleteException;
import legoat.tasktypes.Task;

/**
 * DeleteCommand handles deleting a task.
 */
public class DeleteCommand implements Command {
    /**
     * Deletes a task by 1-based index and persists the updated task list.
     *
     * @param input User command tokens
     * @param taskHandler Parser that owns task state and storage
     * @return User-facing delete confirmation message
     * @throws IOException If persisting task data fails
     * @throws WrongFormatDeleteException If index is missing or invalid
     */
    @Override
    public String execute(String[] input, Parser taskHandler) throws IOException,
            WrongFormatDeleteException {
        try {
            int taskIndexToRemove = parseTaskIndex(input);
            ArrayList<Task> tasks = taskHandler.getTasks();
            removeTaskAndSave(taskHandler, tasks, taskIndexToRemove);
            return buildDeleteMessage(tasks.size());
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new WrongFormatDeleteException();
        }
    }

    /**
     * Parses the user-supplied 1-based task index into a 0-based index.
     *
     * @param input User command tokens
     * @return 0-based task index
     */
    private int parseTaskIndex(String[] input) {
        return Integer.parseInt(input[1]) - 1;
    }

    /**
     * Removes the target task and persists the updated task list.
     *
     * @param taskHandler Parser that owns task state and storage
     * @param tasks Mutable task list
     * @param taskIndexToRemove 0-based index of task to delete
     * @throws IOException If persisting task data fails
     */
    private void removeTaskAndSave(Parser taskHandler, ArrayList<Task> tasks, int taskIndexToRemove)
            throws IOException {
        tasks.remove(taskIndexToRemove);
        taskHandler.getStorage().saveData(tasks);
    }

    /**
     * Builds singular/plural delete confirmation output.
     *
     * @param tasksSize Number of tasks remaining
     * @return User-facing delete confirmation message
     */
    private String buildDeleteMessage(int tasksSize) {
        if (tasksSize == 1) {
            return "Task deleted!!\nYou have " + tasksSize + " Task left!!";
        }
        return "Task deleted!!\nYou have " + tasksSize + " Tasks left!!";
    }
}
