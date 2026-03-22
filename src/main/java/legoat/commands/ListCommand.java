package legoat.commands;

import java.util.ArrayList;

import legoat.exceptions.EmptyListException;
import legoat.exceptions.WrongFormatListException;
import legoat.tasktypes.Task;

/**
 * ListCommand handles listing tasks.
 */
public class ListCommand implements Command {
    /**
     * Lists all tasks currently stored in the parser.
     *
     * @param input User command tokens
     * @param taskHandler Parser that owns task state
     * @return Numbered list of tasks
     * @throws EmptyListException If there are no tasks to list
     * @throws WrongFormatListException If command format is invalid
     */
    @Override
    public String execute(String[] input, Parser taskHandler) throws EmptyListException,
            WrongFormatListException {
        validateListCommand(input);
        ArrayList<Task> tasks = taskHandler.getTasks();
        validateListNotEmpty(tasks);
        return buildListMessage(taskHandler, tasks);
    }

    /**
     * Validates the list command keyword.
     *
     * @param input User command tokens
     * @throws WrongFormatListException If keyword is not {@code list}
     */
    private void validateListCommand(String[] input) throws WrongFormatListException {
        if (input.length != 1 || !input[0].equals("list")) {
            throw new WrongFormatListException();
        }
        assert input[0].equals("list") : "list command is just \"list\"!";
    }

    /**
     * Validates that there are tasks available for listing.
     *
     * @param tasks Current task list
     * @throws EmptyListException If task list is empty
     */
    private void validateListNotEmpty(ArrayList<Task> tasks) throws EmptyListException {
        if (tasks.isEmpty()) {
            throw new EmptyListException();
        }
        assert !tasks.isEmpty() : "List of tasks must not be empty";
    }

    /**
     * Builds the full numbered task list output.
     *
     * @param taskHandler Parser used to resolve concrete task type display
     * @param tasks Current task list
     * @return Numbered task list as text
     */
    private String buildListMessage(Parser taskHandler, ArrayList<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        int lineNumber = 0;
        for (Task task : tasks) {
            lineNumber++;
            appendTaskLine(sb, taskHandler, task, lineNumber);
        }
        return sb.toString().trim();
    }

    /**
     * Appends one numbered task line to the output builder.
     *
     * @param sb Output text builder
     * @param taskHandler Parser used to resolve concrete task type display
     * @param task Task to append
     * @param lineNumber 1-based line number for display
     */
    private void appendTaskLine(StringBuilder sb, Parser taskHandler, Task task, int lineNumber) {
        String line = lineNumber + ". " + taskHandler.getTaskBasedOnType(task).toString() + "\n";
        sb.append(line);
    }
}
