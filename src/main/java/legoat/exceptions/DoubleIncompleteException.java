package legoat.exceptions;

import legoat.ui.StringFormat;

/**
* Represents an error condition where a task is attempted to be marked as incomplete
* when it is already incomplete.
*
* @author Russell Lin
*/
public class DoubleIncompleteException extends Exception {

    /**
    * Constructs a {@code DoubleIncompleteException} with a detail message.
    */
    public DoubleIncompleteException() {
        super(StringFormat.DOUBLE_COMPLETION_EXCEPTION_STRING);
    }
}
