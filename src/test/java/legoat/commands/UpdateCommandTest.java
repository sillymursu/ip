package legoat.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import legoat.exceptions.WrongFormatUpdateException;
import legoat.tasktypes.Deadline;
import legoat.tasktypes.Event;
import legoat.tasktypes.Task;
import legoat.tasktypes.TaskStatus;
import legoat.tasktypes.TaskType;
import legoat.ui.StringFormat;

/**
 * Tests for UpdateCommand.
 */
class UpdateCommandTest {
    /**
     * Verifies updating task name changes the task name successfully.
     */
    @Test
    void execute_updateName_successfullyChangesTaskName() throws Exception {
        Parser parser = TestParserFactory.newParser();
        UpdateCommand command = new UpdateCommand();
        parser.getTasks().add(new Task("read", TaskType.TODO, TaskStatus.INCOMPLETE));

        String result = command.execute(new String[] {
            "update", "1", "name", "write", "tests"
        }, parser);

        assertEquals("write tests", parser.getTasks().get(0).getTaskName());
        assertTrue(result.contains(StringFormat.UPDATE_SUCCESS_STRING));
    }

    /**
     * Verifies updating deadline field changes the deadline value.
     */
    @Test
    void execute_updateDeadline_successfullyChangesDeadline() throws Exception {
        Parser parser = TestParserFactory.newParser();
        UpdateCommand command = new UpdateCommand();
        Deadline deadline = new Deadline("submit", TaskType.DEADLINE, TaskStatus.INCOMPLETE, "2026 12 01 1200");
        parser.getTasks().add(deadline);

        String result = command.execute(new String[] {
            "update", "1", "deadline", "2026", "12", "02", "1300"
        }, parser);

        assertEquals(StringFormat.parseDate("2026 12 02 1300"), deadline.getDeadline());
        assertTrue(result.contains(StringFormat.UPDATE_SUCCESS_STRING));
    }

    /**
     * Verifies updating event /from field changes event begin time.
     */
    @Test
    void execute_updateEventFrom_successfullyChangesEventBegin() throws Exception {
        Parser parser = TestParserFactory.newParser();
        UpdateCommand command = new UpdateCommand();
        Event event = new Event("meeting", TaskType.EVENT, TaskStatus.INCOMPLETE,
                "2026 12 01 1200", "2026 12 01 1300");
        parser.getTasks().add(event);

        String result = command.execute(new String[] {
            "update", "1", "event", "/from", "2026", "12", "01", "1100"
        }, parser);

        assertEquals(StringFormat.parseDate("2026 12 01 1100"), event.getBegin());
        assertTrue(result.contains(StringFormat.UPDATE_SUCCESS_STRING));
    }

    /**
     * Verifies incomplete update command format throws an exception.
     */
    @Test
    void execute_invalidUpdateFormat_throwsWrongFormatUpdateException() {
        Parser parser = TestParserFactory.newParser();
        UpdateCommand command = new UpdateCommand();
        parser.getTasks().add(new Task("read", TaskType.TODO, TaskStatus.INCOMPLETE));

        WrongFormatUpdateException thrown = assertThrows(WrongFormatUpdateException.class, (
                ) -> command.execute(new String[] {"update", "1", "name"}, parser));
        assertNotNull(thrown.getMessage());
    }

    /**
     * Verifies deadline updates on a Todo task are rejected.
     */
    @Test
    void execute_deadlineUpdateOnTodo_throwsWrongFormatUpdateException() {
        Parser parser = TestParserFactory.newParser();
        UpdateCommand command = new UpdateCommand();
        parser.getTasks().add(new Task("read", TaskType.TODO, TaskStatus.INCOMPLETE));

        WrongFormatUpdateException thrown = assertThrows(WrongFormatUpdateException.class, (
                ) -> command.execute(new String[] {
                    "update", "1", "deadline", "2026", "12", "02", "1300"
                }, parser));
        assertNotNull(thrown.getMessage());
    }

    /**
     * Verifies invalid event sub-field in update command is rejected.
     */
    @Test
    void execute_invalidEventField_throwsWrongFormatUpdateException() {
        Parser parser = TestParserFactory.newParser();
        UpdateCommand command = new UpdateCommand();
        Event event = new Event("meeting", TaskType.EVENT, TaskStatus.INCOMPLETE,
                "2026 12 01 1200", "2026 12 01 1300");
        parser.getTasks().add(event);

        WrongFormatUpdateException thrown = assertThrows(WrongFormatUpdateException.class, (
                ) -> command.execute(new String[] {
                    "update", "1", "event", "/at", "2026", "12", "01", "1100"
                }, parser));
        assertNotNull(thrown.getMessage());
    }
}
