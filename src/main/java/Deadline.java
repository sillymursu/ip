public class Deadline extends Task {
    String deadline;

    public Deadline(String taskName, String taskType, String taskStatus, String deadline) {
        super(taskName, taskType, taskStatus);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        String s = "[" + this.taskType + "][" + this.taskStatus + "]" + this.taskName + " (by:" + this.deadline + ")";
        return s;
    }
}
