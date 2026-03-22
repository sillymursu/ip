package legoat.exceptions;

import legoat.ui.StringFormat;

/**
* Represents an error condition when the input command of the addDeadline method
* is in the wrong format.
*
* @author Russell Lin
*/
public class WrongFormatListException extends Exception {

    /**
    * Constructs a {@code WrongFormatListException} with a detail message.
    */
    public WrongFormatListException() {
        super(StringFormat.WRONG_FORMAT_LIST_EXCEPTION_STRING);
    }
}
