package legoat.commands;

import java.util.ArrayList;

import legoat.exceptions.WrongFormatFindException;
import legoat.tasktypes.Task;
import legoat.tasktypes.TaskType;

/**
 * FindCommand handles finding tasks by keyword.
 */
public class FindCommand implements Command {
    @Override
    public String execute(String[] input, Parser taskHandler) throws WrongFormatFindException {
        StringBuilder sb = new StringBuilder();
        if (input.length != 2) {
            throw new WrongFormatFindException();
        }

        ArrayList<Task> tasks = taskHandler.getTasks();
        int tasksFound = 0;
        for (Task task : tasks) {
            String taskName = task.getTaskName();
            if (taskName.contains(input[1])) {
                tasksFound++;
                TaskType taskType = task.getTaskType();
                switch (taskType) {
                case DEADLINE, EVENT -> {
                    sb.append(taskHandler.getTaskBasedOnType(task).toString());
                    sb.append("\n");
                }
                default -> {
                    sb.append(task);
                    sb.append("\n");
                }
                }
            }
        }

        if (tasksFound != 0) {
            if (tasksFound > 1) {
                return "I found " + tasksFound + " tasks:\n" + sb.toString().trim();
            }
            return "I found 1 task:\n" + sb.toString().trim();
        }

        return "Uh oh! No tasks with keyword " + input[1] + " were found!";
    }
}
