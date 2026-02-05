package legoat;

import legoat.handlers.InputHandler;
import legoat.legoatui.Ui;

/**
* Main class of LeGoat program.
*
* @author Russell Lin
*/
public class LeGoat {
    private final Ui format;
    public static void main(String[] args) {
        LeGoat goat = new LeGoat();
        goat.start();
    }

    /**
    * <p>Constructor for LeGoat objects.
    * @since v0.1
    */
    public LeGoat() {
        this.format = new Ui();
    }

    /**
    * <p>First method called by LeGoat, initializes a InputHandler object.
    * @since v0.1
    */
    public void start() {
        System.out.println("Hello from\n" + format.LOGO + "\n" + format.LEGOAT_STR +
            "What can I do for you?" + "\n" + format.LONG_LINE);
        InputHandler handle = new InputHandler();
        handle.start();
        System.out.println(format.LONG_LINE + "\n\n" + format.LEGOAT_STR + format.BYE_MESSAGE +
            "\n\n" + format.LONG_LINE);
    }
}
