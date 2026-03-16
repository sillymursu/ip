package legoat.exceptions;

import legoat.ui.StringFormat;

/**
* Represents an error condition when the input command of the Mark method
* is in the wrong format.
*
* @author Russell Lin
*/
public class WrongFormatMarkException extends Exception {

    /**
    * Constructs a {@code WrongFormatMarkException} with a detail message.
    */
    public WrongFormatMarkException() {
        super(StringFormat.WRONG_FORMAT_MARK_EXCEPTION_STRING);
    }
}
