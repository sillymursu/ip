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
    @Override
    public String execute(String[] input, Parser taskHandler) throws IOException,
            EventTimeException, WrongFormatUpdateException {
        try {
            if (input.length < 4) {
                throw new WrongFormatUpdateException();
            }

            int updateTargetIdx = Integer.parseInt(input[1]) - 1;
            String updateField = input[2];

            ArrayList<Task> tasks = taskHandler.getTasks();
            Task task = taskHandler.getTaskBasedOnType(tasks.get(updateTargetIdx));
            switch (updateField) {
            case "name" -> {
                task.changeName(input);
                taskHandler.getStorage().saveData(tasks);
                return StringFormat.UPDATE_SUCCESS_STRING + "\n" + task;
            }
            case "deadline" -> {
                if (task instanceof Deadline deadline) {
                    deadline.changeDeadline(input);
                    taskHandler.getStorage().saveData(tasks);
                    return StringFormat.UPDATE_SUCCESS_STRING + "\n" + deadline;
                }
                throw new WrongFormatUpdateException();
            }
            case "event" -> {
                if (task instanceof Event event) {
                    String specificUpdateField = input[3];
                    if (specificUpdateField.equals("/from") || specificUpdateField.equals("/to")) {
                        event.changeEvent(input);
                        taskHandler.getStorage().saveData(tasks);
                        return StringFormat.UPDATE_SUCCESS_STRING + "\n" + event;
                    }
                }
                throw new WrongFormatUpdateException();
            }
            default -> throw new WrongFormatUpdateException();
            }
        } catch (NumberFormatException | IndexOutOfBoundsException | ClassCastException e) {
            throw new WrongFormatUpdateException();
        }
    }
}
