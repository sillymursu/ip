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
    @Override
    public String execute(String[] input, Parser taskHandler) throws IOException,
            WrongFormatEventException, EventTimeException {
        StringBuilder eventName = new StringBuilder();
        StringBuilder eventFrom = new StringBuilder();
        StringBuilder eventTo = new StringBuilder();

        int splitIndex = 1;
        for (int i = 1; i < input.length; i++) {
            String current = input[i];
            if (current.equals("/from")) {
                splitIndex++;
                break;
            }
            splitIndex++;
            eventName.append(" ");
            eventName.append(current);
        }

        for (int j = splitIndex; j < input.length; j++) {
            String current = input[j];
            if (current.equals("/to")) {
                splitIndex++;
                break;
            }
            splitIndex++;
            eventFrom.append(" ");
            eventFrom.append(current);
        }

        for (int y = splitIndex; y < input.length; y++) {
            eventTo.append(" ");
            eventTo.append(input[y]);
        }

        String taskName = eventName.toString().trim();
        String taskBegin = eventFrom.toString().trim();
        String taskEnd = eventTo.toString().trim();
        String parsedBeginDate = StringFormat.parseDate(taskBegin);
        String parsedEndDate = StringFormat.parseDate(taskEnd);

        if (taskName.isEmpty() || taskBegin.isEmpty() || taskEnd.isEmpty()) {
            throw new WrongFormatEventException();
        }

        if (!parsedBeginDate.isEmpty() && !parsedEndDate.isEmpty()) {
            if (StringFormat.eventDateValidation(taskBegin, taskEnd)) {
                throw new EventTimeException();
            }
        }

        String reminder = "";
        if (!parsedBeginDate.isEmpty()) {
            taskBegin = parsedBeginDate;
        }
        if (!parsedEndDate.isEmpty()) {
            taskEnd = parsedEndDate;
        }
        if (parsedBeginDate.isEmpty() || parsedEndDate.isEmpty()) {
            reminder = StringFormat.EVENT_REMINDER_STRING;
        }

        assert !taskName.isEmpty() : "taskName must not be empty";
        assert !taskBegin.isEmpty() : "taskBegin must not be empty";
        assert !taskEnd.isEmpty() : "taskEnd must not be empty";

        ArrayList<Task> tasks = taskHandler.getTasks();
        Event event = new Event(taskName, TaskType.EVENT, TaskStatus.INCOMPLETE, taskBegin, taskEnd);
        tasks.add(event);
        taskHandler.getStorage().saveData(tasks);

        return ("Added Event @ index " + tasks.size() + ":\n" + event + "\n" + reminder).trim();
    }
}
