package legoat.tasktypes;

/**
* Event is a subtype of Task, that has an extra "begin" field and "end" field.
*
* @author Russell Lin
*/
public class Event extends Task {
    private final String begin;
    private final String end;

    /**
    * <p>Constructor for Event objects.
    * @since v0.1
    */
    public Event(String taskName, String taskType, String taskStatus, String begin, String end) {
        super(taskName, taskType, taskStatus);
        this.begin = begin;
        this.end = end;
    }

    /**
    * <p>Getter method that returns the "begin" field of an instance of Event.
    * @return String of the "begin" field of an instance of Event
    * @since v0.1
    */
    public String getBegin() {
        return this.begin;
    }
    /**
    * <p>Getter method that returns the "end" field of an instance of Event.
    * @return String of the "end" field of an instance of Event
    * @since v0.1
    */
    public String getEnd() {
        return this.end;
    }

    /**
    * <p>Returns the String representation of a Event.
    * @return String representation of a Event
    * @since v0.1
    */
    @Override
    public String toString() {
        String s = "[" + this.getTaskType() + "][" + this.getTaskStatus()
                + "] " + this.getTaskName() + " (from: " + this.begin + " | to: " + this.end + ")";
        return s;
    }
}
