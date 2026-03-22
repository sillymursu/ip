package legoat.commands;

import java.util.ArrayList;

import legoat.exceptions.WrongFormatFindException;
import legoat.tasktypes.Task;
import legoat.tasktypes.TaskType;

/**
 * FindCommand handles finding tasks by keyword.
 */
public class FindCommand implements Command {
    /**
     * Finds tasks whose names contain the provided keyword.
     *
     * @param input User command tokens
     * @param taskHandler Parser that owns task state
     * @return User-facing find result message
     * @throws WrongFormatFindException If command format is invalid
     */
    @Override
    public String execute(String[] input, Parser taskHandler) throws WrongFormatFindException {
        validateFindInput(input);
        SearchResult searchResult = findTasks(taskHandler, input[1]);
        return buildFindMessage(input[1], searchResult);
    }

    /**
     * Validates find command format.
     *
     * @param input User command tokens
     * @throws WrongFormatFindException If token count is invalid
     */
    private void validateFindInput(String[] input) throws WrongFormatFindException {
        if (input.length != 2) {
            throw new WrongFormatFindException();
        }
    }

    /**
     * Searches all tasks for name matches with the provided keyword.
     *
     * @param taskHandler Parser that owns task state
     * @param keyword Keyword used for matching task names
     * @return Aggregated search result data
     */
    private SearchResult findTasks(Parser taskHandler, String keyword) {
        ArrayList<Task> tasks = taskHandler.getTasks();
        StringBuilder sb = new StringBuilder();
        int tasksFound = 0;

        for (Task task : tasks) {
            if (task.getTaskName().contains(keyword)) {
                tasksFound++;
                appendTaskLine(sb, taskHandler, task);
            }
        }

        return new SearchResult(tasksFound, sb.toString().trim());
    }

    /**
     * Appends one matching task line to the output builder.
     *
     * @param sb Output text builder
     * @param taskHandler Parser used to resolve concrete task type display
     * @param task Matching task
     */
    private void appendTaskLine(StringBuilder sb, Parser taskHandler, Task task) {
        TaskType taskType = task.getTaskType();
        switch (taskType) {
        case DEADLINE, EVENT -> sb.append(taskHandler.getTaskBasedOnType(task).toString());
        default -> sb.append(task);
        }
        sb.append("\n");
    }

    /**
     * Builds final user-facing output for find command results.
     *
     * @param keyword Search keyword
     * @param searchResult Aggregated search result data
     * @return User-facing find output message
     */
    private String buildFindMessage(String keyword, SearchResult searchResult) {
        if (searchResult.tasksFound == 0) {
            return "Uh oh! No tasks with keyword " + keyword + " were found!";
        }

        if (searchResult.tasksFound > 1) {
            return "I found " + searchResult.tasksFound + " tasks:\n" + searchResult.tasksAsText;
        }
        return "I found 1 task:\n" + searchResult.tasksAsText;
    }

    /**
     * Data holder for find command search result summary.
     */
    private static class SearchResult {
        private final int tasksFound;
        private final String tasksAsText;

        private SearchResult(int tasksFound, String tasksAsText) {
            this.tasksFound = tasksFound;
            this.tasksAsText = tasksAsText;
        }
    }
}
