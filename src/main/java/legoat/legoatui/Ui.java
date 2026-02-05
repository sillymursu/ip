package legoat.legoatui;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
* Ui is a data class that stores all User Interface elements.
*
* @author Russell Lin
*/
public class Ui {
    public final String LOGO = """
                       _      ___    ____    _____     ___    _______
                      | |    |  _|  / ___\\  / / \\ \\   / _ \\  |__   __|
                      | |    | |_  / / ___  | | | |  / /_\\ \\    | |
                      | |    |  _| \\ \\|_  \\ | | | | / /   \\ \\   | |
                      | |___ |_|_   \\ \\_| | | \\_/ | | |   | |   | |   _
                      |____/ |___|   \\___/  \\_____/ |_|   |_|   |_|  |_|
                      """;
    public final String BYE_MESSAGE = "LeGoat logging off!";
    public final String LONG_LINE = "--------------------------------------------------";
    public final String LEGOAT_STR = "LeGoat: ";
    public final String DEADLINE_FORMAT_REMINDER = """

                                PS: If you want "/by" to be formatted:
                                    yyyy mm dd <24h time>""";
    public final String EVENT_FORMAT_REMINDER = """

                                PS: If you want "/from" or "/to" to be formatted:
                                    yyyy mm dd <24h time>""";

    public Ui() {}

    /**
    * <p>Parses a String into a proper date.
    * @param maybeDate a String that might be parseable to the desired "MMM yyyy hh:mm a"
    *  date format
    * @return Returns a String, a proper date, or a null String if no proper date could be parsed
    * @throws DateTimeException
    * @since v0.1
    */
    public String parseDate(String maybeDate) {
        try {
            DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy MM dd HHmm");
            DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM yyyy hh:mm a");
            LocalDateTime inputTime = LocalDateTime.parse(maybeDate, inputFormat);
            String daySuffix = getDaySuffix(inputTime.getDayOfMonth());
            String outputTime = String.format("%d%s %s", inputTime.getDayOfMonth(), daySuffix, inputTime.format(outputFormat));
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
    public String getDaySuffix(int day) {
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
