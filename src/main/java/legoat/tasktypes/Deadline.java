package legoat.tasktypes;

import legoat.ui.StringFormat;
/**
* Deadline is a subtype of Task, that has an extra "deadline" field.
*
* @author Russell Lin
*/
public class Deadline extends Task {
    private String deadline;

    /**
    * <p>Constructor for Deadline objects.
    * @since v0.1
    */
    public Deadline(String taskName, TaskType taskType, TaskStatus taskStatus, String deadline) {
        super(taskName, taskType, taskStatus);
        assert !deadline.isEmpty() : "deadline must not be empty";
        this.deadline = deadline;
    }

    /**
    * <p>Method that changes the "deadline" field of an instance of Deadline.
    * @since v0.3
    */
    public void changeDeadline(String[] input) {
        StringBuilder dDate = new StringBuilder();
        for (int j = 3; j < input.length; j++) {
            dDate.append(" ");
            dDate.append(input[j]);
        }
        String updatedDeadline = dDate.toString().trim();
        String updatedDeadlineDate = StringFormat.parseDate(updatedDeadline);
        if (updatedDeadlineDate.equals("")) {
            this.deadline = updatedDeadline;
        } else {
            this.deadline = updatedDeadlineDate;
        }
    }

    /**
    * <p>Getter method that returns the "deadline" field of an instance of Deadline.
    * @return String of the "deadline" field of an instance of Deadline
    * @since v0.1
    */
    public String getDeadline() {
        return this.deadline;
    }

    /**
    * <p>Returns the String representation of a Deadline.
    * @return String representation of a Deadline
    * @since v0.1
    */
    @Override
    public String toString() {
        return "[" + getTaskType().getCode() + "][" + getTaskStatus().getSymbol()
                + "] " + getTaskName() + " (by: " + deadline + ")";
    }
}
