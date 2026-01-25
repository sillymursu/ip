import java.util.Scanner;

public class LeGoat {
    public static void main(String[] args) {
        String logo = """
                       _      ___    ____    _____     ___    _______
                      | |    |  _|  / ___\\  / / \\ \\   / _ \\  |__   __|
                      | |    | |_  / / ___  | | | |  / /_\\ \\    | |
                      | |    |  _| \\ \\|_  \\ | | | | / /   \\ \\   | |
                      | |___ |_|_   \\ \\_| | | \\_/ | | |   | |   | |   _
                      |____/ |___|   \\___/  \\_____/ |_|   |_|   |_|  |_|
                      """;
        String longLine = "\n--------------------------------------------------\n";
        String byeMsg = "LeGoat logging off!";
        System.out.println("Hello from\n" + logo + "\n" + "What can I do for you?" + longLine);
        @SuppressWarnings("ConvertToTryWithResources")
        Scanner sc = new Scanner(System.in);
        int byeFlag = 0;
        while (byeFlag == 0) {
            String input = sc.nextLine();
            if (!input.equals("bye")) {
                System.out.println(longLine + input + longLine);
            } else {
                byeFlag = 1;
            }
        }
        System.out.println(longLine + byeMsg + longLine);
        sc.close();
    }
}
