package legoat.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import legoat.exceptions.DoubleCompletionException;
import legoat.exceptions.WrongFormatMarkException;
import legoat.tasktypes.Task;
import legoat.tasktypes.TaskStatus;
import legoat.tasktypes.TaskType;

/**
 * Tests for MarkCommand.
 */
class MarkCommandTest {
    /**
     * Verifies that marking a valid task index marks the task complete.
     */
    @Test
    void execute_validIndex_marksTask() throws Exception {
        Parser parser = TestParserFactory.newParser();
        MarkCommand command = new MarkCommand();
        Task task = new Task("read", TaskType.TODO, TaskStatus.INCOMPLETE);
        parser.getTasks().add(task);

        String result = command.execute(new String[] {"mark", "1"}, parser);

        assertEquals(TaskStatus.COMPLETE, task.getTaskStatus());
        assertTrue(result.contains("Easy work. Task completed!"));
        assertTrue(result.contains("[T][X] read"));
    }

    /**
     * Verifies that marking an already complete task throws an exception.
     */
    @Test
    void execute_alreadyComplete_throwsDoubleCompletionException() {
        Parser parser = TestParserFactory.newParser();
        MarkCommand command = new MarkCommand();
        Task task = new Task("read", TaskType.TODO, TaskStatus.COMPLETE);
        parser.getTasks().add(task);

        DoubleCompletionException thrown = assertThrows(DoubleCompletionException.class, (
                ) -> command.execute(new String[] {"mark", "1"}, parser));
        assertNotNull(thrown.getMessage());
    }

    /**
     * Verifies that invalid mark input throws a wrong-format exception.
     */
    @Test
    void execute_invalidIndex_throwsWrongFormatMarkException() {
        Parser parser = TestParserFactory.newParser();
        MarkCommand command = new MarkCommand();
        parser.getTasks().add(new Task("read", TaskType.TODO, TaskStatus.INCOMPLETE));

        WrongFormatMarkException thrown = assertThrows(WrongFormatMarkException.class, (
                ) -> command.execute(new String[] {"mark", "abc"}, parser));
        assertNotNull(thrown.getMessage());
    }
}
