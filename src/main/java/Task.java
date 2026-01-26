public class Task {
    String taskName;
    String taskType;
    String taskStatus;

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

    @Override
    public String toString() {
        String s = "[" + this.taskType + "][" + this.taskStatus + "]" + this.taskName;
        return s;
    }
}
