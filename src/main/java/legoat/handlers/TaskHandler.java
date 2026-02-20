package legoat.handlers;

import java.io.IOException;
import java.util.ArrayList;

import legoat.exceptions.DoubleCompletionException;
import legoat.exceptions.DoubleIncompleteException;
import legoat.exceptions.EmptyListException;
import legoat.exceptions.EventTimeException;
import legoat.exceptions.WrongFormatDeadlineException;
import legoat.exceptions.WrongFormatDeleteException;
import legoat.exceptions.WrongFormatEventException;
import legoat.exceptions.WrongFormatFindException;
import legoat.exceptions.WrongFormatTodoException;
import legoat.exceptions.WrongFormatUpdateException;
import legoat.tasktypes.Deadline;
import legoat.tasktypes.Event;
import legoat.tasktypes.Task;
import legoat.tasktypes.TaskStatus;
import legoat.tasktypes.TaskType;
import legoat.ui.StringFormat;

/**
* TaskHandler handles all task related events.
*
* @author Russell Lin
*/
public class TaskHandler {
    final ArrayList<Task> tasks = new ArrayList<>();
    private final DataHandler dataHandler = new DataHandler();

    /**
    * <p>Constructor for TaskHandler.
    * Loads data of tasks on instance creation.
    * @since v0.2
    */
    public TaskHandler() {
        this.loadTaskData();
    }

    /**
    * <p>Loads task data from "data\LeGoatData.txt" if applicable.
    * @since v0.2
    */
    private void loadTaskData() {
        dataHandler.loadData(this);
    }

    /**
    * <p>Function called when user input is "list", prints a list of added tasks unless
    *  current list of tasks is empty.
    * @return A list of current tasks as a String
    * @since v0.2
    */
    public String list() throws EmptyListException {
        if (tasks.isEmpty()) {
            throw new EmptyListException();
        }
        StringBuilder sb = new StringBuilder();
        assert !tasks.isEmpty() : "List of tasks must not be empty";
        int lineNumber = 0;
        for (Task t : tasks) {
            lineNumber++;
            String s = lineNumber + ". " + getTaskBasedOnType(t).toString() + "\n";
            sb.append(s);
        }
        return sb.toString().trim();
    }

    /**
    * <p>Marks/Unmarks a task based off input.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @since v0.2
    */
    public String markUnmark(String[] input) throws IOException,
                DoubleCompletionException, DoubleIncompleteException,
                WrongFormatDeleteException {
        try {
            int lineNum = Integer.parseInt(input[1]) - 1;
            assert lineNum >= 0 : "Line number must be positive";
            assert lineNum < tasks.size() : "Line number must not be out of bounds";
            Task t = tasks.get(lineNum);
            if (input[0].equals("mark")) {
                if (t.getTaskStatus() == TaskStatus.COMPLETE) {
                    throw new DoubleCompletionException();
                } else {
                    t.mark();
                    dataHandler.saveData(tasks);
                    return "Easy work. Task completed!\n"
                            + t.toString();
                }
            } else {
                if (t.getTaskStatus() == TaskStatus.INCOMPLETE) {
                    throw new DoubleIncompleteException();
                } else {
                    t.unmark();
                    dataHandler.saveData(tasks);
                    return "Electric Boogaloo. Task uncompleted!\n"
                            + t.toString();
                }
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new WrongFormatDeleteException();
        }
    }

    /**
    * <p>Adds a Task object to the list of tasks maintained by TaskHandler instance.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @since v0.2
    */
    public String addToDo(String[] input) throws IOException,
            WrongFormatTodoException {
        StringBuilder tdName = new StringBuilder();
        for (int i = 1; i < input.length; i++) {
            tdName.append(" ");
            tdName.append(input[i]);
        }
        String taskName = tdName.toString().trim();
        if (taskName.isEmpty()) {
            throw new WrongFormatTodoException();
        } else {
            assert !taskName.isEmpty() : "taskName must not be empty";
            Task t = new Task(taskName, TaskType.TODO, TaskStatus.INCOMPLETE);
            tasks.add(t);
            dataHandler.saveData(tasks);
            return "Added Task @ index " + tasks.size() + ":\n" + t.toString();
        }
    }

    /**
    * <p>Adds a Deadline object to the list of tasks maintained by TaskHandler instance.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @since v0.1
    */
    public String addDeadline(String[] input) throws IOException,
            WrongFormatDeadlineException {
        StringBuilder dName = new StringBuilder();
        StringBuilder dDate = new StringBuilder();
        int b = 1;
        for (int i = 1; i < input.length; i++) {
            String deadlineDate = input[i];
            if (deadlineDate.equals("/by")) {
                b++;
                break;
            } else {
                b++;
                dName.append(" ");
                dName.append(input[i]);
            }
        }
        for (int j = b; j < input.length; j++) {
            dDate.append(" ");
            dDate.append(input[j]);
        }
        String taskName = dName.toString().trim();
        String taskDeadline = dDate.toString().trim();
        String taskDeadlineDate = StringFormat.parseDate(taskDeadline);
        if (taskName.isEmpty() || taskDeadline.isEmpty()) {
            throw new WrongFormatDeadlineException();
        } else {
            String reminder = "";
            if (!taskDeadlineDate.isEmpty()) {
                taskDeadline = taskDeadlineDate;
            } else {
                reminder = StringFormat.DEADLINE_REMINDER_STRING;
            }
            assert !taskName.isEmpty() : "taskName must not be empty";
            assert !taskDeadline.isEmpty() : "taskDeadline must not be empty";
            Deadline d = new Deadline(taskName, TaskType.DEADLINE, TaskStatus.INCOMPLETE, taskDeadline);
            tasks.add(d);
            this.dataHandler.saveData(tasks);
            return ("Added Deadline @ index " + tasks.size() + ":\n" + d.toString() + "\n" + reminder).trim();
        }
    }

    /**
    * <p>Adds a Event object to the list of tasks maintained by TaskHandler instance.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @since v0.1
    */
    public String addEvent(String[] input) throws IOException,
            WrongFormatEventException, EventTimeException {
        StringBuilder eName = new StringBuilder();
        StringBuilder eFrom = new StringBuilder();
        StringBuilder eTo = new StringBuilder();
        int b = 1;
        for (int i = 1; i < input.length; i++) {
            String deadlineDate = input[i];
            if (deadlineDate.equals("/from")) {
                b++;
                break;
            } else {
                b++;
                eName.append(" ");
                eName.append(input[i]);
            }
        }
        for (int j = b; j < input.length; j++) {
            String deadlineDate = input[j];
            if (deadlineDate.equals("/to")) {
                b++;
                break;
            } else {
                b++;
                eFrom.append(" ");
                eFrom.append(input[j]);
            }
        }
        for (int y = b; y < input.length; y++) {
            eTo.append(" ");
            eTo.append(input[y]);
        }
        String taskName = eName.toString().trim();
        String taskBegin = eFrom.toString().trim();
        String taskEnd = eTo.toString().trim();
        String taskBeginDate = StringFormat.parseDate(taskBegin);
        String taskEndDate = StringFormat.parseDate(taskEnd);
        if (taskName.isEmpty() || taskBegin.isEmpty() || taskEnd.isEmpty()) {
            throw new WrongFormatEventException();
        } else {
            if (!taskBeginDate.isEmpty() && !taskEndDate.isEmpty()) {
                if (StringFormat.eventDateValidation(taskBegin, taskEnd)) {
                    throw new EventTimeException();
                }
            }
            String reminder = "";
            if (!taskBeginDate.isEmpty()) {
                taskBegin = taskBeginDate;
            }
            if (!taskEndDate.isEmpty()) {
                taskEnd = taskEndDate;
            }
            if (taskBeginDate.equals("") || taskEndDate.equals("")) {
                reminder = StringFormat.EVENT_REMINDER_STRING;
            }
            assert !taskName.isEmpty() : "taskName must not be empty";
            assert !taskBegin.isEmpty() : "taskBegin must not be empty";
            assert !taskEnd.isEmpty() : "taskEnd must not be empty";
            Event e = new Event(taskName, TaskType.EVENT, TaskStatus.INCOMPLETE, taskBegin, taskEnd);
            tasks.add(e);
            dataHandler.saveData(tasks);
            return ("Added Event @ index " + tasks.size() + ":\n" + e.toString() + "\n" + reminder).trim();
        }
    }

    /**
    * <p>Deletes a task from the list of tasks maintained by TaskHandler instance based on index [1-n]
    *  of the specified task in the list.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @since v0.1
    */
    public String deleteTask(String[] input) throws IOException,
            WrongFormatDeleteException {
        try {
            int taskIndexToRemove = Integer.parseInt(input[1]) - 1;
            tasks.remove(taskIndexToRemove);
            dataHandler.saveData(tasks);
            if (tasks.size() == 1) {
                return "Task deleted!!\nYou have " + tasks.size() + " Task left!!";
            } else {
                return "Task deleted!!\nYou have " + tasks.size() + " Tasks left!!";
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new WrongFormatDeleteException();
        }
    }

    /**
    * <p>Finds tasks from the list of tasks maintained by TaskHandler based on a
    * singular matching keyword in task names.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @since v0.1
    */
    public String find(String[] input) throws WrongFormatFindException {
        StringBuilder sb = new StringBuilder();
        if (input.length > 2) {
            throw new WrongFormatFindException();
        } else {
            int tasksFound = 0;
            for (Task t : tasks) {
                String taskName = t.getTaskName();
                if (taskName.contains(input[1])) {
                    tasksFound++;
                    TaskType taskType = t.getTaskType();
                    switch (taskType) {
                    case DEADLINE -> {
                        Deadline d = (Deadline) t;
                        sb.append(d.toString());
                        sb.append("\n");
                    }
                    case EVENT -> {
                        Event e = (Event) t;
                        sb.append(e.toString());
                        sb.append("\n");
                    }
                    default -> {
                        sb.append(t.toString());
                        sb.append("\n");
                    }
                    }
                    sb.append(getTaskBasedOnType(t).toString());
                    sb.append("\n");
                }
            }
            if (tasksFound != 0) {
                if (tasksFound > 1) {
                    return "I found "
                            + tasksFound + " tasks:\n"
                            + sb.toString().trim();
                } else {
                    return "I found 1 task:\n"
                            + sb.toString().trim();
                }
            } else {
                return "Uh oh! No tasks with keyword "
                        + input[1] + " were found!";
            }
        }
    }

    /**
    * <p>Gets correctly typed Todos, Events, Deadlines from the list of tasks.
    * @param t Selected Task
    * @return Selected task
    * @since v0.2
    */
    public Task getTaskBasedOnType(Task t) {
        TaskType taskType = t.getTaskType();
        switch (taskType) {
        case DEADLINE -> {
            Deadline d = (Deadline) t;
            return d;
        }
        case EVENT -> {
            Event e = (Event) t;
            return e;
        }
        default -> {
            return t;
        }
        }
    }

    /**
    * <p>Updates specified Todos, Events, Deadlines from the list of tasks.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @return String representation of whether or not the update was successful or ran into problems
    * @since v0.3
    */
    public String update(String[] input) throws IOException,
            EventTimeException, WrongFormatUpdateException {
        try {
            if (input.length < 4) {
                throw new WrongFormatUpdateException();
            }
            int updateTargetIdx = Integer.parseInt(input[1]) - 1;
            String updateField = input[2];
            Task t = getTaskBasedOnType(tasks.get(updateTargetIdx));
            switch (updateField) {
            case "name" -> {
                t.changeName(input);
                dataHandler.saveData(tasks);
                return StringFormat.UPDATE_SUCCESS_STRING;
            }
            case "deadline" -> {
                if (t instanceof Deadline d) {
                    d.changeDeadline(input);
                    dataHandler.saveData(tasks);
                    return StringFormat.UPDATE_SUCCESS_STRING;
                } else {
                    throw new WrongFormatUpdateException();
                }
            }
            case "event" -> {
                if (t instanceof Event e) {
                    String specificUpdateField = input[3];
                    if (specificUpdateField.equals("/from") || specificUpdateField.equals("/to")) {
                        e.changeEvent(input);
                        dataHandler.saveData(tasks);
                        return StringFormat.UPDATE_SUCCESS_STRING;
                    } else {
                        throw new WrongFormatUpdateException();
                    }
                } else {
                    throw new WrongFormatUpdateException();
                }
            }
            default -> {
                throw new WrongFormatUpdateException();
            }
            }
        } catch (NumberFormatException | IndexOutOfBoundsException | ClassCastException e) {
            throw new WrongFormatUpdateException();
        }
    }
}
