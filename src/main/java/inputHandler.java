import java.util.Scanner;

public class inputHandler {
    ui format;
    taskHandler taskHandler;
    int byeFlag;
    
    public inputHandler() {
        this.byeFlag = 0;
        this.format = new ui();
        this.taskHandler = new taskHandler();
    }

    public void start() {
        @SuppressWarnings("ConvertToTryWithResources")
        Scanner sc = new Scanner(System.in);
        taskHandler.loadData();
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
            case "mark", "unmark" -> taskHandler.markUnmark(input);
            case "todo" -> taskHandler.addToDo(input);
            case "deadline" -> taskHandler.addDeadline(input);
            case "event" -> taskHandler.addEvent(input);
            case "delete" -> taskHandler.delete(input);
            default -> this.notACommand();
        }
    }

    public void notACommand() {
        System.err.println(format.longLine + "\n\n" + format.LeGoatStr +
            "Not something I can help with, brochacho." + "\n\n" + format.longLine);
    }

    public void bye() {
        this.byeFlag = 1;
    }

    public void list() {
        if (taskHandler.listTasks.isEmpty()) {
            System.err.println(format.longLine + "\n\n" + format.LeGoatStr +
                "The list is currently empty! Add some Tasks first!!" + "\n\n" + format.longLine);
        } else {
            System.out.println(format.longLine + "\n");
                for (int i = 0; i < taskHandler.listTasks.size(); i++) {
                    int lineNumber = i + 1;
                    String type = taskHandler.listTasks.get(i).taskType;
                    switch (type) {
                        case "D" -> {
                            Deadline d = (Deadline) taskHandler.listTasks.get(i);
                            System.out.println(lineNumber + ". " + d.toString());
                        }
                        case "E" -> {
                            Event e = (Event) taskHandler.listTasks.get(i);
                            System.out.println(lineNumber + ". " + e.toString());
                        }
                        default -> {
                            Task t = taskHandler.listTasks.get(i);
                            System.out.println(lineNumber + ". " + t.toString());
                        }
                    }
                }
            System.out.println("\n" + format.longLine);
        }
    }
}