package legoat.commands;

import java.io.IOException;
import java.util.ArrayList;

import legoat.exceptions.WrongFormatDeadlineException;
import legoat.tasktypes.Deadline;
import legoat.tasktypes.Task;
import legoat.tasktypes.TaskStatus;
import legoat.tasktypes.TaskType;
import legoat.ui.StringFormat;

/**
 * DeadlineCommand handles deadline creation.
 */
public class DeadlineCommand implements Command {
    /**
     * Creates and persists a deadline task from parsed user input.
     *
     * @param input User command tokens
     * @param taskHandler Parser that owns task state and storage
     * @return Success message for the created deadline
     * @throws IOException If persisting task data fails
     * @throws WrongFormatDeadlineException If required deadline fields are missing
     */
    @Override
    public String execute(String[] input, Parser taskHandler) throws IOException,
            WrongFormatDeadlineException {
        String taskName = extractTaskName(input);
        String rawDeadline = extractRawDeadline(input);
        validateRequiredFields(taskName, rawDeadline);

        String parsedDeadlineDate = StringFormat.parseDate(rawDeadline);
        String taskDeadline = resolveTaskDeadline(rawDeadline, parsedDeadlineDate);
        String reminder = buildReminder(parsedDeadlineDate);

        return saveDeadlineAndBuildMessage(taskHandler, taskName, taskDeadline, reminder);
    }

    /**
     * Extracts the deadline task name from user input up to the {@code /by} delimiter.
     *
     * @param input User command tokens
     * @return Trimmed task name
     */
    private String extractTaskName(String[] input) {
        StringBuilder deadlineName = new StringBuilder();
        for (int i = 1; i < input.length; i++) {
            String current = input[i];
            if (current.equals("/by")) {
                break;
            }
            deadlineName.append(" ");
            deadlineName.append(current);
        }

        return deadlineName.toString().trim();
    }

    /**
     * Extracts the raw deadline segment after the {@code /by} delimiter.
     *
     * @param input User command tokens
     * @return Trimmed raw deadline text
     */
    private String extractRawDeadline(String[] input) {
        StringBuilder deadlineDate = new StringBuilder();
        int splitIndex = findIndexAfterBy(input);

        for (int j = splitIndex; j < input.length; j++) {
            deadlineDate.append(" ");
            deadlineDate.append(input[j]);
        }

        return deadlineDate.toString().trim();
    }

    /**
     * Finds the first index immediately after {@code /by}.
     *
     * @param input User command tokens
     * @return Index to start reading deadline text from
     */
    private int findIndexAfterBy(String[] input) {
        int splitIndex = 1;
        for (int i = 1; i < input.length; i++) {
            splitIndex++;
            if (input[i].equals("/by")) {
                break;
            }
        }
        return splitIndex;
    }

    /**
     * Validates that the command contains both task name and deadline text.
     *
     * @param taskName Extracted task name
     * @param taskDeadline Extracted deadline text
     * @throws WrongFormatDeadlineException If any required field is empty
     */
    private void validateRequiredFields(String taskName, String taskDeadline)
            throws WrongFormatDeadlineException {
        if (taskName.isEmpty() || taskDeadline.isEmpty()) {
            throw new WrongFormatDeadlineException();
        }
    }

    /**
     * Resolves the final deadline value, preferring parsed formatted dates when available.
     *
     * @param rawDeadline Raw user-entered deadline text
     * @param parsedDeadlineDate Parsed and formatted deadline, or empty when parsing fails
     * @return Final deadline string stored in the task
     */
    private String resolveTaskDeadline(String rawDeadline, String parsedDeadlineDate) {
        String taskDeadline = parsedDeadlineDate.isEmpty() ? rawDeadline : parsedDeadlineDate;
        assert !taskDeadline.isEmpty() : "taskDeadline must not be empty";
        return taskDeadline;
    }

    /**
     * Builds a formatting reminder when the deadline is not parseable as a date.
     *
     * @param parsedDeadlineDate Parsed and formatted deadline, or empty when parsing fails
     * @return Reminder message or empty string
     */
    private String buildReminder(String parsedDeadlineDate) {
        return parsedDeadlineDate.isEmpty() ? StringFormat.DEADLINE_REMINDER_STRING : "";
    }

    /**
     * Saves the new deadline task and returns the user-facing success message.
     *
     * @param taskHandler Parser that owns task state and storage
     * @param taskName Final task name
     * @param taskDeadline Final task deadline
     * @param reminder Optional reminder message
     * @return Success message for UI output
     * @throws IOException If persisting task data fails
     */
    private String saveDeadlineAndBuildMessage(Parser taskHandler, String taskName,
            String taskDeadline, String reminder) throws IOException {
        assert !taskName.isEmpty() : "taskName must not be empty";
        ArrayList<Task> tasks = taskHandler.getTasks();
        Deadline deadline = new Deadline(taskName, TaskType.DEADLINE,
                TaskStatus.INCOMPLETE, taskDeadline);
        tasks.add(deadline);
        taskHandler.getStorage().saveData(tasks);
        return ("Added Deadline @ index " + tasks.size() + ":\n" + deadline + "\n"
                + reminder).trim();
    }
}
