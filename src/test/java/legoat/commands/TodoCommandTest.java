package legoat.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import legoat.exceptions.WrongFormatTodoException;
import legoat.tasktypes.Task;
import legoat.tasktypes.TaskStatus;
import legoat.tasktypes.TaskType;

/**
 * Tests for TodoCommand.
 */
class TodoCommandTest {
    /**
     * Verifies that valid todo input creates an incomplete Todo task.
     */
    @Test
    void execute_validInput_addsTodoTask() throws Exception {
        Parser parser = TestParserFactory.newParser();
        TodoCommand command = new TodoCommand();

        String result = command.execute(new String[] {"todo", "read", "book"}, parser);

        assertEquals(1, parser.getTasks().size());
        Task addedTask = parser.getTasks().get(0);
        assertEquals("read book", addedTask.getTaskName());
        assertEquals(TaskType.TODO, addedTask.getTaskType());
        assertEquals(TaskStatus.INCOMPLETE, addedTask.getTaskStatus());
        assertEquals("Added Task @ index 1:\n[T][  ] read book", result);
    }

    /**
     * Verifies that missing todo content throws a wrong-format exception.
     */
    @Test
    void execute_missingName_throwsWrongFormatTodoException() {
        Parser parser = TestParserFactory.newParser();
        TodoCommand command = new TodoCommand();

        WrongFormatTodoException thrown = assertThrows(WrongFormatTodoException.class, (
                ) -> command.execute(new String[] {"todo"}, parser));
        assertNotNull(thrown.getMessage());
    }
}
