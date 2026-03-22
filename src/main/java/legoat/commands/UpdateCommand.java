package legoat.commands;

import java.io.IOException;
import java.util.ArrayList;

import legoat.exceptions.EventTimeException;
import legoat.exceptions.WrongFormatUpdateException;
import legoat.tasktypes.Deadline;
import legoat.tasktypes.Event;
import legoat.tasktypes.Task;
import legoat.ui.StringFormat;

/**
 * UpdateCommand handles task updates.
 */
public class UpdateCommand implements Command {
    /**
     * Updates a specific task field and persists the updated task list.
     *
     * @param input User command tokens
     * @param taskHandler Parser that owns task state and storage
     * @return User-facing update confirmation message
     * @throws IOException If persisting task data fails
     * @throws EventTimeException If updated event timing is invalid
     * @throws WrongFormatUpdateException If command format or target field is invalid
     */
    @Override
    public String execute(String[] input, Parser taskHandler) throws IOException,
            EventTimeException, WrongFormatUpdateException {
        try {
            validateInputLength(input);
            int updateTargetIdx = parseTargetIndex(input);
            String updateField = input[2];
            ArrayList<Task> tasks = taskHandler.getTasks();
            Task task = taskHandler.getTaskBasedOnType(tasks.get(updateTargetIdx));
            return handleUpdateByField(updateField, task, input, taskHandler, tasks);
        } catch (NumberFormatException | IndexOutOfBoundsException | ClassCastException e) {
            throw new WrongFormatUpdateException();
        }
    }

    /**
     * Validates minimum token count for update command.
     *
     * @param input User command tokens
     * @throws WrongFormatUpdateException If insufficient tokens are provided
     */
    private void validateInputLength(String[] input) throws WrongFormatUpdateException {
        if (input.length < 4) {
            throw new WrongFormatUpdateException();
        }
    }

    /**
     * Parses the target task index from user input.
     *
     * @param input User command tokens
     * @return 0-based task index
     */
    private int parseTargetIndex(String[] input) {
        return Integer.parseInt(input[1]) - 1;
    }

    /**
     * Routes update execution by target field.
     *
     * @param updateField Requested field to update
     * @param task Selected task
     * @param input User command tokens
     * @param taskHandler Parser that owns task state and storage
     * @param tasks Mutable task list
     * @return User-facing update confirmation message
     * @throws IOException If persisting task data fails
     * @throws EventTimeException If updated event timing is invalid
     * @throws WrongFormatUpdateException If field is invalid or incompatible with task type
     */
    private String handleUpdateByField(String updateField, Task task, String[] input,
            Parser taskHandler, ArrayList<Task> tasks)
            throws IOException, EventTimeException, WrongFormatUpdateException {
        return switch (updateField) {
        case "name" -> updateName(task, input, taskHandler, tasks);
        case "deadline" -> updateDeadline(task, input, taskHandler, tasks);
        case "event" -> updateEvent(task, input, taskHandler, tasks);
        default -> throw new WrongFormatUpdateException();
        };
    }

    /**
     * Updates the selected task name and persists changes.
     *
     * @param task Selected task
     * @param input User command tokens
     * @param taskHandler Parser that owns task state and storage
     * @param tasks Mutable task list
     * @return User-facing update confirmation message
     * @throws IOException If persisting task data fails
     */
    private String updateName(Task task, String[] input, Parser taskHandler, ArrayList<Task> tasks)
            throws IOException {
        task.changeName(input);
        taskHandler.getStorage().saveData(tasks);
        return StringFormat.UPDATE_SUCCESS_STRING + "\n" + task;
    }

    /**
     * Updates the selected deadline task and persists changes.
     *
     * @param task Selected task
     * @param input User command tokens
     * @param taskHandler Parser that owns task state and storage
     * @param tasks Mutable task list
     * @return User-facing update confirmation message
     * @throws IOException If persisting task data fails
     * @throws WrongFormatUpdateException If selected task is not a deadline
     */
    private String updateDeadline(Task task, String[] input, Parser taskHandler,
            ArrayList<Task> tasks) throws IOException, WrongFormatUpdateException {
        if (!(task instanceof Deadline deadline)) {
            throw new WrongFormatUpdateException();
        }
        deadline.changeDeadline(input);
        taskHandler.getStorage().saveData(tasks);
        return StringFormat.UPDATE_SUCCESS_STRING + "\n" + deadline;
    }

    /**
     * Updates the selected event task and persists changes.
     *
     * @param task Selected task
     * @param input User command tokens
     * @param taskHandler Parser that owns task state and storage
     * @param tasks Mutable task list
     * @return User-facing update confirmation message
     * @throws IOException If persisting task data fails
     * @throws EventTimeException If updated event timing is invalid
     * @throws WrongFormatUpdateException If selected task is not an event or field is invalid
     */
    private String updateEvent(Task task, String[] input, Parser taskHandler, ArrayList<Task> tasks)
            throws IOException, EventTimeException, WrongFormatUpdateException {
        if (!(task instanceof Event event)) {
            throw new WrongFormatUpdateException();
        }

        validateEventField(input[3]);
        event.changeEvent(input);
        taskHandler.getStorage().saveData(tasks);
        return StringFormat.UPDATE_SUCCESS_STRING + "\n" + event;
    }

    /**
     * Validates event subfield for update command.
     *
     * @param specificUpdateField Event field token
     * @throws WrongFormatUpdateException If field is neither {@code /from} nor {@code /to}
     */
    private void validateEventField(String specificUpdateField) throws WrongFormatUpdateException {
        if (!specificUpdateField.equals("/from") && !specificUpdateField.equals("/to")) {
            throw new WrongFormatUpdateException();
        }
    }
}
