package legoat.exceptions;

import legoat.ui.StringFormat;

/**
* Represents an error condition when the input command of the addDeadline method
* is in the wrong format.
*
* @author Russell Lin
*/
public class WrongFormatDeadlineException extends Exception {

    /**
    * Constructs a {@code WrongFormatDeadlineException} with a detail message.
    */
    public WrongFormatDeadlineException() {
        super(StringFormat.WRONG_FORMAT_DEADLINE_EXCEPTION_STRING);
    }
}
