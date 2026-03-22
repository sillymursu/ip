package legoat.commands;

import java.io.IOException;
import java.util.ArrayList;

import legoat.exceptions.EventTimeException;
import legoat.exceptions.WrongFormatEventException;
import legoat.tasktypes.Event;
import legoat.tasktypes.Task;
import legoat.tasktypes.TaskStatus;
import legoat.tasktypes.TaskType;
import legoat.ui.StringFormat;

/**
 * EventCommand handles event creation.
 */
public class EventCommand implements Command {
    /**
     * Creates and persists an event task from parsed user input.
     *
     * @param input User command tokens
     * @param taskHandler Parser that owns task state and storage
     * @return Success message for the created event
     * @throws IOException If persisting task data fails
     * @throws WrongFormatEventException If required event fields are missing
     * @throws EventTimeException If event start time is after end time
     */
    @Override
    public String execute(String[] input, Parser taskHandler) throws IOException,
            WrongFormatEventException, EventTimeException {
        String taskName = extractTaskName(input);
        String rawBegin = extractRawBegin(input);
        String rawEnd = extractRawEnd(input);
        validateRequiredFields(taskName, rawBegin, rawEnd);

        String parsedBeginDate = StringFormat.parseDate(rawBegin);
        String parsedEndDate = StringFormat.parseDate(rawEnd);
        validateEventTime(rawBegin, rawEnd, parsedBeginDate, parsedEndDate);

        String taskBegin = resolveTaskBegin(rawBegin, parsedBeginDate);
        String taskEnd = resolveTaskEnd(rawEnd, parsedEndDate);
        String reminder = buildReminder(parsedBeginDate, parsedEndDate);

        return saveEventAndBuildMessage(taskHandler, taskName, taskBegin, taskEnd, reminder);
    }

    /**
     * Extracts event name from user input up to the {@code /from} delimiter.
     *
     * @param input User command tokens
     * @return Trimmed event name
     */
    private String extractTaskName(String[] input) {
        StringBuilder eventName = new StringBuilder();
        for (int i = 1; i < input.length; i++) {
            String current = input[i];
            if (current.equals("/from")) {
                break;
            }
            eventName.append(" ");
            eventName.append(current);
        }

        return eventName.toString().trim();
    }

    /**
     * Extracts raw event begin segment after {@code /from} and before {@code /to}.
     *
     * @param input User command tokens
     * @return Trimmed raw begin value
     */
    private String extractRawBegin(String[] input) {
        StringBuilder eventFrom = new StringBuilder();
        int fromIndex = findIndexAfterToken(input, "/from", 1);
        int toIndex = findIndexAfterToken(input, "/to", fromIndex);
        int endExclusive = (toIndex == input.length) ? input.length : (toIndex - 1);

        for (int i = fromIndex; i < endExclusive; i++) {
            eventFrom.append(" ");
            eventFrom.append(input[i]);
        }

        return eventFrom.toString().trim();
    }

    /**
     * Extracts raw event end segment after {@code /to}.
     *
     * @param input User command tokens
     * @return Trimmed raw end value
     */
    private String extractRawEnd(String[] input) {
        StringBuilder eventTo = new StringBuilder();
        int toIndex = findIndexAfterToken(input, "/to", 1);

        for (int i = toIndex; i < input.length; i++) {
            eventTo.append(" ");
            eventTo.append(input[i]);
        }

        return eventTo.toString().trim();
    }

    /**
     * Finds the index immediately after a delimiter token, starting from a position.
     *
     * @param input User command tokens
     * @param token Delimiter token to search
     * @param startIndex Index to start searching from
     * @return Index immediately after token, or {@code input.length} if token is absent
     */
    private int findIndexAfterToken(String[] input, String token, int startIndex) {
        int splitIndex = startIndex;
        for (int i = startIndex; i < input.length; i++) {
            splitIndex++;
            if (input[i].equals(token)) {
                break;
            }
        }
        return splitIndex;
    }

    /**
     * Validates that event name, begin, and end values are all present.
     *
     * @param taskName Extracted event name
     * @param taskBegin Extracted event begin value
     * @param taskEnd Extracted event end value
     * @throws WrongFormatEventException If any required field is empty
     */
    private void validateRequiredFields(String taskName, String taskBegin, String taskEnd)
            throws WrongFormatEventException {
        if (taskName.isEmpty() || taskBegin.isEmpty() || taskEnd.isEmpty()) {
            throw new WrongFormatEventException();
        }
    }

    /**
     * Validates event chronology when both begin and end values are parseable dates.
     *
     * @param taskBegin Raw begin value
     * @param taskEnd Raw end value
     * @param parsedBeginDate Parsed begin date, or empty if unparsable
     * @param parsedEndDate Parsed end date, or empty if unparsable
     * @throws EventTimeException If begin is after end
     */
    private void validateEventTime(String taskBegin, String taskEnd, String parsedBeginDate,
            String parsedEndDate) throws EventTimeException {
        if (!parsedBeginDate.isEmpty() && !parsedEndDate.isEmpty()
                && StringFormat.eventDateValidation(taskBegin, taskEnd)) {
            throw new EventTimeException();
        }
    }

    /**
     * Resolves final begin value, preferring parsed date when available.
     *
     * @param rawBegin Raw begin value
     * @param parsedBeginDate Parsed begin date, or empty if unparsable
     * @return Final begin value to store
     */
    private String resolveTaskBegin(String rawBegin, String parsedBeginDate) {
        String taskBegin = parsedBeginDate.isEmpty() ? rawBegin : parsedBeginDate;
        assert !taskBegin.isEmpty() : "taskBegin must not be empty";
        return taskBegin;
    }

    /**
     * Resolves final end value, preferring parsed date when available.
     *
     * @param rawEnd Raw end value
     * @param parsedEndDate Parsed end date, or empty if unparsable
     * @return Final end value to store
     */
    private String resolveTaskEnd(String rawEnd, String parsedEndDate) {
        String taskEnd = parsedEndDate.isEmpty() ? rawEnd : parsedEndDate;
        assert !taskEnd.isEmpty() : "taskEnd must not be empty";
        return taskEnd;
    }

    /**
     * Builds a formatting reminder when either event date cannot be parsed.
     *
     * @param parsedBeginDate Parsed begin date, or empty if unparsable
     * @param parsedEndDate Parsed end date, or empty if unparsable
     * @return Reminder message or empty string
     */
    private String buildReminder(String parsedBeginDate, String parsedEndDate) {
        return parsedBeginDate.isEmpty() || parsedEndDate.isEmpty()
                ? StringFormat.EVENT_REMINDER_STRING : "";
    }

    /**
     * Persists the event and returns the user-facing success message.
     *
     * @param taskHandler Parser that owns task state and storage
     * @param taskName Final event name
     * @param taskBegin Final event begin value
     * @param taskEnd Final event end value
     * @param reminder Optional reminder message
     * @return Success message for UI output
     * @throws IOException If persisting task data fails
     */
    private String saveEventAndBuildMessage(Parser taskHandler, String taskName,
            String taskBegin, String taskEnd, String reminder) throws IOException {
        assert !taskName.isEmpty() : "taskName must not be empty";
        ArrayList<Task> tasks = taskHandler.getTasks();
        Event event = new Event(taskName, TaskType.EVENT, TaskStatus.INCOMPLETE,
                taskBegin, taskEnd);
        tasks.add(event);
        taskHandler.getStorage().saveData(tasks);
        return ("Added Event @ index " + tasks.size() + ":\n" + event + "\n"
                + reminder).trim();
    }
}
