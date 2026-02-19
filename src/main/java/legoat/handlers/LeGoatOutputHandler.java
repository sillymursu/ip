package legoat.handlers;

import java.io.FileNotFoundException;
import java.io.IOException;

import legoat.ui.StringFormat;

/**
* LeGoatOutputHandler handles all output related events.
*
* @author Russell Lin
*/
public class LeGoatOutputHandler {
    private final TaskHandler taskHandler;

    public LeGoatOutputHandler() throws FileNotFoundException {
        this.taskHandler = new TaskHandler();
    }

    /**
    * <p>Identifies the command that the user wants to issue to LeGoat.
    * @return String that LeGoat will use to reply to the user
    * @since v0.2
    */
    public String handleCommand(String[] input) throws FileNotFoundException,
            IOException {
        return switch (input[0]) {
        case "bye" -> this.bye();
        case "list" -> taskHandler.list();
        case "mark", "unmark" -> taskHandler.markUnmark(input);
        case "todo" -> taskHandler.addToDo(input);
        case "deadline" -> taskHandler.addDeadline(input);
        case "event" -> taskHandler.addEvent(input);
        case "delete" -> taskHandler.deleteTask(input);
        case "find" -> taskHandler.find(input);
        case "update" -> taskHandler.update(input);
        default -> this.handleUnknownCommand();
        };
    }

    /**
    * <p>Handles the case of the user issuing an unknown commmand to LeGoat.
    * @return String that LeGoat will use to reply to the user
    * @since v0.2
    */
    public String handleUnknownCommand() {
        return StringFormat.UNKNOWN_COMMAND_STRING;
    }

    /**
    * <p>Handles the case of the user wanting LeGoat to log off.
    * @return String that LeGoat will use to reply to the user
    * @since v0.2
    */
    public String bye() {
        return StringFormat.BYE_STRING;
    }
}
