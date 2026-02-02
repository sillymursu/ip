public class LeGoat {
    private final ui format;
    public static void main(String[] args) {
        LeGoat goat = new LeGoat();
        goat.start();
    }

    public LeGoat() {
        this.format = new ui();
    }

    public void start() {
        System.out.println("Hello from\n" + format.logo + "\n" + format.LeGoatStr +
            "What can I do for you?" + "\n" + format.longLine);
        InputHandler handle = new InputHandler();
        handle.start();
        System.out.println(format.longLine + "\n\n" + format.LeGoatStr + format.byeMsg +
            "\n\n" + format.longLine);
    }
}
