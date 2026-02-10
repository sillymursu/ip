package legoat.tasktypes;

/**
* Task class.
*
* @author Russell Lin
*/
public class Task {
    private final String taskName;
    private final String taskType;
    private String taskStatus;

    /**
    * <p>Constructor for Task objects.
    * @since v0.1
    */
    public Task(String taskName, String taskType, String taskStatus) {
        this.taskName = taskName;
        this.taskType = taskType;
        this.taskStatus = taskStatus;
    }

    /**
    * <p>Sets the "taskStatus" field of an instance of Task to "X"
    * @since v0.1
    */
    public void mark() {
        this.taskStatus = "X";
    }

    /**
    * <p>Sets the "taskStatus" field of an instance of Task to " "
    * @since v0.1
    */
    public void unmark() {
        this.taskStatus = "  ";
    }

    /**
    * <p>Getter method that returns the "taskName" field of an instance of Task.
    * @return String of the "taskName" field of an instance of Task
    * @since v0.1
    */
    public String getTaskName() {
        return this.taskName;
    }

    /**
    * <p>Getter method that returns the "taskType" field of an instance of Task.
    * @return String of the "taskType" field of an instance of Task
    * @since v0.1
    */
    public String getTaskType() {
        return this.taskType;
    }

    /**
    * <p>Getter method that returns the "taskStatus" field of an instance of Task.
    * @return String of the "taskStatus" field of an instance of Task
    * @since v0.1
    */
    public String getTaskStatus() {
        return this.taskStatus;
    }

    /**
    * <p>Returns the String representation of a Task.
    * @return String representation of a Task
    * @since v0.1
    */
    @Override
    public String toString() {
        return "[" + this.taskType + "][" + this.taskStatus + "] " + this.taskName;
    }
}
