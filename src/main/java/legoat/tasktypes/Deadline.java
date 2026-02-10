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
    public Deadline(String taskName, String taskType, String taskStatus, String deadline) {
        super(taskName, taskType, taskStatus);
        assert !deadline.isEmpty() : "deadline must not be empty";
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
        String s = "[" + this.getTaskType() + "][" + this.getTaskStatus()
                + "] " + this.getTaskName() + " (by: " + this.deadline + ")";
        return s;
    }
}
