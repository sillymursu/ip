package legoat.handlers;
import java.util.Scanner;

import legoat.legoatui.Ui;
import legoat.tasktypes.Deadline;
import legoat.tasktypes.Event;
import legoat.tasktypes.Task;

public class InputHandler {
    private final Ui format;
    TaskHandler taskHandler;
    DataHandler dataHandler;
    int isBye;
    
    public InputHandler() {
        this.isBye = 0;
        this.format = new Ui();
        this.taskHandler = new TaskHandler();
        this.dataHandler = new DataHandler(this.taskHandler);
        this.taskHandler.setDataHandler(this.dataHandler);
    }

    public void start() {
        @SuppressWarnings("ConvertToTryWithResources")
        Scanner sc = new Scanner(System.in);
        dataHandler.loadData();
        while (this.isBye != 1) {
            String inputRaw = sc.nextLine();
            String[] input = inputRaw.split("\\s+");
            this.handleInput(input);
        }
        sc.close();
    }

    public void handleInput(String[] input) {
        switch (input[0]) {
            case "bye" -> this.bye();
            case "list" -> this.list();
            case "mark", "unmark" -> taskHandler.markUnmark(input);
            case "todo" -> taskHandler.addToDo(input);
            case "deadline" -> taskHandler.addDeadline(input);
            case "event" -> taskHandler.addEvent(input);
            case "delete" -> taskHandler.delete(input);
            case "find" -> taskHandler.find(input);
            default -> this.notACommand();
        }
    }

    public void notACommand() {
        System.err.println(format.LONG_LINE + "\n\n" + format.LEGOAT_STR +
            "Not something I can help with, brochacho." + "\n\n" + format.LONG_LINE);
    }

    public void bye() {
        this.isBye = 1;
    }

    public void list() {
        if (taskHandler.getTaskList().isEmpty()) {
            System.err.println(format.LONG_LINE + "\n\n" + format.LEGOAT_STR +
                "The list is currently empty! Add some Tasks first!!" + "\n\n" + format.LONG_LINE);
        } else {
            System.out.println(format.LONG_LINE + "\n");
                for (int i = 0; i < taskHandler.getTaskList().size(); i++) {
                    int lineNumber = i + 1;
                    String type = taskHandler.getTaskList().get(i).getTaskType();
                    switch (type) {
                        case "D" -> {
                            Deadline d = (Deadline) taskHandler.getTaskList().get(i);
                            System.out.println(lineNumber + ". " + d.toString());
                        }
                        case "E" -> {
                            Event e = (Event) taskHandler.getTaskList().get(i);
                            System.out.println(lineNumber + ". " + e.toString());
                        }
                        default -> {
                            Task t = taskHandler.getTaskList().get(i);
                            System.out.println(lineNumber + ". " + t.toString());
                        }
                    }
                }
            System.out.println("\n" + format.LONG_LINE);
        }
    }
}
