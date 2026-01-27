import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class inputHandler {
    String longLine = "--------------------------------------------------";
    String LeGoatStr = "LeGoat: ";
    int byeFlag;
    ArrayList<Task> listTasks;
    File savePath;

    public inputHandler() {
        this.byeFlag = 0;
        this.listTasks = new ArrayList<>();
    }

    public void start() {
        @SuppressWarnings("ConvertToTryWithResources")
        Scanner sc = new Scanner(System.in);
        this.loadData();
        while (this.byeFlag != 1) {
            String inputRaw = sc.nextLine();
            String[] input = inputRaw.split(" ");
            this.handleInput(input);
        }
        sc.close();
    }

    public void handleInput(String[] input) {
        switch (input[0]) {
            case "bye" -> this.bye();
            case "list" -> this.list();
            case "mark", "unmark" -> this.markUnmark(input);
            case "todo" -> this.addToDo(input);
            case "deadline" -> this.addDeadline(input);
            case "event" -> this.addEvent(input);
            case "delete" -> this.delete(input);
            default -> this.notACommand();
        }
    }

    public void notACommand() {
        System.err.println(longLine + "\n\n" + LeGoatStr + "Not something I can help with, brochacho." + "\n\n" + longLine);
    }

    public void bye() {
        this.byeFlag = 1;
    }

    public void list() {
        if (listTasks.isEmpty()) {
            System.err.println(longLine + "\n\n" + LeGoatStr + "The list is currently empty! Add some Tasks first!!" + "\n\n" + longLine);
        } else {
            System.out.println(longLine + "\n");
                for (int i = 0; i < listTasks.size(); i++) {
                    int lineNumber = i + 1;
                    String type = listTasks.get(i).taskType;
                    switch (type) {
                        case "D" -> {
                            Deadline d = (Deadline) listTasks.get(i);
                            System.out.println(lineNumber + ". " + d.toString());
                        }
                        case "E" -> {
                            Event e = (Event) listTasks.get(i);
                            System.out.println(lineNumber + ". " + e.toString());
                        }
                        default -> {
                            Task t = listTasks.get(i);
                            System.out.println(lineNumber + ". " + t.toString());
                        }
                    }
                }
            System.out.println("\n" + longLine);
        }
    }

    public void markUnmark(String[] input) {
        try {
            int lineNum = Integer.parseInt(input[1]) - 1;
            Task t = listTasks.get(lineNum);
            if (input[0].equals("mark")) {
                if (t.taskStatus.equals("X")) {
                    System.err.println(longLine + "\n\n" + LeGoatStr + "Time Paradox? Task is already done!" + "\n\n" + longLine);
                    return;
                } else {
                    t.mark();
                    System.out.println(longLine + "\n\n" + LeGoatStr + "Easy work. Task completed!");
                }
            } else {
                if (t.taskStatus.equals(" ")) {
                    System.err.println(longLine + "\n\n" + LeGoatStr + "Time Paradox? Task is not yet done!" + "\n\n" + longLine);
                    return;
                } else {
                    t.unmark();
                    System.out.println(longLine + "\n\n" + LeGoatStr + "Electric Boogaloo. Task uncompleted!");
                }
            }
            String type = t.taskType;
            switch (type) {
                case "D" -> {
                    Deadline d = (Deadline) listTasks.get(lineNum);
                    System.out.println("   " + d.toString());
                    this.saveData();
                    System.out.println("\n" + longLine);
                    break;
                }
                case "E" -> {
                    Event e = (Event) listTasks.get(lineNum);
                    System.out.println("   " + e.toString());
                    this.saveData();
                    System.out.println("\n" + longLine);
                    break;
                }
                default -> {
                    System.out.println("   " + t.toString());
                    this.saveData();
                    System.out.println("\n" + longLine);
                }
            }
        } catch (NumberFormatException e) {
            System.err.println(longLine + "\n\n" + LeGoatStr + "Second Argument is not a number!!" + "\n\n" + longLine);
        } catch (IndexOutOfBoundsException e) {
            System.err.println(longLine + "\n\n" + LeGoatStr + "Second Argument is not a valid number!!" + "\n\n" + longLine);
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
            System.err.println(longLine + "\n\n" + LeGoatStr + "The correct format is: \"todo <eventName>\"!" + "\n\n" + longLine);
        } else {
            Task t = new Task(taskName, "T", " ");
            listTasks.add(t);
            System.out.println(longLine + "\n\n" + "Added Task:\n   " + t.toString());
            this.saveData();
            System.out.println("\n" + longLine);
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
        if (taskName.isEmpty() || taskDeadline.isEmpty()) {
            System.err.println(longLine + "\n\n" + LeGoatStr + "The correct format is: \"deadline <eventName> /by <deadline>\"!" + "\n\n" + longLine);
        } else {
            Deadline d = new Deadline(taskName, "D", " ", taskDeadline);
            listTasks.add(d);
            System.out.println(longLine + "\n\n" + "Added Deadline:\n   " + d.toString());
            this.saveData();
            System.out.println("\n" + longLine);
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
        if (taskName.isEmpty() || taskBegin.isEmpty() || taskEnd.isEmpty()) {
            System.err.println(longLine + "\n\n" + LeGoatStr + "The correct format is: \"event <eventName> /from <begin> /to <end>\"!" + "\n\n" + longLine);
        } else {
            Event e = new Event(taskName, "E", " ", taskBegin, taskEnd);
            listTasks.add(e);
            System.out.println(longLine + "\n\n" + "Added Event:\n   " + e.toString());
            this.saveData();
            System.out.println("\n" + longLine);
        }
    }

    public void delete(String[] input) {
        try {
            int removeTaskAtIDX = Integer.parseInt(input[1]) - 1;
            listTasks.remove(removeTaskAtIDX);
            System.out.println(longLine + "\n\n" + LeGoatStr + "Task deleted!!" + "\n" +
            LeGoatStr + "You have " + listTasks.size() + " Tasks left!!");
            this.saveData();
            System.out.println("\n" + longLine);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.err.println(longLine + "\n\n" + LeGoatStr + "The correct format is: \"delete < valid line item number>\"!" + "\n\n" + longLine);
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
            System.err.println(longLine + "\n\n" + LeGoatStr + "Sum ting wong..." + "\n\n" + longLine);
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
            System.err.println(longLine + "\n\n" + LeGoatStr + "Sum ting wong..." + "\n\n" + longLine);
        }
    }
}
