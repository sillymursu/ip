import java.util.*;

public class inputHandler {
    String longLine = "--------------------------------------------------";
    String LeGoatStr = "LeGoat: ";
    int byeFlag;
    ArrayList<Task> listTasks;

    public inputHandler() {
        this.byeFlag = 0;
        this.listTasks = new ArrayList<>();
    }

    public void start() {
        @SuppressWarnings("ConvertToTryWithResources")
        Scanner sc = new Scanner(System.in);
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
            default -> this.add(input);
        }
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
                    Task t = listTasks.get(i);
                    System.out.println(lineNumber + ". " + "[" + t.markStatus + "]" + t.taskName);
                }
            System.out.println("\n" + longLine);
        }
    }

    public void markUnmark(String[] input) {
        try {
            int lineNum = Integer.parseInt(input[1]) - 1;
            Task t = listTasks.get(lineNum);
            if (input[0].equals("mark")) {
                t.mark();
                System.out.println(longLine + "\n\n" + LeGoatStr + "Easy work. Task completed!");
            } else {
                t.unmark();
                System.out.println(longLine + "\n\n" + LeGoatStr + "Wah. Task uncompleted!");
            }
            System.out.println("   [" + t.markStatus + "]" + t.taskName + "\n\n" + longLine);
        } catch (NumberFormatException e) {
            System.err.println(longLine + "\n\n" + LeGoatStr + "Second Argument is not a number!!" + "\n\n" + longLine);
        } catch (IndexOutOfBoundsException e) {
            System.err.println(longLine + "\n\n" + LeGoatStr + "Second Argument is not a valid number!!" + "\n\n" + longLine);
        }
    }

    public void add(String[] input) {
        StringBuilder added = new StringBuilder();
            for (String s : input) {
                added.append(" ");
                added.append(s);
            }
        String taskName = added.toString();
        Task t = new Task(taskName, " ");
        listTasks.add(t);
        System.out.println(longLine + "\n\n" + "Added:" + taskName + "\n\n" + longLine);
    }
}
