package legoat.exceptions;

import legoat.ui.StringFormat;

/**
* Represents an error condition where a task is attempted to be marked as complete
* when it is already complete.
*
* @author Russell Lin
*/
public class DoubleCompletionException extends Exception {

    /**
    * Constructs a {@code DoubleCompletionException} with a detail message.
    */
    public DoubleCompletionException() {
        super(StringFormat.DOUBLE_COMPLETION_EXCEPTION_STRING);
    }
}
