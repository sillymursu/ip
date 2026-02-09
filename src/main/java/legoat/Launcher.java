package legoat;

import javafx.application.Application;

/**
* <p>Entry point of LeGoat program.
* This class is needed to work around a classpath issue.
* @since v0.2
*/
public class Launcher {
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
