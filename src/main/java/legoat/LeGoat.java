import handlers.InputHandler;
import legoatui.Ui;

public class LeGoat {
    private final Ui format;
    public static void main(String[] args) {
        LeGoat goat = new LeGoat();
        goat.start();
    }

    public LeGoat() {
        this.format = new Ui();
    }

    public void start() {
        System.out.println("Hello from\n" + format.LOGO + "\n" + format.LEGOAT_STR +
            "What can I do for you?" + "\n" + format.LONG_LINE);
        InputHandler handle = new InputHandler();
        handle.start();
        System.out.println(format.LONG_LINE + "\n\n" + format.LEGOAT_STR + format.BYE_MSG +
            "\n\n" + format.LONG_LINE);
    }
}
