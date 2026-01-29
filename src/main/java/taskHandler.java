import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

public class taskHandler {
    ArrayList<Task> listTasks;
    File savePath;
    ui format;

    public taskHandler() {
        this.listTasks = new ArrayList<>();
        this.format = new ui();
    }

    public void markUnmark(String[] input) {
        try {
            int lineNum = Integer.parseInt(input[1]) - 1;
            Task t = listTasks.get(lineNum);
            if (input[0].equals("mark")) {
                if (t.taskStatus.equals("X")) {
                    System.err.println(format.longLine + "\n\n" + format.LeGoatStr +
                        "Time Paradox? Task is already done!" + "\n\n" + format.longLine);
                    return;
                } else {
                    t.mark();
                    System.out.println(format.longLine + "\n\n" + format.LeGoatStr +
                        "Easy work. Task completed!");
                }
            } else {
                if (t.taskStatus.equals(" ")) {
                    System.err.println(format.longLine + "\n\n" + format.LeGoatStr +
                        "Time Paradox? Task is not yet done!" + "\n\n" + format.longLine);
                    return;
                } else {
                    t.unmark();
                    System.out.println(format.longLine + "\n\n" + format.LeGoatStr +
                        "Electric Boogaloo. Task uncompleted!");
                }
            }
            String type = t.taskType;
            switch (type) {
                case "D" -> {
                    Deadline d = (Deadline) listTasks.get(lineNum);
                    System.out.println("   " + d.toString());
                    this.saveData();
                    System.out.println("\n" + format.longLine);
                    break;
                }
                case "E" -> {
                    Event e = (Event) listTasks.get(lineNum);
                    System.out.println("   " + e.toString());
                    this.saveData();
                    System.out.println("\n" + format.longLine);
                    break;
                }
                default -> {
                    System.out.println("   " + t.toString());
                    this.saveData();
                    System.out.println("\n" + format.longLine);
                }
            }
        } catch (NumberFormatException e) {
            System.err.println(format.longLine + "\n\n" + format.LeGoatStr + 
                "Second Argument is not a number!!" + "\n\n" + format.longLine);
        } catch (IndexOutOfBoundsException e) {
            System.err.println(format.longLine + "\n\n" + format.LeGoatStr + 
                "Second Argument is not a valid number!!" + "\n\n" + format.longLine);
        }
    }

    public void addToDo(String[] input) {
        StringBuilder tdName = new StringBuilder();
            for (int i = 1; i < input.length; i++) {
                tdName.append(" ");
                tdName.append(input[i]);
            }
        String taskName = tdName.toString().replaceFirst(" ","");
        if (taskName.isEmpty()) {
            System.err.println(format.longLine + "\n\n" + format.LeGoatStr + 
                "The correct format is: \"todo <eventName>\"!" + "\n\n" + format.longLine);
        } else {
            Task t = new Task(taskName, "T", " ");
            listTasks.add(t);
            System.out.println(format.longLine + "\n\n" + "Added Task:\n   " + t.toString());
            this.saveData();
            System.out.println("\n" + format.longLine);
        }
    }

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
        String taskName = dName.toString().replaceFirst(" ","");
        String taskDeadline = dDate.toString().replaceFirst(" ","");
        String taskDeadlineDate = format.parseDate(taskDeadline);
        if (taskName.isEmpty() || taskDeadline.isEmpty()) {
            System.err.println(format.longLine + "\n\n" + format.LeGoatStr + 
                "The correct format is: \"deadline <eventName> /by <deadline>\"!" +
                "\n\n" + format.longLine);
        } else {
            if (!taskDeadlineDate.equals("")) {
                taskDeadline = taskDeadlineDate;
            }
            Deadline d = new Deadline(taskName, "D", " ", taskDeadline);
            listTasks.add(d);
            System.out.println(format.longLine + "\n\n" + "Added Deadline:\n   " + d.toString());
            if (taskDeadlineDate.equals("")) {
                System.out.println(format.deadlineTimeFormatReminder);
            }
            this.saveData();
            System.out.println("\n" + format.longLine);
        }
    }

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
        String taskName = eName.toString().replaceFirst(" ","");
        String taskBegin = eFrom.toString().replaceFirst(" ","");
        String taskEnd = eTo.toString().replaceFirst(" ","");
        String taskBeginDate = format.parseDate(taskBegin);
        String taskEndDate = format.parseDate(taskEnd);
        if (taskName.isEmpty() || taskBegin.isEmpty() || taskEnd.isEmpty()) {
            System.err.println(format.longLine + "\n\n" + format.LeGoatStr +
                "The correct format is: \"event <eventName> /from <begin> /to <end>\"!" +
                "\n\n" + format.longLine);
        } else {
            if (!taskBeginDate.equals("")) {
                taskBegin = taskBeginDate;
            }
            if (!taskEndDate.equals("")) {
                taskEnd = taskEndDate;
            }
            Event e = new Event(taskName, "E", " ", taskBegin, taskEnd);
            listTasks.add(e);
            System.out.println(format.longLine + "\n\n" + "Added Event:\n   " + e.toString());
            if (taskBeginDate.equals("") || taskEndDate.equals("")) {
                System.out.println(format.eventTimeFormatReminder);
            }
            this.saveData();
            System.out.println("\n" + format.longLine);
        }
    }

    public void delete(String[] input) {
        try {
            int removeTaskAtIDX = Integer.parseInt(input[1]) - 1;
            listTasks.remove(removeTaskAtIDX);
            if (listTasks.size() == 1) {
                System.out.println(format.longLine + "\n\n" + format.LeGoatStr + "Task deleted!!" + "\n" +
                    format.LeGoatStr + "You have " + listTasks.size() + " Task left!!");
            } else {
                System.out.println(format.longLine + "\n\n" + format.LeGoatStr + "Task deleted!!" + "\n" +
                    format.LeGoatStr + "You have " + listTasks.size() + " Tasks left!!");
            }
            this.saveData();
            System.out.println("\n" + format.longLine);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.err.println(format.longLine + "\n\n" + format.LeGoatStr + 
                "The correct format is: \"delete < valid line item number>\"!" +
                "\n\n" + format.longLine);
        }
    }

    public void saveData() {
        try {
            this.savePath = new File("src/data/LeGoatData.txt");
            this.savePath.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(savePath)) {
                for (Task t : listTasks) {
                    String taskType = t.taskType;
                    switch (taskType) {
                        case "D" -> {
                            Deadline d = (Deadline) t;
                            writer.write(d.taskType + " | " + d.taskStatus + " | " + d.taskName + " | " + d.deadline + "\n");
                        }
                        case "E" -> {
                            Event e = (Event) t;
                            writer.write(e.taskType + " | " + e.taskStatus + " | " + e.taskName + " | " + e.begin+ " | " + e.end + "\n");
                        }
                        default -> {
                            writer.write(t.taskType + " | " + t.taskStatus + " | " + t.taskName + "\n");
                        }
                    }
                }
                System.out.println("\nSaved successfully!!!");
            }
        } catch (IOException e) {
            System.err.println(format.longLine + "\n\n" + format.LeGoatStr + "Save FAILED!!!" +
                "\n\n" + format.longLine);
        }
    }

    public void loadData() {
        try {
            this.savePath = new File("src/data/LeGoatData.txt");
            if (!savePath.exists()) {
            } else {
                try (Scanner dataReader = new Scanner(this.savePath)) {
                    while (dataReader.hasNextLine()) {
                        String[] lineItems = dataReader.nextLine().split(" \\| ");
                        String type = lineItems[0];
                        switch (type) {
                            case "D" -> {
                                Deadline d = new Deadline(lineItems[2], "D", lineItems[1], lineItems[3]);
                                listTasks.add(d);
                            }
                            case "E" -> {
                                Event e = new Event(lineItems[2], "E", lineItems[1], lineItems[3], lineItems[4]);
                                listTasks.add(e);
                            }
                            case "T" -> {
                                Task t = new Task(lineItems[2], "T", lineItems[1]);
                                listTasks.add(t);
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println(format.longLine + "\n\n" + format.LeGoatStr +
                "Load FAILED!!!" + "\n\n" + format.longLine);
        }
    }

    public void find(String[] input) {
        if (input.length > 2) {
            System.err.println(format.longLine + "\n\n" + format.LeGoatStr + "You can only find (1) keyword!" +
                "\n\n" + format.longLine);
        } else {
            int tasksFound = 0;
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
            try {
                System.out.println(format.longLine + "\n");
                for (Task t : listTasks) {
                    String taskName = t.taskName;
                    if (taskName.contains(input[1])) {
                        tasksFound++;
                        String type = t.taskType;
                        switch (type) {
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
                        System.out.println(format.LeGoatStr + "I found " + tasksFound + " tasks:");
                        bw.flush();
                    } else {
                        System.out.println(format.LeGoatStr + "I found 1 task:");
                        bw.flush();
                    }
                } else {
                    System.err.println(format.LeGoatStr + "Uh oh! No tasks with keyword " + input[1] + " were found!");
                }
                System.out.println("\n" + format.longLine);
            } catch (IOException e) {
                System.err.println(format.LeGoatStr + "Uh oh! IOException!" + "\n" + format.longLine);
            }
        }
    }
}
