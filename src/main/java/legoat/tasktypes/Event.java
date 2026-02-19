package legoat.tasktypes;

import legoat.ui.StringFormat;

/**
* Event is a subtype of Task, that has an extra "begin" field and "end" field.
*
* @author Russell Lin
*/
public class Event extends Task {
    private String begin;
    private String end;

    /**
    * <p>Constructor for Event objects.
    * @since v0.1
    */
    public Event(String taskName, TaskType taskType, TaskStatus taskStatus, String begin, String end) {
        super(taskName, taskType, taskStatus);
        assert !begin.isEmpty() : "Event beginning must not be empty!";
        assert !end.isEmpty() : "Event ending must not be empty!";
        this.begin = begin;
        this.end = end;
    }

    /**
    * <p>Method that changes the "begin" or "end" field of an instance of Event.
    * @since v0.3
    */
    public void changeEvent(String[] input) {
        String fieldToUpdate = input[3];
        StringBuilder eField = new StringBuilder();
        for (int j = 4; j < input.length; j++) {
            eField.append(" ");
            eField.append(input[j]);
        }
        String updatedField = eField.toString().trim();
        String updatedFieldDate = StringFormat.parseDate(updatedField);
        switch (fieldToUpdate) {
        case "/from" -> {
            if (updatedFieldDate.equals("")) {
                this.begin = updatedField;
            } else {
                this.begin = updatedFieldDate;
            }
        }
        case "/to" -> {
            if (updatedFieldDate.equals("")) {
                this.end = updatedField;
            } else {
                this.end = updatedFieldDate;
            }
        }
        default -> { }
        }
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
        return "[" + getTaskType().getCode() + "][" + getTaskStatus().getSymbol()
                + "] " + getTaskName() + " (from: " + begin + " | to: " + end + ")";
    }
}
