package legoat.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import legoat.exceptions.DoubleIncompleteException;
import legoat.exceptions.WrongFormatUnmarkException;
import legoat.tasktypes.Task;
import legoat.tasktypes.TaskStatus;
import legoat.tasktypes.TaskType;

/**
 * Tests for UnmarkCommand.
 */
class UnmarkCommandTest {
    /**
     * Verifies that unmarking a valid task index marks the task incomplete.
     */
    @Test
    void execute_validIndex_unmarksTask() throws Exception {
        Parser parser = TestParserFactory.newParser();
        UnmarkCommand command = new UnmarkCommand();
        Task task = new Task("read", TaskType.TODO, TaskStatus.COMPLETE);
        parser.getTasks().add(task);

        String result = command.execute(new String[] {"unmark", "1"}, parser);

        assertEquals(TaskStatus.INCOMPLETE, task.getTaskStatus());
        assertTrue(result.contains("Electric Boogaloo. Task uncompleted!"));
        assertTrue(result.contains("[T][  ] read"));
    }

    /**
     * Verifies that unmarking an already incomplete task throws an exception.
     */
    @Test
    void execute_alreadyIncomplete_throwsDoubleIncompleteException() {
        Parser parser = TestParserFactory.newParser();
        UnmarkCommand command = new UnmarkCommand();
        Task task = new Task("read", TaskType.TODO, TaskStatus.INCOMPLETE);
        parser.getTasks().add(task);

        DoubleIncompleteException thrown = assertThrows(DoubleIncompleteException.class, (
                ) -> command.execute(new String[] {"unmark", "1"}, parser));
        assertNotNull(thrown.getMessage());
    }

    /**
     * Verifies that invalid unmark input throws a wrong-format exception.
     */
    @Test
    void execute_invalidIndex_throwsWrongFormatUnmarkException() {
        Parser parser = TestParserFactory.newParser();
        UnmarkCommand command = new UnmarkCommand();
        parser.getTasks().add(new Task("read", TaskType.TODO, TaskStatus.COMPLETE));

        WrongFormatUnmarkException thrown = assertThrows(WrongFormatUnmarkException.class, (
                ) -> command.execute(new String[] {"unmark", "abc"}, parser));
        assertNotNull(thrown.getMessage());
    }
}
