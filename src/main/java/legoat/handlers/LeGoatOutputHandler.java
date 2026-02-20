package legoat.handlers;

import java.io.IOException;

import legoat.exceptions.DoubleCompletionException;
import legoat.exceptions.DoubleIncompleteException;
import legoat.exceptions.EmptyListException;
import legoat.exceptions.EventTimeException;
import legoat.exceptions.WrongFormatDeadlineException;
import legoat.exceptions.WrongFormatDeleteException;
import legoat.exceptions.WrongFormatEventException;
import legoat.exceptions.WrongFormatFindException;
import legoat.exceptions.WrongFormatTodoException;
import legoat.exceptions.WrongFormatUnknownException;
import legoat.exceptions.WrongFormatUpdateException;
import legoat.ui.StringFormat;

/**
* LeGoatOutputHandler handles all output related events.
*
* @author Russell Lin
*/
public class LeGoatOutputHandler {
    private final TaskHandler taskHandler;

    public LeGoatOutputHandler() {
        this.taskHandler = new TaskHandler();
    }

    /**
    * <p>Identifies the command that the user wants to issue to LeGoat.
    * @return String that LeGoat will use to reply to the user
    * @since v0.2
    */
    public String handleCommand(String[] input) throws IOException,
            DoubleCompletionException, DoubleIncompleteException,
            EmptyListException, EventTimeException, WrongFormatDeadlineException,
            WrongFormatDeleteException, WrongFormatEventException,
            WrongFormatFindException, WrongFormatTodoException,
            WrongFormatUnknownException, WrongFormatUpdateException {
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
    public String handleUnknownCommand() throws WrongFormatUnknownException {
        throw new WrongFormatUnknownException();
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
