package legoat.commands;

import java.util.ArrayList;

import legoat.exceptions.EmptyListException;
import legoat.tasktypes.Task;

/**
 * ListCommand handles listing tasks.
 */
public class ListCommand implements Command {
    @Override
    public String execute(String[] input, Parser taskHandler) throws EmptyListException {
        ArrayList<Task> tasks = taskHandler.getTasks();
        if (tasks.isEmpty()) {
            throw new EmptyListException();
        }
        StringBuilder sb = new StringBuilder();
        assert !tasks.isEmpty() : "List of tasks must not be empty";
        int lineNumber = 0;
        for (Task task : tasks) {
            lineNumber++;
            String line = lineNumber + ". " + taskHandler.getTaskBasedOnType(task).toString() + "\n";
            sb.append(line);
        }
        return sb.toString().trim();
    }
}
