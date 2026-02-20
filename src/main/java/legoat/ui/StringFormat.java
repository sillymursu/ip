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
    public static final String DEADLINE_REMINDER_STRING = """

                                PS: If you want "/by" to be formatted:
                                    yyyy mm dd <24h time>""";
    public static final String EVENT_REMINDER_STRING = """

                                PS: If you want "/from" or "/to" to be formatted:
                                    yyyy mm dd <24h time>""";
    public static final String WRONG_FORMAT_UNKNOWN_EXCEPTION_STRING =
            """
            Not something I can help with, son.
            But here is a list of valid commands:

            bye -> exit LeGoat!
            list -> list current tasks!
            todo -> add a Todo task!
            deadline -> add a Deadline task!
            event -> add an Event task!
            delete -> delete a task from the list!
            find -> find a task from the list!
            update -> update the name, deadline or event(begin/end)
                            of a task from the list!
            """;
    public static final String EMPTY_LIST_EXCEPTION_STRING =
            "The list is currently empty! Add some Tasks first!!";
    public static final String EVENT_TIME_EXCEPTION_STRING =
            "The event beginning time must not be after the ending time!";
    public static final String DOUBLE_COMPLETION_EXCEPTION_STRING =
            "Time Paradox? Task is already done!";
    public static final String DOUBLE_INCOMPLETE_EXCEPTION_STRING =
            "Time Paradox? Task is not yet done!";
    public static final String NUMBER_FORMAT_EXCEPTION_STRING =
            "Index specified is not a number!!";
    public static final String INDEX_OUT_OF_BOUNDS_EXCEPTION_STRING =
            "Index specified is not a valid number!!";
    public static final String CLASS_CAST_EXCEPTION_STRING =
            "This field is not applicable to the specified task!";
    public static final String WRONG_FORMAT_TODO_EXCEPTION_STRING =
            "The correct format is: \"todo <eventName>\"!";
    public static final String WRONG_FORMAT_DEADLINE_EXCEPTION_STRING =
            "The correct format is: \"deadline <eventName> /by <deadline>\"!";
    public static final String WRONG_FORMAT_EVENT_EXCEPTION_STRING =
            "The correct format is: \"event <eventName> /from <begin> /to <end>\"!";
    public static final String WRONG_FORMAT_DELETE_EXCEPTION_STRING =
            "The correct format is: \"delete < valid line item number>\"!";
    public static final String WRONG_FORMAT_FIND_EXCEPTION_STRING =
            "The correct format is: \"find <singular keyword>\"!";
    public static final String WRONG_FORMAT_UPDATE_EXCEPTION_STRING =
            """
            The correct update command formats are:
                \"update <list idx> name <updated name>\"
                \"update <list idx> deadline <updated deadline>\"
                \"update <list idx> event /from <updated beginning>\"
                \"update <list idx> event /to <updated ending>\"
            """;
    public static final String UPDATE_SUCCESS_STRING =
            "Successfully updated selected task!";
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

    /**
    * <p>Checks if the first date is after the second date.
    * @param date1String first date string in format "yyyy MM dd HHmm"
    * @param date2String second date string in format "yyyy MM dd HHmm"
    * @return true if date1 is after date2, false otherwise or if dates cannot be parsed
    * @since v0.3
    */
    public static boolean eventDateValidation(String date1String, String date2String) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HHmm");
            LocalDateTime date1 = LocalDateTime.parse(date1String, formatter);
            LocalDateTime date2 = LocalDateTime.parse(date2String, formatter);
            return date1.isAfter(date2);
        } catch (DateTimeException e) {
            return false;
        }
    }
}
