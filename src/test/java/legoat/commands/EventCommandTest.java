package legoat.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import legoat.exceptions.EventTimeException;
import legoat.exceptions.WrongFormatEventException;
import legoat.tasktypes.Event;
import legoat.ui.StringFormat;

/**
 * Tests for EventCommand.
 */
class EventCommandTest {
    /**
     * Verifies formatted event input creates an event task.
     */
    @Test
    void execute_formattedDates_addsEventTask() throws Exception {
        Parser parser = TestParserFactory.newParser();
        EventCommand command = new EventCommand();

        String result = command.execute(new String[] {
            "event", "meeting", "/from", "2026", "12", "01", "1200", "/to", "2026", "12", "01", "1300"
        }, parser);

        assertEquals(1, parser.getTasks().size());
        Event event = (Event) parser.getTasks().get(0);
        assertEquals("meeting", event.getTaskName());
        assertEquals(StringFormat.parseDate("2026 12 01 1200"), event.getBegin());
        assertEquals(StringFormat.parseDate("2026 12 01 1300"), event.getEnd());
        assertTrue(result.contains("Added Event @ index 1:"));
    }

    /**
     * Verifies reminder text appears when event dates cannot be parsed.
     */
    @Test
    void execute_unformattedDates_includesReminder() throws Exception {
        Parser parser = TestParserFactory.newParser();
        EventCommand command = new EventCommand();

        String result = command.execute(new String[] {
            "event", "meeting", "/from", "tomorrow", "/to", "later"
        }, parser);

        assertTrue(result.contains("PS: If you want \"/from\" or \"/to\" to be formatted"));
    }

    /**
     * Verifies missing event end time throws a wrong-format exception.
     */
    @Test
    void execute_missingEnd_throwsWrongFormatEventException() {
        Parser parser = TestParserFactory.newParser();
        EventCommand command = new EventCommand();

        WrongFormatEventException thrown = assertThrows(WrongFormatEventException.class, (
                ) -> command.execute(new String[] {
                    "event", "meeting", "/from", "2026", "12", "01", "1200"
                }, parser));
        assertNotNull(thrown.getMessage());
    }

    /**
     * Verifies event start time after end time throws an event-time exception.
     */
    @Test
    void execute_startAfterEnd_throwsEventTimeException() {
        Parser parser = TestParserFactory.newParser();
        EventCommand command = new EventCommand();

        EventTimeException thrown = assertThrows(EventTimeException.class, (
                ) -> command.execute(new String[] {
                    "event", "meeting", "/from", "2026", "12", "01", "1300",
                    "/to", "2026", "12", "01", "1200"
                }, parser));
        assertNotNull(thrown.getMessage());
    }
}
