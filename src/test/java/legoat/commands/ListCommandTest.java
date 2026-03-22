package legoat.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import legoat.exceptions.EmptyListException;
import legoat.tasktypes.Deadline;
import legoat.tasktypes.Task;
import legoat.tasktypes.TaskStatus;
import legoat.tasktypes.TaskType;

/**
 * Tests for ListCommand.
 */
class ListCommandTest {
    /**
     * Verifies that listing an empty task list throws an exception.
     */
    @Test
    void execute_emptyList_throwsEmptyListException() {
        Parser parser = TestParserFactory.newParser();
        ListCommand command = new ListCommand();

        EmptyListException thrown = assertThrows(EmptyListException.class, (
                ) -> command.execute(new String[] {"list"}, parser));
        assertNotNull(thrown.getMessage());
    }

    /**
     * Verifies that listing tasks returns a numbered, formatted output.
     */
    @Test
    void execute_withTasks_returnsNumberedList() throws Exception {
        Parser parser = TestParserFactory.newParser();
        ListCommand command = new ListCommand();
        parser.getTasks().add(new Task("read", TaskType.TODO, TaskStatus.INCOMPLETE));
        parser.getTasks().add(new Deadline("submit", TaskType.DEADLINE,
                TaskStatus.INCOMPLETE, "tomorrow"));

        String result = command.execute(new String[] {"list"}, parser);

        assertEquals("1. [T][  ] read\n2. [D][  ] submit (by: tomorrow)", result);
    }
}
