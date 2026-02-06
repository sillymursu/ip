package legoat.handlers;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import legoat.legoatui.Ui;
import legoat.tasktypes.Deadline;
import legoat.tasktypes.Event;
import legoat.tasktypes.Task;

/**
* TaskHandler handles all task related events.
*
* @author Russell Lin
*/
public class TaskHandler {
    private final ArrayList<Task> tasks;
    private final Ui format;
    private DataHandler dataHandler;

    /**
    * <p>Constructor for TaskHandler objects. Initializes a list of tasks.
    * @since v0.1
    */
    public TaskHandler() {
        this.tasks = new ArrayList<>();
        this.format = new Ui();
    }

    /**
    * <p>Sets this TaskHandler object's dataHandler field to specified
    * instance of DataHandler.
    * @param dataHandler Instance of DataHandler class
    * @since v0.1
    */
    public void setDataHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
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
    * <p>Marks/Unmarks a task based off input.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @throws NumberFormatException
    * @throws IndexOutOfBoundsException
    * @since v0.1
    */
    public void markUnmark(String[] input) {
        try {
            int lineNum = Integer.parseInt(input[1]) - 1;
            Task t = getTasks().get(lineNum);
            if (input[0].equals("mark")) {
                if (t.getTaskStatus().equals("X")) {
                    System.err.println(format.longLineString + "\n\n" + format.leGoatString
                            + "Time Paradox? Task is already done!" + "\n\n" + format.longLineString);
                    return;
                } else {
                    t.mark();
                    System.out.println(format.longLineString + "\n\n" + format.leGoatString
                            + "Easy work. Task completed!");
                }
            } else {
                if (t.getTaskStatus().equals(" ")) {
                    System.err.println(format.longLineString + "\n\n" + format.leGoatString
                            + "Time Paradox? Task is not yet done!" + "\n\n" + format.longLineString);
                    return;
                } else {
                    t.unmark();
                    System.out.println(format.longLineString + "\n\n" + format.leGoatString
                            + "Electric Boogaloo. Task uncompleted!");
                }
            }
            String taskType = t.getTaskType();
            switch (taskType) {
            case "D" -> {
                Deadline d = (Deadline) getTasks().get(lineNum);
                System.out.println("   " + d.toString());
                this.dataHandler.saveData(this.getTasks());
                System.out.println("\n" + format.longLineString);
                break;
            }
            case "E" -> {
                Event e = (Event) getTasks().get(lineNum);
                System.out.println("   " + e.toString());
                this.dataHandler.saveData(this.getTasks());
                System.out.println("\n" + format.longLineString);
                break;
            }
            default -> {
                System.out.println("   " + t.toString());
                this.dataHandler.saveData(this.getTasks());
                System.out.println("\n" + format.longLineString);
            }
            }
        } catch (NumberFormatException e) {
            System.err.println(format.longLineString + "\n\n" + format.leGoatString
                    + "Second Argument is not a number!!" + "\n\n" + format.longLineString);
        } catch (IndexOutOfBoundsException e) {
            System.err.println(format.longLineString + "\n\n" + format.leGoatString
                    + "Second Argument is not a valid number!!" + "\n\n" + format.longLineString);
        }
    }

    /**
    * <p>Adds a Task object to the list of tasks maintained by TaskHandler instance.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @since v0.1
    */
    public void addToDo(String[] input) {
        StringBuilder tdName = new StringBuilder();
        for (int i = 1; i < input.length; i++) {
            tdName.append(" ");
            tdName.append(input[i]);
        }
        String taskName = tdName.toString().replaceFirst(" ", "");
        if (taskName.isEmpty()) {
            System.err.println(format.longLineString + "\n\n" + format.leGoatString
                    + "The correct format is: \"todo <eventName>\"!" + "\n\n" + format.longLineString);
        } else {
            Task t = new Task(taskName, "T", " ");
            getTasks().add(t);
            System.out.println(format.longLineString + "\n\n" + "Added Task:\n   " + t.toString());
            this.dataHandler.saveData(this.getTasks());
            System.out.println("\n" + format.longLineString);
        }
    }

    /**
    * <p>Adds a Deadline object to the list of tasks maintained by TaskHandler instance.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @since v0.1
    */
    public void addDeadline(String[] input) {
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
        String taskName = dName.toString().replaceFirst(" ", "");
        String taskDeadline = dDate.toString().replaceFirst(" ", "");
        String taskDeadlineDate = format.parseDate(taskDeadline);
        if (taskName.isEmpty() || taskDeadline.isEmpty()) {
            System.err.println(format.longLineString + "\n\n" + format.leGoatString
                    + "The correct format is: \"deadline <eventName> /by <deadline>\"!"
                    + "\n\n" + format.longLineString);
        } else {
            if (!taskDeadlineDate.isEmpty()) {
                taskDeadline = taskDeadlineDate;
            }
            Deadline d = new Deadline(taskName, "D", " ", taskDeadline);
            getTasks().add(d);
            System.out.println(format.longLineString + "\n\n" + "Added Deadline:\n   " + d.toString());
            if (taskDeadlineDate.equals("")) {
                System.out.println(format.deadlineFormatReminderString);
            }
            this.dataHandler.saveData(this.getTasks());
            System.out.println("\n" + format.longLineString);
        }
    }

    /**
    * <p>Adds a Event object to the list of tasks maintained by TaskHandler instance.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @since v0.1
    */
    public void addEvent(String[] input) {
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
        String taskName = eName.toString().replaceFirst(" ", "");
        String taskBegin = eFrom.toString().replaceFirst(" ", "");
        String taskEnd = eTo.toString().replaceFirst(" ", "");
        String taskBeginDate = format.parseDate(taskBegin);
        String taskEndDate = format.parseDate(taskEnd);
        if (taskName.isEmpty() || taskBegin.isEmpty() || taskEnd.isEmpty()) {
            System.err.println(format.longLineString + "\n\n" + format.leGoatString
                    + "The correct format is: \"event <eventName> /from <begin> /to <end>\"!"
                    + "\n\n" + format.longLineString);
        } else {
            if (!taskBeginDate.isEmpty()) {
                taskBegin = taskBeginDate;
            }
            if (!taskEndDate.isEmpty()) {
                taskEnd = taskEndDate;
            }
            Event e = new Event(taskName, "E", " ", taskBegin, taskEnd);
            getTasks().add(e);
            System.out.println(format.longLineString + "\n\n" + "Added Event:\n   " + e.toString());
            if (taskBeginDate.equals("") || taskEndDate.equals("")) {
                System.out.println(format.eventFormatReminderString);
            }
            this.dataHandler.saveData(this.getTasks());
            System.out.println("\n" + format.longLineString);
        }
    }

    /**
    * <p>Deletes a task from the list of tasks maintained by TaskHandler instance based on index [1-n]
    *  of the specified task in the list.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @since v0.1
    */
    public void deleteTask(String[] input) {
        try {
            int taskIndexToRemove = Integer.parseInt(input[1]) - 1;
            getTasks().remove(taskIndexToRemove);
            if (getTasks().size() == 1) {
                System.out.println(format.longLineString + "\n\n" + format.leGoatString
                        + "Task deleted!!" + "\n" + format.leGoatString + "You have "
                        + getTasks().size() + " Task left!!");
            } else {
                System.out.println(format.longLineString + "\n\n" + format.leGoatString
                        + "Task deleted!!" + "\n" + format.leGoatString + "You have "
                        + getTasks().size() + " Tasks left!!");
            }
            this.dataHandler.saveData(this.getTasks());
            System.out.println("\n" + format.longLineString);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.err.println(format.longLineString + "\n\n" + format.leGoatString
                    + "The correct format is: \"delete < valid line item number>\"!"
                    + "\n\n" + format.longLineString);
        }
    }

    /**
    * <p>Finds tasks from the list of tasks maintained by TaskHandler based on a
    * singular matching keyword in task names.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @since v0.1
    */
    public void find(String[] input) {
        if (input.length > 2) {
            System.err.println(format.longLineString + "\n\n" + format.leGoatString
                    + "You can only find (1) keyword!" + "\n\n" + format.longLineString);
        } else {
            int tasksFound = 0;
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
            try {
                System.out.println(format.longLineString + "\n");
                for (Task t : getTasks()) {
                    String taskName = t.getTaskName();
                    if (taskName.contains(input[1])) {
                        tasksFound++;
                        String taskType = t.getTaskType();
                        switch (taskType) {
                        case "D" -> {
                            Deadline d = (Deadline) t;
                            bw.write("   " + d.toString());
                            bw.newLine();
                        }
                        case "E" -> {
                            Event e = (Event) t;
                            bw.write("   " + e.toString());
                            bw.newLine();
                        }
                        default -> {
                            bw.write("   " + t.toString());
                            bw.newLine();
                        }
                        }
                    }
                }
                if (tasksFound != 0) {
                    if (tasksFound > 1) {
                        System.out.println(format.leGoatString + "I found "
                                + tasksFound + " tasks:");
                        bw.flush();
                    } else {
                        System.out.println(format.leGoatString + "I found 1 task:");
                        bw.flush();
                    }
                } else {
                    System.err.println(format.leGoatString + "Uh oh! No tasks with keyword "
                            + input[1] + " were found!");
                }
                System.out.println("\n" + format.longLineString);
            } catch (IOException e) {
                System.err.println(format.leGoatString + "Uh oh! IOException!"
                        + "\n" + format.longLineString);
            }
        }
    }
}
