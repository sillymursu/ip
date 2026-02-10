package legoat.tasktypes;

/**
* Task class.
*
* @author Russell Lin
*/
public class Task {
    private final String taskName;
    private final TaskType taskType;
    private TaskStatus taskStatus;

    /**
    * <p>Constructor for Task objects.
    * @since v0.2
    */
    public Task(String taskName, TaskType taskType, TaskStatus taskStatus) {
        assert !taskName.isEmpty() : "taskName must not be empty!";
        assert taskType != null : "taskType must not be null!";
        assert taskStatus != null : "taskStatus must not be null!";
        this.taskName = taskName;
        this.taskType = taskType;
        this.taskStatus = taskStatus;
    }

    /**
    * <p>Sets the "taskStatus" field of an instance of Task to COMPLETE
    * @since v0.2
    */
    public void mark() {
        this.taskStatus = TaskStatus.COMPLETE;
    }

    /**
    * <p>Sets the "taskStatus" field of an instance of Task to INCOMPLETE
    * @since v0.2
    */
    public void unmark() {
        this.taskStatus = TaskStatus.INCOMPLETE;
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
    * @return TaskType of an instance of Task
    * @since v0.2
    */
    public TaskType getTaskType() {
        return this.taskType;
    }

    /**
    * <p>Getter method that returns the "taskStatus" field of an instance of Task.
    * @return TaskStatus of an instance of Task
    * @since v0.2
    */
    public TaskStatus getTaskStatus() {
        return this.taskStatus;
    }

    /**
    * <p>Returns the String representation of a Task.
    * @return String representation of a Task
    * @since v0.1
    */
    @Override
    public String toString() {
        return "[" + taskType.getCode() + "][" + taskStatus.getSymbol() + "] " + this.taskName;
    }
}
