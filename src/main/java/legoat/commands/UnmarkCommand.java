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
    @Override
    public String execute(String[] input, Parser taskHandler) throws IOException,
            DoubleIncompleteException, WrongFormatUnmarkException {
        try {
            int lineNum = Integer.parseInt(input[1]) - 1;
            assert lineNum >= 0 : "Line number must be positive";

            ArrayList<Task> tasks = taskHandler.getTasks();
            assert lineNum < tasks.size() : "Line number must not be out of bounds";
            Task task = tasks.get(lineNum);

            if (task.getTaskStatus() == TaskStatus.INCOMPLETE) {
                throw new DoubleIncompleteException();
            }

            task.unmark();
            taskHandler.getStorage().saveData(tasks);
            return "Electric Boogaloo. Task uncompleted!\n" + task;
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new WrongFormatUnmarkException();
        }
    }
}
