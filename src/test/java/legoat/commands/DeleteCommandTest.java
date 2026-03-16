package legoat.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import legoat.exceptions.WrongFormatDeleteException;
import legoat.tasktypes.Task;
import legoat.tasktypes.TaskStatus;
import legoat.tasktypes.TaskType;

/**
 * Tests for DeleteCommand.
 */
class DeleteCommandTest {
    /**
     * Verifies singular message when one task remains after deletion.
     */
    @Test
    void execute_oneTaskLeft_returnsSingularMessage() throws Exception {
        Parser parser = TestParserFactory.newParser();
        DeleteCommand command = new DeleteCommand();
        parser.getTasks().add(new Task("first", TaskType.TODO, TaskStatus.INCOMPLETE));
        parser.getTasks().add(new Task("second", TaskType.TODO, TaskStatus.INCOMPLETE));

        String result = command.execute(new String[] {"delete", "1"}, parser);

        assertEquals(1, parser.getTasks().size());
        assertEquals("Task deleted!!\nYou have 1 Task left!!", result);
    }

    /**
     * Verifies plural message when multiple tasks remain after deletion.
     */
    @Test
    void execute_multipleTasksLeft_returnsPluralMessage() throws Exception {
        Parser parser = TestParserFactory.newParser();
        DeleteCommand command = new DeleteCommand();
        parser.getTasks().add(new Task("first", TaskType.TODO, TaskStatus.INCOMPLETE));
        parser.getTasks().add(new Task("second", TaskType.TODO, TaskStatus.INCOMPLETE));
        parser.getTasks().add(new Task("third", TaskType.TODO, TaskStatus.INCOMPLETE));

        String result = command.execute(new String[] {"delete", "1"}, parser);

        assertEquals(2, parser.getTasks().size());
        assertEquals("Task deleted!!\nYou have 2 Tasks left!!", result);
    }

    /**
     * Verifies invalid delete input throws a wrong-format exception.
     */
    @Test
    void execute_invalidIndex_throwsWrongFormatDeleteException() {
        Parser parser = TestParserFactory.newParser();
        DeleteCommand command = new DeleteCommand();
        parser.getTasks().add(new Task("first", TaskType.TODO, TaskStatus.INCOMPLETE));

        WrongFormatDeleteException thrown = assertThrows(WrongFormatDeleteException.class, (
                ) -> command.execute(new String[] {"delete", "abc"}, parser));
        assertNotNull(thrown.getMessage());
    }
}
