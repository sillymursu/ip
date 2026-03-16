package legoat.commands;

import java.io.IOException;
import java.util.ArrayList;

import legoat.exceptions.WrongFormatTodoException;
import legoat.tasktypes.Task;
import legoat.tasktypes.TaskStatus;
import legoat.tasktypes.TaskType;

/**
 * TodoCommand handles todo creation.
 */
public class TodoCommand implements Command {
    @Override
    public String execute(String[] input, Parser taskHandler) throws IOException,
            WrongFormatTodoException {
        StringBuilder todoName = new StringBuilder();
        for (int i = 1; i < input.length; i++) {
            todoName.append(" ");
            todoName.append(input[i]);
        }

        String taskName = todoName.toString().trim();
        if (taskName.isEmpty()) {
            throw new WrongFormatTodoException();
        }

        assert !taskName.isEmpty() : "taskName must not be empty";
        ArrayList<Task> tasks = taskHandler.getTasks();
        Task task = new Task(taskName, TaskType.TODO, TaskStatus.INCOMPLETE);
        tasks.add(task);
        taskHandler.getStorage().saveData(tasks);
        return "Added Task @ index " + tasks.size() + ":\n" + task;
    }
}
