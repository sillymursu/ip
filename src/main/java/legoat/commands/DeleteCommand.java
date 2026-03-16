package legoat.commands;

import java.io.IOException;
import java.util.ArrayList;

import legoat.exceptions.WrongFormatDeleteException;
import legoat.tasktypes.Task;

/**
 * DeleteCommand handles deleting a task.
 */
public class DeleteCommand implements Command {
    @Override
    public String execute(String[] input, Parser taskHandler) throws IOException,
            WrongFormatDeleteException {
        try {
            int taskIndexToRemove = Integer.parseInt(input[1]) - 1;
            ArrayList<Task> tasks = taskHandler.getTasks();
            tasks.remove(taskIndexToRemove);
            taskHandler.getStorage().saveData(tasks);

            if (tasks.size() == 1) {
                return "Task deleted!!\nYou have " + tasks.size() + " Task left!!";
            }
            return "Task deleted!!\nYou have " + tasks.size() + " Tasks left!!";
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new WrongFormatDeleteException();
        }
    }
}
