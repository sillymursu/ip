package legoat.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import legoat.exceptions.WrongFormatDeadlineException;
import legoat.tasktypes.Deadline;
import legoat.ui.StringFormat;

/**
 * Tests for DeadlineCommand.
 */
class DeadlineCommandTest {
    /**
     * Verifies that a formatted deadline input creates a deadline task.
     */
    @Test
    void execute_formattedDate_addsDeadlineTask() throws Exception {
        Parser parser = TestParserFactory.newParser();
        DeadlineCommand command = new DeadlineCommand();

        String result = command.execute(new String[] {
            "deadline", "submit", "report", "/by", "2026", "12", "01", "2300"
        }, parser);

        assertEquals(1, parser.getTasks().size());
        Deadline deadline = (Deadline) parser.getTasks().get(0);
        assertEquals("submit report", deadline.getTaskName());
        assertEquals(StringFormat.parseDate("2026 12 01 2300"), deadline.getDeadline());
        assertTrue(result.contains("Added Deadline @ index 1:"));
        assertTrue(result.contains("submit report"));
    }

    /**
     * Verifies reminder text appears when deadline date cannot be parsed.
     */
    @Test
    void execute_unformattedDate_includesReminder() throws Exception {
        Parser parser = TestParserFactory.newParser();
        DeadlineCommand command = new DeadlineCommand();

        String result = command.execute(new String[] {
            "deadline", "submit", "report", "/by", "tomorrow"
        }, parser);

        assertTrue(result.contains("PS: If you want \"/by\" to be formatted"));
    }

    /**
     * Verifies missing deadline content throws a wrong-format exception.
     */
    @Test
    void execute_missingDeadline_throwsWrongFormatDeadlineException() {
        Parser parser = TestParserFactory.newParser();
        DeadlineCommand command = new DeadlineCommand();

        WrongFormatDeadlineException thrown = assertThrows(WrongFormatDeadlineException.class, (
                ) -> command.execute(new String[] {"deadline", "submit"}, parser));
        assertNotNull(thrown.getMessage());
    }
}
