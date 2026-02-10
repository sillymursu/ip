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
import legoat.tasktypes.TaskStatus;
import legoat.tasktypes.TaskType;
import legoat.ui.StringFormat;

/**
* DataHandler handles all data related events.
*
* @author Russell Lin
*/
public class DataHandler {
    private File savedPath = new File("data/LeGoatData.txt");

    /**
    * <p>Saves the current list of tasks to data/LeGoatData.txt on any change to tasks.
    * @param tasks ArrayList of Tasks
    * @since v0.1
    */
    public void saveData(ArrayList<Task> tasks) throws IOException {
        this.savedPath.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(savedPath)) {
            for (Task t : tasks) {
                TaskType taskType = t.getTaskType();
                switch (taskType) {
                case DEADLINE -> {
                    Deadline d = (Deadline) t;
                    writer.write(d.getTaskType().getCode() + " | "
                            + d.getTaskStatus().getSymbol() + " | "
                            + d.getTaskName() + " | "
                            + d.getDeadline() + "\n");
                }
                case EVENT -> {
                    Event e = (Event) t;
                    writer.write(e.getTaskType().getCode() + " | "
                            + e.getTaskStatus().getSymbol() + " | "
                            + e.getTaskName() + " | "
                            + e.getBegin() + " | "
                            + e.getEnd() + "\n");
                }
                default -> {
                    writer.write(t.getTaskType().getCode() + " | "
                            + t.getTaskStatus().getSymbol() + " | "
                            + t.getTaskName() + "\n");
                }
    public void saveData(ArrayList<Task> taskList) {
        try {
            this.savedPath = new File("data/LeGoatData.txt");
            this.savedPath.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(savedPath)) {
                assert !taskList.isEmpty() : "List of tasks must not be empty";
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
            }
            System.out.println("\nSaved successfully!!!");
        }
    }

    /**
    * <p>Loads the list of tasks in data/LeGoatData.txt on LeGoat startup.
    * @since v0.1
    */
    public void loadData(TaskHandler taskHandler) throws FileNotFoundException {
        try {
            this.savedPath = new File("data/LeGoatData.txt");
            if (savedPath.exists()) {
                try (Scanner dataReader = new Scanner(this.savedPath)) {
                    while (dataReader.hasNextLine()) {
                        String[] lineItems = dataReader.nextLine().split(" \\| ");
                        TaskType taskType = TaskType.fromCode(lineItems[0]);
                        TaskStatus taskStatus = TaskStatus.fromSymbol(lineItems[1]);
                        switch (taskType) {
                        case DEADLINE -> {
                            Deadline d = new Deadline(lineItems[2], TaskType.DEADLINE, taskStatus, lineItems[3]);
                            taskHandler.tasks.add(d);
                        }
                        case EVENT -> {
                            Event e = new Event(lineItems[2], TaskType.EVENT, taskStatus, lineItems[3], lineItems[4]);
                            taskHandler.tasks.add(e);
                        }
                        default -> {
                            Task t = new Task(lineItems[2], TaskType.TODO, taskStatus);
                            taskHandler.tasks.add(t);
                        String type = lineItems[0];
                        switch (type) {
                        case "D" -> {
                            assert lineItems.length == 4 : "There must only be 4 fields";
                            Deadline d = new Deadline(lineItems[2], "D", lineItems[1], lineItems[3]);
                            taskHandler.getTasks().add(d);
                        }
                        case "E" -> {
                            assert lineItems.length == 5 : "There must only be 5 fields";
                            Event e = new Event(lineItems[2], "E", lineItems[1], lineItems[3], lineItems[4]);
                            taskHandler.getTasks().add(e);
                        }
                        default -> {
                            assert lineItems.length == 3 : "There must only be 3 fields";
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
