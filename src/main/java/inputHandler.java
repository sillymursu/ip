import java.util.*;

public class inputHandler {
    String longLine = "--------------------------------------------------";
    int byeFlag;
    ArrayList<String> listString;

    public inputHandler() {
        this.byeFlag = 0;
        this.listString = new ArrayList<>();
    }

    public void start() {
        @SuppressWarnings("ConvertToTryWithResources")
        Scanner sc = new Scanner(System.in);
        while (this.byeFlag != 1) {
            String input = sc.nextLine();
            this.handleInput(input);
        }
        sc.close();
    }

    public void handleInput(String input) {
        if (!input.equals("bye") && !input.equals("list")) {
            listString.add(input);
            System.out.println(longLine + "\n\n" + "Added: " + input + "\n\n" + longLine);
        } else if (input.equals("list") && !listString.isEmpty()){
            System.out.println(longLine + "\n\n");
            for (int i = 0; i < listString.size(); i++) {
                int lineNumber = i + 1;
                System.out.println(lineNumber + ". " + listString.get(i));
            }
            System.out.println("\n\n" + longLine);
        } else if (input.equals("bye")) {
            this.byeFlag = 1;
        }
    }
}
