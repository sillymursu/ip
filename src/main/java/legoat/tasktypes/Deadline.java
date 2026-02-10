package legoat.tasktypes;

/**
* Deadline is a subtype of Task, that has an extra "deadline" field.
*
* @author Russell Lin
*/
public class Deadline extends Task {
    private final String deadline;

    /**
    * <p>Constructor for Deadline objects.
    * @since v0.1
    */
    public Deadline(String taskName, TaskType taskType, TaskStatus taskStatus, String deadline) {
        super(taskName, taskType, taskStatus);
        this.deadline = deadline;
    }

    /**
    * <p>Getter method that returns the "deadline" field of an instance of Deadline.
    * @return String of the "deadline" field of an instance of Deadline
    * @since v0.1
    */
    public String getDeadline() {
        return this.deadline;
    }

    /**
    * <p>Returns the String representation of a Deadline.
    * @return String representation of a Deadline
    * @since v0.1
    */
    @Override
    public String toString() {
        return "[" + getTaskType().getCode() + "][" + getTaskStatus().getSymbol()
                + "] " + getTaskName() + " (by: " + deadline + ")";
    }
}
