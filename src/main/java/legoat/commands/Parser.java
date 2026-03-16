package legoat.commands;

import java.io.IOException;
import java.util.ArrayList;

import legoat.exceptions.DoubleCompletionException;
import legoat.exceptions.DoubleIncompleteException;
import legoat.exceptions.EmptyListException;
import legoat.exceptions.EventTimeException;
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
* Parser handles all task related events.
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
    * <p>Constructor for Parser.
    * Loads data of tasks on instance creation.
    * @since v0.2
    */
    public Parser() {
        this.loadTaskData();
    }

    /**
    * <p>Retrieves the current list of tasks.
    * @return ArrayList of all tasks
    * @since v0.2
    */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    /**
    * <p>Retrieves the Storage used by Parser.
    * @return Storage instance
    * @since v0.3
    */
    public Storage getStorage() {
        return this.storage;
    }

    /**
    * <p>Loads task data from "data\LeGoatData.txt" if applicable.
    * @since v0.2
    */
    private void loadTaskData() {
        storage.loadData(this);
    }

    /**
    * <p>Function called when user input is "list", prints a list of added tasks unless
    *  current list of tasks is empty.
    * @return A list of current tasks as a String
    * @since v0.2
    */
    public String list() throws EmptyListException {
        return listCommand.execute(new String[] {"list"}, this);
    }

    /**
    * <p>Marks a task based off input.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @since v0.2
    */
    public String mark(String[] input) throws IOException,
            DoubleCompletionException, WrongFormatMarkException {
        return markCommand.execute(input, this);
    }

    /**
    * <p>Unmarks a task based off input.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @since v0.2
    */
    public String unmark(String[] input) throws IOException,
            DoubleIncompleteException, WrongFormatUnmarkException {
        return unmarkCommand.execute(input, this);
    }

    /**
    * <p>Adds a Task object to the list of tasks maintained by Parser instance.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @since v0.2
    */
    public String addToDo(String[] input) throws IOException,
            WrongFormatTodoException {
        return todoCommand.execute(input, this);
    }

    /**
    * <p>Adds a Deadline object to the list of tasks maintained by Parser instance.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @since v0.1
    */
    public String addDeadline(String[] input) throws IOException,
            WrongFormatDeadlineException {
        return deadlineCommand.execute(input, this);
    }

    /**
    * <p>Adds a Event object to the list of tasks maintained by Parser instance.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @since v0.1
    */
    public String addEvent(String[] input) throws IOException,
            WrongFormatEventException, EventTimeException {
        return eventCommand.execute(input, this);
    }

    /**
    * <p>Deletes a task from the list of tasks maintained by Parser instance based on index [1-n]
    *  of the specified task in the list.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @since v0.1
    */
    public String deleteTask(String[] input) throws IOException,
            WrongFormatDeleteException {
        return deleteCommand.execute(input, this);
    }

    /**
    * <p>Finds tasks from the list of tasks maintained by Parser based on a
    * singular matching keyword in task names.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @since v0.1
    */
    public String find(String[] input) throws WrongFormatFindException {
        return findCommand.execute(input, this);
    }

    /**
    * <p>Gets correctly typed Todos, Events, Deadlines from the list of tasks.
    * @param t Selected Task
    * @return Selected task
    * @since v0.2
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
    * <p>Updates specified Todos, Events, Deadlines from the list of tasks.
    * @param input User input is stored as a String[], input[0] is used to decide which command runs
    * @return String representation of whether or not the update was successful or ran into problems
    * @since v0.3
    */
    public String update(String[] input) throws IOException,
            EventTimeException, WrongFormatUpdateException {
        return updateCommand.execute(input, this);
    }

    /**
    * <p>Identifies the command that the user wants to issue to LeGoat.
    * @return String that LeGoat will use to reply to the user
    * @since v0.2
    */
    public String handleCommand(String[] input) throws IOException,
            DoubleCompletionException, DoubleIncompleteException,
            EmptyListException, EventTimeException, WrongFormatDeadlineException,
            WrongFormatMarkException, WrongFormatUnmarkException,
            WrongFormatDeleteException, WrongFormatEventException,
            WrongFormatFindException, WrongFormatTodoException,
            WrongFormatUnknownException, WrongFormatUpdateException {
        return switch (input[0]) {
        case "bye" -> this.bye();
        case "list" -> this.list();
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
    * <p>Handles the case of the user issuing an unknown commmand to LeGoat.
    * @return String that LeGoat will use to reply to the user
    * @since v0.2
    */
    public String handleUnknownCommand() throws WrongFormatUnknownException {
        throw new WrongFormatUnknownException();
    }

    /**
    * <p>Handles the case of the user wanting LeGoat to log off.
    * @return String that LeGoat will use to reply to the user
    * @since v0.2
    */
    public String bye() {
        return StringFormat.BYE_STRING;
    }
}
