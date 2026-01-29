import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ui {
    String logo = """
                       _      ___    ____    _____     ___    _______
                      | |    |  _|  / ___\\  / / \\ \\   / _ \\  |__   __|
                      | |    | |_  / / ___  | | | |  / /_\\ \\    | |
                      | |    |  _| \\ \\|_  \\ | | | | / /   \\ \\   | |
                      | |___ |_|_   \\ \\_| | | \\_/ | | |   | |   | |   _
                      |____/ |___|   \\___/  \\_____/ |_|   |_|   |_|  |_|
                      """;
    String byeMsg = "LeGoat logging off!";
    String longLine = "--------------------------------------------------";
    String LeGoatStr = "LeGoat: ";
    String deadlineTimeFormatReminder = """

                                PS: If you want "/by" to be formatted:
                                    yyyy mm dd <24h time>""";
    String eventTimeFormatReminder = """

                                PS: If you want "/from" or "/to" to be formatted:
                                    yyyy mm dd <24h time>""";

    public ui() {}

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
