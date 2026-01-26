public class Event extends Task {
    String begin;
    String end;

    public Event(String taskName, String taskType, String taskStatus, String begin, String end) {
        super(taskName, taskType, taskStatus);
        this.begin = begin;
        this.end = end;
    }

    @Override
    public String toString() {
        String s = "[" + this.taskType + "][" + this.taskStatus + "]" + this.taskName + 
        " (from:" + this.begin + " | to:" + this.end + ")";
        return s;
    }
}
