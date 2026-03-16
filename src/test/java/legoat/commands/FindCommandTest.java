package legoat.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import legoat.exceptions.WrongFormatFindException;
import legoat.tasktypes.Task;
import legoat.tasktypes.TaskStatus;
import legoat.tasktypes.TaskType;

/**
 * Tests for FindCommand.
 */
class FindCommandTest {
    /**
     * Verifies matching keyword returns all matching tasks.
     */
    @Test
    void execute_matchingKeyword_returnsMatchingTasks() throws Exception {
        Parser parser = TestParserFactory.newParser();
        FindCommand command = new FindCommand();
        parser.getTasks().add(new Task("read book", TaskType.TODO, TaskStatus.INCOMPLETE));
        parser.getTasks().add(new Task("book flight", TaskType.TODO, TaskStatus.INCOMPLETE));
        parser.getTasks().add(new Task("wash car", TaskType.TODO, TaskStatus.INCOMPLETE));

        String result = command.execute(new String[] {"find", "book"}, parser);

        assertTrue(result.startsWith("I found 2 tasks:"));
        assertTrue(result.contains("read book"));
        assertTrue(result.contains("book flight"));
    }

    /**
     * Verifies non-matching keyword returns a not-found message.
     */
    @Test
    void execute_noMatch_returnsNotFoundMessage() throws Exception {
        Parser parser = TestParserFactory.newParser();
        FindCommand command = new FindCommand();
        parser.getTasks().add(new Task("read", TaskType.TODO, TaskStatus.INCOMPLETE));

        String result = command.execute(new String[] {"find", "xyz"}, parser);

        assertTrue(result.contains("No tasks with keyword xyz were found!"));
    }

    /**
     * Verifies more than one keyword throws a wrong-format exception.
     */
    @Test
    void execute_multipleKeywords_throwsWrongFormatFindException() {
        Parser parser = TestParserFactory.newParser();
        FindCommand command = new FindCommand();

        WrongFormatFindException thrown = assertThrows(WrongFormatFindException.class, (
                ) -> command.execute(new String[] {"find", "book", "flight"}, parser));
        assertNotNull(thrown.getMessage());
    }

    /**
     * Verifies missing keyword throws a wrong-format exception.
     */
    @Test
    void execute_noKeyword_throwsWrongFormatFindException() {
        Parser parser = TestParserFactory.newParser();
        FindCommand command = new FindCommand();

        WrongFormatFindException thrown = assertThrows(WrongFormatFindException.class, (
                ) -> command.execute(new String[] {"find"}, parser));
        assertNotNull(thrown.getMessage());
    }
}
