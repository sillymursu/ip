package legoat.tasktypes;
public class Task {
    private final String taskName;
    private final String taskType;
    private String taskStatus;

    public Task(String taskName, String taskType, String taskStatus) {
        this.taskName = taskName;
        this.taskType = taskType;
        this.taskStatus = taskStatus;
    }

    public void mark() {
        this.taskStatus = "X";
    }

    public void unmark() {
        this.taskStatus = " ";
    }

    public String getTaskName() {
        return this.taskName;
    }

    public String getTaskType() {
        return this.taskType;
    }

    public String getTaskStatus() {
        return this.taskStatus;
    }

    @Override
    public String toString() {
        String s = "[" + this.taskType + "][" + this.taskStatus + "] " + this.taskName;
        return s;
    }
}
