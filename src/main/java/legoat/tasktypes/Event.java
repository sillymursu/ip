package legoat.tasktypes;
public class Event extends Task {
    private final String begin;
    private final String end;

    public Event(String taskName, String taskType, String taskStatus, String begin, String end) {
        super(taskName, taskType, taskStatus);
        this.begin = begin;
        this.end = end;
    }

    public String getBegin() {
        return this.begin;
    }

    public String getEnd() {
        return this.end;
    }

    @Override
    public String toString() {
        String s = "[" + this.getTaskType() + "][" + this.getTaskStatus() + "] " + this.getTaskName() +
                " (from: " + this.begin + " | to: " + this.end + ")";
        return s;
    }
}
