package legoat.commands;

import java.io.IOException;
import java.util.ArrayList;

import legoat.exceptions.DoubleCompletionException;
import legoat.exceptions.DoubleIncompleteException;
import legoat.exceptions.EmptyListException;
import legoat.exceptions.EventTimeException;
import legoat.exceptions.WrongFormatListException;
import legoat.exceptions.WrongFormatDeadlineException;
import legoat.exceptions.WrongFormatDeleteException;
import legoat.exceptions.WrongFormatEventException;
import legoat.exceptions.WrongFormatFindException;
import legoat.exceptions.WrongFormatMarkException;
import legoat.exceptions.WrongFormatUnmarkException;
import legoat.exceptions.WrongFormatTodoException;
import legoat.exceptions.WrongFormatUnknownException;
import legoat.exceptions.WrongFormatUpdateException;
import legoat.storage.Storage;
import legoat.tasktypes.Deadline;
import legoat.tasktypes.Event;
import legoat.tasktypes.Task;
import legoat.tasktypes.TaskType;
import legoat.ui.StringFormat;

/**
 * Parser routes user commands, owns in-memory task state, and coordinates storage.
 *
 * @author Russell Lin
 */
public class Parser {
    private final ArrayList<Task> tasks = new ArrayList<>();
    private final Storage storage = new Storage();
    private final ListCommand listCommand = new ListCommand();
    private final MarkCommand markCommand = new MarkCommand();
    private final UnmarkCommand unmarkCommand = new UnmarkCommand();
    private final TodoCommand todoCommand = new TodoCommand();
    private final DeadlineCommand deadlineCommand = new DeadlineCommand();
    private final EventCommand eventCommand = new EventCommand();
    private final DeleteCommand deleteCommand = new DeleteCommand();
    private final FindCommand findCommand = new FindCommand();
    private final UpdateCommand updateCommand = new UpdateCommand();

    /**
     * Creates a parser and loads persisted tasks into memory.
     */
    public Parser() {
        this.loadTaskData();
    }

    /**
     * Returns the current mutable task list.
     *
     * @return In-memory list of tasks
     */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Returns the storage component used for persistence.
     *
     * @return Storage instance used by this parser
     */
    public Storage getStorage() {
        return this.storage;
    }

    /**
     * Loads persisted task data from disk, if available.
     */
    private void loadTaskData() {
        storage.loadData(this);
    }

    /**
     * Executes the list command.
     *
     * @param input User command tokens
     * @return Numbered list of current tasks
     * @throws EmptyListException If there are no tasks to list
     * @throws WrongFormatListException If list command format is invalid
     */
    public String list(String[] input) throws EmptyListException, WrongFormatListException {
        return listCommand.execute(input, this);
    }

    /**
     * Executes the mark command.
     *
     * @param input User command tokens
     * @return User-facing mark result message
     * @throws IOException If saving updated tasks fails
     * @throws DoubleCompletionException If selected task is already complete
     * @throws WrongFormatMarkException If mark command format is invalid
     */
    public String mark(String[] input) throws IOException,
            DoubleCompletionException, WrongFormatMarkException {
        return markCommand.execute(input, this);
    }

    /**
     * Executes the unmark command.
     *
     * @param input User command tokens
     * @return User-facing unmark result message
     * @throws IOException If saving updated tasks fails
     * @throws DoubleIncompleteException If selected task is already incomplete
     * @throws WrongFormatUnmarkException If unmark command format is invalid
     */
    public String unmark(String[] input) throws IOException,
            DoubleIncompleteException, WrongFormatUnmarkException {
        return unmarkCommand.execute(input, this);
    }

    /**
     * Executes the todo command.
     *
     * @param input User command tokens
     * @return User-facing todo creation message
     * @throws IOException If saving updated tasks fails
     * @throws WrongFormatTodoException If todo command format is invalid
     */
    public String addToDo(String[] input) throws IOException,
            WrongFormatTodoException {
        return todoCommand.execute(input, this);
    }

    /**
     * Executes the deadline command.
     *
     * @param input User command tokens
     * @return User-facing deadline creation message
     * @throws IOException If saving updated tasks fails
     * @throws WrongFormatDeadlineException If deadline command format is invalid
     */
    public String addDeadline(String[] input) throws IOException,
            WrongFormatDeadlineException {
        return deadlineCommand.execute(input, this);
    }

    /**
     * Executes the event command.
     *
     * @param input User command tokens
     * @return User-facing event creation message
     * @throws IOException If saving updated tasks fails
     * @throws WrongFormatEventException If event command format is invalid
     * @throws EventTimeException If event start time is after end time
     */
    public String addEvent(String[] input) throws IOException,
            WrongFormatEventException, EventTimeException {
        return eventCommand.execute(input, this);
    }

    /**
     * Executes the delete command.
     *
     * @param input User command tokens
     * @return User-facing delete result message
     * @throws IOException If saving updated tasks fails
     * @throws WrongFormatDeleteException If delete command format is invalid
     */
    public String deleteTask(String[] input) throws IOException,
            WrongFormatDeleteException {
        return deleteCommand.execute(input, this);
    }

    /**
     * Executes the find command.
     *
     * @param input User command tokens
     * @return User-facing find result message
     * @throws WrongFormatFindException If find command format is invalid
     */
    public String find(String[] input) throws WrongFormatFindException {
        return findCommand.execute(input, this);
    }

    /**
     * Returns the concrete runtime task type for display/update behavior.
     *
     * @param t Selected task
     * @return Same task reference with concrete subtype type information
     */
    public Task getTaskBasedOnType(Task t) {
        TaskType taskType = t.getTaskType();
        switch (taskType) {
        case DEADLINE -> {
            Deadline d = (Deadline) t;
            return d;
        }
        case EVENT -> {
            Event e = (Event) t;
            return e;
        }
        default -> {
            return t;
        }
        }
    }

    /**
     * Executes the update command.
     *
     * @param input User command tokens
     * @return User-facing update result message
     * @throws IOException If saving updated tasks fails
     * @throws EventTimeException If updated event timing is invalid
     * @throws WrongFormatUpdateException If update command format is invalid
     */
    public String update(String[] input) throws IOException,
            EventTimeException, WrongFormatUpdateException {
        return updateCommand.execute(input, this);
    }

    /**
     * Routes a tokenized user command to the corresponding command handler.
     *
     * @param input User command tokens
     * @return User-facing response string
     * @throws IOException If persistence fails during command execution
     * @throws DoubleCompletionException If mark targets an already-complete task
     * @throws DoubleIncompleteException If unmark targets an already-incomplete task
     * @throws EmptyListException If listing is requested for an empty task list
     * @throws WrongFormatListException If list command format is invalid
     * @throws EventTimeException If event timing is invalid
     * @throws WrongFormatDeadlineException If deadline command format is invalid
     * @throws WrongFormatMarkException If mark command format is invalid
     * @throws WrongFormatUnmarkException If unmark command format is invalid
     * @throws WrongFormatDeleteException If delete command format is invalid
     * @throws WrongFormatEventException If event command format is invalid
     * @throws WrongFormatFindException If find command format is invalid
     * @throws WrongFormatTodoException If todo command format is invalid
     * @throws WrongFormatUnknownException If command keyword is unrecognized
     * @throws WrongFormatUpdateException If update command format is invalid
     */
    public String handleCommand(String[] input) throws IOException,
            DoubleCompletionException, DoubleIncompleteException,
            EmptyListException, WrongFormatListException, EventTimeException,
            WrongFormatDeadlineException, WrongFormatMarkException,
            WrongFormatUnmarkException, WrongFormatDeleteException,
            WrongFormatEventException, WrongFormatFindException,
            WrongFormatTodoException, WrongFormatUnknownException,
            WrongFormatUpdateException {
        return switch (input[0]) {
        case "bye" -> this.bye();
        case "list" -> this.list(input);
        case "mark" -> this.mark(input);
        case "unmark" -> this.unmark(input);
        case "todo" -> this.addToDo(input);
        case "deadline" -> this.addDeadline(input);
        case "event" -> this.addEvent(input);
        case "delete" -> this.deleteTask(input);
        case "find" -> this.find(input);
        case "update" -> this.update(input);
        default -> this.handleUnknownCommand();
        };
    }

    /**
     * Handles unknown command keywords.
     *
     * @return Never returns normally
     * @throws WrongFormatUnknownException Always thrown to signal unsupported command
     */
    public String handleUnknownCommand() throws WrongFormatUnknownException {
        throw new WrongFormatUnknownException();
    }

    /**
     * Handles logoff command response.
     *
     * @return Logoff response string
     */
    public String bye() {
        return StringFormat.BYE_STRING;
    }
}
