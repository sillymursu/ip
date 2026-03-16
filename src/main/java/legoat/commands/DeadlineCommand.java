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
    @Override
    public String execute(String[] input, Parser taskHandler) throws IOException,
            WrongFormatDeadlineException {
        StringBuilder deadlineName = new StringBuilder();
        StringBuilder deadlineDate = new StringBuilder();

        int splitIndex = 1;
        for (int i = 1; i < input.length; i++) {
            String current = input[i];
            if (current.equals("/by")) {
                splitIndex++;
                break;
            }
            splitIndex++;
            deadlineName.append(" ");
            deadlineName.append(current);
        }

        for (int j = splitIndex; j < input.length; j++) {
            deadlineDate.append(" ");
            deadlineDate.append(input[j]);
        }

        String taskName = deadlineName.toString().trim();
        String taskDeadline = deadlineDate.toString().trim();
        String parsedDeadlineDate = StringFormat.parseDate(taskDeadline);

        if (taskName.isEmpty() || taskDeadline.isEmpty()) {
            throw new WrongFormatDeadlineException();
        }

        String reminder = "";
        if (!parsedDeadlineDate.isEmpty()) {
            taskDeadline = parsedDeadlineDate;
        } else {
            reminder = StringFormat.DEADLINE_REMINDER_STRING;
        }

        assert !taskName.isEmpty() : "taskName must not be empty";
        assert !taskDeadline.isEmpty() : "taskDeadline must not be empty";

        ArrayList<Task> tasks = taskHandler.getTasks();
        Deadline deadline = new Deadline(taskName, TaskType.DEADLINE, TaskStatus.INCOMPLETE, taskDeadline);
        tasks.add(deadline);
        taskHandler.getStorage().saveData(tasks);

        return ("Added Deadline @ index " + tasks.size() + ":\n" + deadline + "\n" + reminder).trim();
    }
}
