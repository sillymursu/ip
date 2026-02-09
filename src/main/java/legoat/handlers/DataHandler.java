package legoat.handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import legoat.tasktypes.Deadline;
import legoat.tasktypes.Event;
import legoat.tasktypes.Task;
import legoat.ui.StringFormat;

/**
* DataHandler handles all data related events.
*
* @author Russell Lin
*/
public class DataHandler {
    private File savedPath;

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
                        writer.write(d.getTaskType() + " | " + d.getTaskStatus() + " | " + d.getTaskName()
                                + " | " + d.getDeadline() + "\n");
                    }
                    case "E" -> {
                        Event e = (Event) t;
                        writer.write(e.getTaskType() + " | " + e.getTaskStatus() + " | " + e.getTaskName()
                                + " | " + e.getBegin() + " | " + e.getEnd() + "\n");
                    }
                    default -> {
                        writer.write(t.getTaskType() + " | " + t.getTaskStatus() + " | " + t.getTaskName()
                                + "\n");
                    }
                    }
                }
                System.out.println("\nSaved successfully!!!");
            }
        } catch (IOException e) {
            System.err.println(StringFormat.LEGOAT_STRING + "Save FAILED!!!");
        }
    }

    /**
    * <p>Loads the list of tasks in data/LeGoatData.txt on LeGoat startup.
    * @since v0.1
    */
    public void loadData(TaskHandler taskHandler) {
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
                            taskHandler.getTasks().add(d);
                        }
                        case "E" -> {
                            Event e = new Event(lineItems[2], "E", lineItems[1], lineItems[3], lineItems[4]);
                            taskHandler.getTasks().add(e);
                        }
                        default -> {
                            Task t = new Task(lineItems[2], "T", lineItems[1]);
                            taskHandler.getTasks().add(t);
                        }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println(StringFormat.LEGOAT_STRING + "Load FAILED!!!");
        }
    }
}
