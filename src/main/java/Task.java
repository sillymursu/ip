public class Task {
    String taskName;
    String markStatus;

    public Task(String taskName, String markStatus) {
        this.taskName = taskName;
        this.markStatus = markStatus;
    }

    public void mark() {
        this.markStatus = "X";
    }

    public void unmark() {
        this.markStatus = " ";
    }
}
