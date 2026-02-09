package legoat.handlers;

import legoat.ui.StringFormat;

/**
* LeGoatOutputHandler handles all output related events.
*
* @author Russell Lin
*/
public class LeGoatOutputHandler {
    private final TaskHandler taskHandler = new TaskHandler();

    /**
    * <p>Gets the output response for LeGoat.
    * @return String that LeGoat will use to reply to the user
    * @since v0.2
    */
    public String getResponse(String[] input) {
        return this.handleCommand(input);
    }

    /**
    * <p>Identifies the command that the user wants to issue to LeGoat.
    * @return String that LeGoat will use to reply to the user
    * @since v0.2
    */
    public String handleCommand(String[] input) {
        return switch (input[0]) {
        case "bye" -> this.bye();
        case "list" -> taskHandler.list();
        case "mark", "unmark" -> taskHandler.markUnmark(input);
        case "todo" -> taskHandler.addToDo(input);
        case "deadline" -> taskHandler.addDeadline(input);
        case "event" -> taskHandler.addEvent(input);
        case "delete" -> taskHandler.deleteTask(input);
        case "find" -> taskHandler.find(input);
        default -> this.handleUnknownCommand();
        };
    }

    /**
    * <p>Handles the case of the user issuing an unknown commmand to LeGoat.
    * @return String that LeGoat will use to reply to the user
    * @since v0.2
    */
    public String handleUnknownCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append(StringFormat.LEGOAT_STRING + "Not something I can help with, brochacho.\n"
                + "But, here is a list of valid commands:\n\n"
                + "bye -> exit LeGoat\n"
                + "list -> list current tasks\n"
                + "todo -> add a Todo task!\n"
                + "deadline -> add a Deadline task!\n"
                + "event -> add an Event task!\n"
                + "delete -> delete a task from the list!\n"
                + "find -> find a task from the list!\n"
        );
        return sb.toString().trim();
    }

    /**
    * <p>Handles the case of the user wanting LeGoat to log off.
    * @return String that LeGoat will use to reply to the user
    * @since v0.2
    */
    public String bye() {
        String output = StringFormat.LEGOAT_STRING + StringFormat.BYE_STRING;
        return output;
    }
}
