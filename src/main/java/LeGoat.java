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
        String longLine = "--------------------------------------------------";
        String byeMsg = "LeGoat logging off!";
        String LeGoatStr = "LeGoat: ";
        System.out.println("Hello from\n" + logo + "\n" + LeGoatStr + "What can I do for you?" + "\n" + longLine);
        inputHandler handler = new inputHandler();
        handler.start();
        System.out.println(longLine + "\n\n" + LeGoatStr + byeMsg + "\n\n" + longLine);
    }
}
