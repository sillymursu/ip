package legoat.handlers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import legoat.tasktypes.Deadline;
import legoat.tasktypes.Event;
import legoat.tasktypes.Task;
import legoat.ui.StringFormat;

/**
* TaskHandler handles all task related events.
*
* @author Russell Lin
*/
public class TaskHandler {
    private final ArrayList<Task> tasks = new ArrayList<>();
    private final DataHandler dataHandler = new DataHandler();

    /**
    * <p>Constructor for TaskHandler.
    * Loads data of tasks on instance creation.
    * @since v0.2
    */
    public TaskHandler() throws FileNotFoundException {
        this.loadTaskData();
    }

    /**
    * <p>Loads task data from "data\LeGoatData.txt" if applicable.
    * @since v0.2
    */
    private void loadTaskData() throws FileNotFoundException {
        dataHandler.loadData(this);
    }

    /**
    * <p>Getter method that returns a list of tasks.
    * @return ArrayList of tasks
    * @since v0.1
    */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    /**
    * <p>Function called when user input is "list", prints a list of added tasks unless
    *  current list of tasks is empty.
    * @return A list of current tasks as a String
    * @since v0.2
    */
    public String list() {
        if (tasks.isEmpty()) {
            String output = StringFormat.LEGOAT_STRING + "The list is currently empty! Add some Tasks first!!";
            return output;
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < tasks.size(); i++) {
                int lineNumber = i + 1;
                String taskType = tasks.get(i).getTaskType();
                switch (taskType) {
                case "D" -> {
                    Deadline d = (Deadline) tasks.get(i);
                    String s = lineNumber + ". " + d.toString() + "\n";
                    sb.append(s);
                }
                case "E" -> {
                    Event e = (Event) tasks.get(i);
                    String s = lineNumber + ". " + e.toString();
                    sb.append(s);
                }
                default -> {
                    Task t = tasks.get(i);
                    String s = lineNumber + ". " + t.toString();
                    sb.append(s);
                }
                }
            }
            String output = sb.toString().trim();
            return output;
        }
    }

    /**
    * <p>Marks/Unmarks a task based off input.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @return String of the task marked/unmarked
    * @throws NumberFormatException
    * @throws IndexOutOfBoundsException
    * @since v0.2
    */
    public String markUnmark(String[] input) throws NumberFormatException,
                IndexOutOfBoundsException {
        try {
            int lineNum = Integer.parseInt(input[1]) - 1;
            Task t = tasks.get(lineNum);
            if (input[0].equals("mark")) {
                if (t.getTaskStatus().equals("X")) {
                    return StringFormat.LEGOAT_STRING + "Time Paradox? Task is already done!";
                } else {
                    t.mark();
                    return StringFormat.LEGOAT_STRING + "Easy work. Task completed!\n"
                            + "   " + t.toString();
                }
            } else {
                if (t.getTaskStatus().equals("  ")) {
                    return StringFormat.LEGOAT_STRING + "Time Paradox? Task is not yet done!";
                } else {
                    t.unmark();
                    return StringFormat.LEGOAT_STRING + "Electric Boogaloo. Task uncompleted!\n"
                            + "   " + t.toString();
                }
            }
        } catch (NumberFormatException e) {
            return StringFormat.LEGOAT_STRING + "Second Argument is not a number!!";
        } catch (IndexOutOfBoundsException e) {
            return StringFormat.LEGOAT_STRING + "Second Argument is not a valid number!!";
        }
    }

    /**
    * <p>Adds a Task object to the list of tasks maintained by TaskHandler instance.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @since v0.2
    */
    public String addToDo(String[] input) throws IOException {
        StringBuilder tdName = new StringBuilder();
        for (int i = 1; i < input.length; i++) {
            tdName.append(" ");
            tdName.append(input[i]);
        }
        String taskName = tdName.toString().trim();
        if (taskName.isEmpty()) {
            return StringFormat.LEGOAT_STRING
                    + "The correct format is: \"todo <eventName>\"!";
        } else {
            Task t = new Task(taskName, "T", "  ");
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
    public String addDeadline(String[] input) throws IOException {
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
            return StringFormat.LEGOAT_STRING
                    + "The correct format is: \"deadline <eventName> /by <deadline>\"!";
        } else {
            String reminder = "";
            if (!taskDeadlineDate.isEmpty()) {
                taskDeadline = taskDeadlineDate;
            } else {
                reminder = StringFormat.DEADLINE_REMINDER_STRING;
            }
            Deadline d = new Deadline(taskName, "D", "  ", taskDeadline);
            tasks.add(d);
            this.dataHandler.saveData(tasks);
            String output = "Added Deadline @ index " + tasks.size() + ":\n" + d.toString() + "\n" + reminder;
            return output.trim();
        }
    }

    /**
    * <p>Adds a Event object to the list of tasks maintained by TaskHandler instance.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @since v0.1
    */
    public String addEvent(String[] input) throws IOException {
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
            return StringFormat.LEGOAT_STRING
                    + "The correct format is: \"event <eventName> /from <begin> /to <end>\"!";
        } else {
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
            Event e = new Event(taskName, "E", "  ", taskBegin, taskEnd);
            tasks.add(e);
            dataHandler.saveData(tasks);
            String output = "Added Event @ index " + tasks.size() + ":\n" + e.toString() + "\n" + reminder;
            return output.trim();
        }
    }

    /**
    * <p>Deletes a task from the list of tasks maintained by TaskHandler instance based on index [1-n]
    *  of the specified task in the list.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @since v0.1
    */
    public String deleteTask(String[] input) throws IOException {
        try {
            int taskIndexToRemove = Integer.parseInt(input[1]) - 1;
            tasks.remove(taskIndexToRemove);
            dataHandler.saveData(tasks);
            if (tasks.size() == 1) {
                return StringFormat.LEGOAT_STRING
                        + "Task deleted!!" + "\n" + StringFormat.LEGOAT_STRING + "You have "
                        + getTasks().size() + " Task left!!";
            } else {
                return StringFormat.LEGOAT_STRING
                        + "Task deleted!!" + "\n" + StringFormat.LEGOAT_STRING + "You have "
                        + getTasks().size() + " Tasks left!!";
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return StringFormat.LEGOAT_STRING
                    + "The correct format is: \"delete < valid line item number>\"!";
        }
    }

    /**
    * <p>Finds tasks from the list of tasks maintained by TaskHandler based on a
    * singular matching keyword in task names.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @since v0.1
    */
    public String find(String[] input) {
        StringBuilder sb = new StringBuilder();
        if (input.length > 2) {
            return StringFormat.LEGOAT_STRING
                    + "You can only find (1) keyword!";
        } else {
            int tasksFound = 0;
            for (Task t : getTasks()) {
                String taskName = t.getTaskName();
                if (taskName.contains(input[1])) {
                    tasksFound++;
                    String taskType = t.getTaskType();
                    switch (taskType) {
                    case "D" -> {
                        Deadline d = (Deadline) t;
                        sb.append(d.toString());
                        sb.append("\n");
                    }
                    case "E" -> {
                        Event e = (Event) t;
                        sb.append(e.toString());
                        sb.append("\n");
                    }
                    default -> {
                        sb.append(t.toString());
                        sb.append("\n");
                    }
                    }
                }
            }
            if (tasksFound != 0) {
                if (tasksFound > 1) {
                    return StringFormat.LEGOAT_STRING + "I found "
                            + tasksFound + " tasks:\n"
                            + sb.toString().trim();
                } else {
                    return StringFormat.LEGOAT_STRING + "I found 1 task:\n"
                            + sb.toString().trim();

                }
            } else {
                return StringFormat.LEGOAT_STRING + "Uh oh! No tasks with keyword "
                        + input[1] + " were found!";
            }
        }
    }
}
