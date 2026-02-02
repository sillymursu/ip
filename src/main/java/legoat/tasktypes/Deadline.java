package tasktypes;

public class Deadline extends Task {
    private final String deadline;

    public Deadline(String taskName, String taskType, String taskStatus, String deadline) {
        super(taskName, taskType, taskStatus);
        this.deadline = deadline;
    }

    public String getDeadline() {
        return this.deadline;
    }

    @Override
    public String toString() {
        String s = "[" + this.getTaskType() + "][" + this.getTaskStatus() + "] " + this.getTaskName() +
            " (by: " + this.deadline + ")";
        return s;
    }
}
