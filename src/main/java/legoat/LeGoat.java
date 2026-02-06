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

    /**
    * <p>Constructor for LeGoat objects.
    * @since v0.1
    */
    public LeGoat() {
        this.format = new Ui();
    }

    public static void main(String[] args) {
        LeGoat goat = new LeGoat();
        goat.start();
    }

    /**
    * <p>First method called by LeGoat, initializes a InputHandler object.
    * @since v0.1
    */
    public void start() {
        System.out.println("Hello from\n" + format.logoString + "\n" + format.leGoatString
                + "What can I do for you?" + "\n" + format.longLineString);
        InputHandler handle = new InputHandler();
        handle.start();
        System.out.println(format.longLineString + "\n\n" + format.leGoatString
                + format.byeString + "\n\n" + format.longLineString);
    }
}
