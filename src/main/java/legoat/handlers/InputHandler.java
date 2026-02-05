package legoat.handlers;
import java.util.Scanner;

import legoat.legoatui.Ui;
import legoat.tasktypes.Deadline;
import legoat.tasktypes.Event;
import legoat.tasktypes.Task;

/**
* InputHandler handles all input related events.
*
* @author Russell Lin
*/
public class InputHandler {
    private final Ui format;
    TaskHandler taskHandler;
    DataHandler dataHandler;
    int isBye;
    
    /**
    * <p>Constructor for InputHandler objects. Initializes a TaskHandler object and DataHandler object.
    * @since v0.1
    */
    public InputHandler() {
        this.isBye = 0;
        this.format = new Ui();
        this.taskHandler = new TaskHandler();
        this.dataHandler = new DataHandler(this.taskHandler);
        this.taskHandler.setDataHandler(this.dataHandler);
    }

    /**
    * <p>Second method called by LeGoat, forces LeBron to accept input until "bye" input.
    * @since v0.1
    */
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

    /**
    * <p>Handles user inputs by directing them to the correct function.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @since v0.1
    */
    public void handleInput(String[] input) {
        switch (input[0]) {
        case "bye" -> this.bye();
        case "list" -> this.list();
        case "mark", "unmark" -> taskHandler.markUnmark(input);
        case "todo" -> taskHandler.addToDo(input);
        case "deadline" -> taskHandler.addDeadline(input);
        case "event" -> taskHandler.addEvent(input);
        case "delete" -> taskHandler.deleteTask(input);
        case "find" -> taskHandler.find(input);
        default -> this.handleUnknownCommand();
        }
    }

    /**
    * <p>Default function called when user input is not recognised as a valid command.
    * @since v0.1
    */
    public void handleUnknownCommand() {
        System.err.println(format.LONG_LINE + "\n\n" + format.LEGOAT_STR +
            "Not something I can help with, brochacho." + "\n\n" + format.LONG_LINE);
    }

    /**
    * <p>Function called when user input is "bye", sets isBye to 1, which force LeBron to terminate.
    * @since v0.1
    */
    public void bye() {
        this.isBye = 1;
    }

    /**
    * <p>Function called when user input is "list", prints a list of added tasks unless
    *  current list of tasks is empty.
    * @since v0.1
    */
    public void list() {
        if (taskHandler.getTasks().isEmpty()) {
            System.err.println(format.LONG_LINE + "\n\n" + format.LEGOAT_STR +
                "The list is currently empty! Add some Tasks first!!" + "\n\n" + format.LONG_LINE);
        } else {
            System.out.println(format.LONG_LINE + "\n");
                for (int i = 0; i < taskHandler.getTasks().size(); i++) {
                    int lineNumber = i + 1;
                    String taskType = taskHandler.getTasks().get(i).getTaskType();
                    switch (taskType) {
                        case "D" -> {
                            Deadline d = (Deadline) taskHandler.getTasks().get(i);
                            System.out.println(lineNumber + ". " + d.toString());
                        }
                        case "E" -> {
                            Event e = (Event) taskHandler.getTasks().get(i);
                            System.out.println(lineNumber + ". " + e.toString());
                        }
                        default -> {
                            Task t = taskHandler.getTasks().get(i);
                            System.out.println(lineNumber + ". " + t.toString());
                        }
                    }
                }
            System.out.println("\n" + format.LONG_LINE);
        }
    }
}
