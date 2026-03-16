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
    @Override
    public String execute(String[] input, Parser taskHandler) throws IOException,
            DoubleCompletionException, WrongFormatMarkException {
        try {
            int lineNum = Integer.parseInt(input[1]) - 1;
            assert lineNum >= 0 : "Line number must be positive";

            ArrayList<Task> tasks = taskHandler.getTasks();
            assert lineNum < tasks.size() : "Line number must not be out of bounds";
            Task task = tasks.get(lineNum);

            if (task.getTaskStatus() == TaskStatus.COMPLETE) {
                throw new DoubleCompletionException();
            }

            task.mark();
            taskHandler.getStorage().saveData(tasks);
            return "Easy work. Task completed!\n" + task;
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new WrongFormatMarkException();
        }
    }
}
