package legoat.handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import legoat.legoatui.Ui;
import legoat.tasktypes.Deadline;
import legoat.tasktypes.Event;
import legoat.tasktypes.Task;

/**
* DataHandler handles all data related events.
*
* @author Russell Lin
*/
public class DataHandler {
    private File savedPath;
    private final Ui format;
    private final TaskHandler taskHandler;

    /**
    * <p>Constructor for DataHandler objects.
    * @param taskHandler Instance of class that handles all events related to Tasks.
    * @since v0.1
    */
    public DataHandler(TaskHandler taskHandler) {
        this.format = new Ui();
        this.taskHandler = taskHandler;
    }

    /**
    * <p>Saves the current list of tasks to data/LeGoatData.txt on any change to tasks.
    * @param taskList ArrayList of Tasks
    * @since v0.1
    */
    public void saveData(ArrayList<Task> taskList) {
        try {
            this.savedPath = new File("data/LeGoatData.txt");
            this.savedPath.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(savedPath)) {
                for (Task t : taskList) {
                    String taskType = t.getTaskType();
                    switch (taskType) {
                        case "D" -> {
                            Deadline d = (Deadline) t;
                            writer.write(d.getTaskType() + " | " + d.getTaskStatus() + " | " + d.getTaskName() +
                                    " | " + d.getDeadline() + "\n");
                        }
                        case "E" -> {
                            Event e = (Event) t;
                            writer.write(e.getTaskType() + " | " + e.getTaskStatus() + " | " + e.getTaskName() +
                                    " | " + e.getBegin() + " | " + e.getEnd() + "\n");
                        }
                        default -> {
                            writer.write(t.getTaskType() + " | " + t.getTaskStatus() + " | " + t.getTaskName() +
                                    "\n");
                        }
                    }
                }
                System.out.println("\nSaved successfully!!!");
            }
        } catch (IOException e) {
            System.err.println(format.LONG_LINE + "\n\n" + format.LEGOAT_STR + "Save FAILED!!!" +
                    "\n\n" + format.LONG_LINE);
        }
    }

    /**
    * <p>Loads the list of tasks in data/LeGoatData.txt on LeGoat startup.
    * @since v0.1
    */
    public void loadData() {
        try {
            this.savedPath = new File("data/LeGoatData.txt");
            if (!savedPath.exists()) {
            } else {
                try (Scanner dataReader = new Scanner(this.savedPath)) {
                    while (dataReader.hasNextLine()) {
                        String[] lineItems = dataReader.nextLine().split(" \\| ");
                        String type = lineItems[0];
                        switch (type) {
                            case "D" -> {
                                Deadline d = new Deadline(lineItems[2], "D", lineItems[1], lineItems[3]);
                                this.taskHandler.getTasks().add(d);
                            }
                            case "E" -> {
                                Event e = new Event(lineItems[2], "E", lineItems[1], lineItems[3], lineItems[4]);
                                this.taskHandler.getTasks().add(e);
                            }
                            case "T" -> {
                                Task t = new Task(lineItems[2], "T", lineItems[1]);
                                this.taskHandler.getTasks().add(t);
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println(format.LONG_LINE + "\n\n" + format.LEGOAT_STR +
                    "Load FAILED!!!" + "\n\n" + format.LONG_LINE);
        }
    }
}
