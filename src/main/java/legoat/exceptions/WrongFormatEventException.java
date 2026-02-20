package legoat.exceptions;

import legoat.ui.StringFormat;

/**
* Represents an error condition when the input command of the addEvent method
* is in the wrong format.
*
* @author Russell Lin
*/
public class WrongFormatEventException extends Exception {

    /**
    * Constructs a {@code WrongFormatEventException} with a detail message.
    */
    public WrongFormatEventException() {
        super(StringFormat.WRONG_FORMAT_EVENT_EXCEPTION_STRING);
    }
}
