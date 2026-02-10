package legoat.ui;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
* Ui is a data class that stores String formatting elements.
*
* @author Russell Lin
*/
public class StringFormat {
    public static final String LOGO_STRING = """
                       _      ___    ____    _____     ___    _______
                      | |    |  _|  / ___\\  / / \\ \\   / _ \\  |__   __|
                      | |    | |_  / / ___  | | | |  / /_\\ \\    | |
                      | |    |  _| \\ \\|_  \\ | | | | / /   \\ \\   | |
                      | |___ |_|_   \\ \\_| | | \\_/ | | |   | |   | |   _
                      |____/ |___|   \\___/  \\_____/ |_|   |_|   |_|  |_|
                      """;
    public static final String BYE_STRING = "LeGoat logging off!";
    public static final String LEGOAT_STRING = "LeGoat: ";
    public static final String DEADLINE_REMINDER_STRING = """

                                PS: If you want "/by" to be formatted:
                                    yyyy mm dd <24h time>""";
    public static final String EVENT_REMINDER_STRING = """

                                PS: If you want "/from" or "/to" to be formatted:
                                    yyyy mm dd <24h time>""";
    public static final String UNKNOWN_COMMAND_STRING =
            """
            Not something I can help with, brochacho.
            But here is a list of valid commands:

            bye -> exit LeGoat
            list -> list current tasks
            todo -> add a Todo task!
            deadline -> add a Deadline task!
            event -> add an Event task!
            delete -> delete a task from the list!
            find -> find a task from the list!
            """;

    /**
    * <p>Parses a String into a proper date.
    * @param maybeDate a String that might be parseable to the desired "MMM yyyy hh:mm a"
    *     date format
    * @return Returns a String, a proper date, or a null String if no proper date could be parsed
    * @throws DateTimeException
    * @since v0.1
    */
    public static String parseDate(String maybeDate) {
        try {
            DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy MM dd HHmm");
            DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM yyyy hh:mm a");
            LocalDateTime inputTime = LocalDateTime.parse(maybeDate, inputFormat);
            String daySuffix = getDaySuffix(inputTime.getDayOfMonth());
            String outputTime = String.format("%d%s %s", inputTime.getDayOfMonth(),
                    daySuffix, inputTime.format(outputFormat));
            return outputTime;
        } catch (DateTimeException e) {
            return "";
        }
    }

    /**
    * <p>Returns the suffix of specified day of a month.
    * @param day an Integer representing the day of the month
    * @return suffix of specified day of a month
    * @since v0.1
    */
    public static String getDaySuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        return switch (day % 10) {
        case 1 -> "st";
        case 2 -> "nd";
        case 3 -> "rd";
        default -> "th";
        };
    }
}
